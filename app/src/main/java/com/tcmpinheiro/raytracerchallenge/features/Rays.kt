package com.tcmpinheiro.raytracerchallenge.features


data class Ray(val origin:Tuple, val direction:Tuple)

data class Intersection(val t:Double, val target:TargetObject)

data class TargetObject(var transform: Matrix = identityMatrix())

fun position(ray: Ray, time: Double): Tuple {
    return ray.origin + ray.direction * time
}

fun sphere():TargetObject{
    return TargetObject()
}



fun intersect(targetObject: TargetObject, ray: Ray): List<Intersection> {
    //the vector from the targetObject's center, to the ray origin
    //remember: the targetObject is centered at the world origin sphere_to_ray ← ray.origin - point(0, 0, 0)
    val rayTransformed = transform(ray, targetObject.transform.inverse())
    val sphere_to_ray = rayTransformed.origin - point(0.0, 0.0, 0.0)
    val a = dot(rayTransformed.direction, rayTransformed.direction)
    val b = 2 * dot(rayTransformed.direction, sphere_to_ray)
    val c = dot(sphere_to_ray, sphere_to_ray) - 1
    val discriminant = b * b - 4 * a * c
    return if (discriminant >= 0) {
        val i1 = Intersection((-b - Math.sqrt(discriminant)) / (2 * a), targetObject)
        val i2 = Intersection(( -b + Math.sqrt(discriminant)) / (2 * a), targetObject)
        intersections(i1, i2)
    } else {
        emptyList()
    }
}

fun intersections(vararg intersections: Intersection): List<Intersection> {
    return intersections.asList()
}

fun hit(intersections: List<Intersection>): Intersection? {
    return intersections.filter { it.t > 0 }.minBy { it.t }
}

fun transform(ray: Ray, matrix: Matrix): Ray {
    return Ray( matrix * ray.origin, matrix * ray.direction)
}