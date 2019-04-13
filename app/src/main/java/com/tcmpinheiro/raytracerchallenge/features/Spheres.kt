package com.tcmpinheiro.raytracerchallenge.features

data class PointLight(val position: Tuple = point(0.0, 0.0, 0.0), val intensity:Color = Color(1.0, 1.0, 1.0))

abstract class Shape(var transform: Matrix = identityMatrix(),
                 var material: Material = Material()) {

    abstract fun local_intersect(ray: Ray): List<Intersection>

    abstract fun local_normal_at(point: Tuple):Tuple

    fun intersect(ray:Ray):List<Intersection> {
        val local_ray = transform(ray, transform.inverse())
        return local_intersect(local_ray)
    }

    fun normal_at(point:Tuple): Tuple {
        val local_point = transform.inverse() * point
        val local_normal = local_normal_at(local_point)
        val world_normal = transform.inverse().transpose() * local_normal
        world_normal.w = 0.0
        return normalize(world_normal)
    }



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shape

        return transform == other.transform &&
                material == other.material
    }

    override fun hashCode(): Int {
        var result = transform.hashCode()
        result = 31 * result + material.hashCode()
        return result
    }
}

class Sphere(transform: Matrix = identityMatrix(),
             material: Material = Material()) : Shape(transform, material) {

    override fun local_normal_at(point: Tuple): Tuple {
        return vector(point.x, point.y, point.z)
    }

    lateinit var localRay:Ray

    override fun local_intersect(ray: Ray): List<Intersection> {
        //the vector from the shape's center, to the ray origin
        //remember: the shape is centered at the world origin sphere_to_ray ← ray.origin - point(0, 0, 0)
        localRay = ray
        val sphere_to_ray = ray.origin - point(0.0, 0.0, 0.0)
        val a = dot(ray.direction, ray.direction)
        val b = 2 * dot(ray.direction, sphere_to_ray)
        val c = dot(sphere_to_ray, sphere_to_ray) - 1
        val discriminant = b * b - 4 * a * c
        return if (discriminant >= 0) {
            val i1 = Intersection((-b - Math.sqrt(discriminant)) / (2 * a), this)
            val i2 = Intersection(( -b + Math.sqrt(discriminant)) / (2 * a), this)
            intersections(i1, i2)
        } else {
            emptyList()
        }
    }
}

data class Material(val color: Color = Color(1.0, 1.0, 1.0),
                    var ambient: Double = 0.1,
                    val diffuse: Double = 0.9,
                    val specular: Double = 0.9,
                    val shininess: Double = 200.0
)

fun normal_at(shape:Shape, worldPoint:Tuple):Tuple {
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

    if (lightDotNormal < 0 || inShadow) {
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