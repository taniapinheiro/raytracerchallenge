package com.tcmpinheiro.raytracerchallenge.features

import java.lang.Math.abs

class Plane(transform: Matrix = identityMatrix(), material: Material = Material()) : Shape(transform, material) {
    override fun local_intersect(ray: Ray): List<Intersection> {
        if (abs(ray.direction.y) < EPSILON){
            return emptyList() // empty set -- no intersections end if
        }
        val t = -ray.origin.y / ray.direction.y
        return listOf(Intersection(t, this))
    }

    override fun local_normal_at(point: Tuple): Tuple {
        return vector(0.0, 1.0, 0.0)
    }

}