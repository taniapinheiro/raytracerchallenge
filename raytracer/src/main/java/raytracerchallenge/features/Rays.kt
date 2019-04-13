package com.tcmpinheiro.raytracerchallenge.features


data class Ray(val origin:Tuple, val direction:Tuple)

data class Intersection(val t:Double, val target:Shape)

fun position(ray: Ray, time: Double): Tuple {
    return ray.origin + ray.direction * time
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