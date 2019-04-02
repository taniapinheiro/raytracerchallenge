package com.tcmpinheiro.raytracerchallenge.features

data class PointLight(val position: Tuple = point(0.0, 0.0, 0.0), val intensity:Color = Color(1.0, 1.0, 1.0))

data class TargetObject(var transform: Matrix = identityMatrix(),
                        var material: Material = Material()
)

fun sphere():TargetObject{
    return TargetObject()
}

data class Material(val color: Color = Color(1.0, 1.0, 1.0),
                    var ambient: Double = 0.1,
                    val diffuse: Double = 0.9,
                    val specular: Double = 0.9,
                    val shininess: Double = 200.0)

fun normal_at(shape:TargetObject, worldPoint:Tuple):Tuple {
    val objectPoint = shape.transform.inverse() * worldPoint
    val objectNormal = objectPoint - point(0.0, 0.0, 0.0)
    val worldNormal = shape.transform.inverse().transpose() * objectNormal
    worldNormal.w = 0.0
    return normalize(worldNormal)
}

fun reflect(vector: Tuple, normal: Tuple): Tuple {
    return  vector - normal * 2.0 * dot(vector, normal)
}

fun lighting(material: Material, light: PointLight, point: Tuple, eyeV: Tuple, normalV: Tuple, inShadow:Boolean = false): Color{
    //combine the surface color with the light's color/intensity
    val effectiveColor = material.color * light.intensity

    //find the direction to the light source
    val lightv = normalize(light.position - point)

    // compute the ambient contribution
    val ambientLight = effectiveColor * material.ambient

    // light_dot_normal represents the cosine of the angle between the
    // light vector and the normal vector. A negative number means the
    // light is on the other side of the surface.
    var diffuse: Color
    var specular: Color
    val lightDotNormal = dot(lightv, normalV)

    if (lightDotNormal < 0) {
        diffuse = Color(0.0, 0.0, 0.0)
        specular = Color(0.0, 0.0, 0.0)
    } else {
        // compute the diffuse contribution
        diffuse = effectiveColor * material.diffuse * lightDotNormal

        // reflect_dot_eye represents the cosine of the angle between the
        // reflection vector and the eye vector. A negative number means the
        // light reflects away from the eye.
        val reflectV = reflect(-lightv, normalV)
        val reflectDotEye = dot(reflectV, eyeV)
        if (reflectDotEye <= 0){
            specular = Color(0.0, 0.0, 0.0)
        } else {
            //compute the specular contribution
            val factor =  Math.pow(reflectDotEye, material.shininess)
            specular = light.intensity * material.specular * factor
        }
    }
    return ambientLight + diffuse + specular
}