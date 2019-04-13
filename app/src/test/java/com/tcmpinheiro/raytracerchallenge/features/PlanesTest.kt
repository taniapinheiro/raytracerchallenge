package com.tcmpinheiro.raytracerchallenge.features

import org.junit.Assert.assertEquals
import org.junit.Test

class PlanesTest {

    val delta = 0.00001

    /**
     * Scenario: The normal of a plane is constant everywhere
     * Given p ← plane()
     * When n1 ← local_normal_at(p, point(0, 0, 0))
     * And n2 ← local_normal_at(p, point(10, 0, -10))
     * And n3 ← local_normal_at(p, point(-5, 0, 150))
     * Then n1 = vector(0, 1, 0)
     * And n2 = vector(0, 1, 0)
     * And n3 = vector(0, 1, 0)
     */
    @Test
    fun testNormalOfPlaneIsConstant() {
        val p = Plane()
        val n1 = p.local_normal_at(point(0.0, 0.0, 0.0))
        val n2 = p.local_normal_at(point(10.0, 0.0, -10.0))
        val n3 = p.normal_at(point(-5.0, 0.0, 150.0))
        assertEquals(vector(0.0, 1.0, 0.0), n1)
        assertEquals(vector(0.0, 1.0, 0.0), n2)
        assertEquals(vector(0.0, 1.0, 0.0), n3)
    }

    /**
     * Scenario: Intersect with a ray parallel to the plane
     * Given p ← plane()
     * And r ← ray(point(0, 10, 0), vector(0, 0, 1))
     * When xs ← local_intersect(p, r)
     * Then xs is empty
     */
    @Test
    fun testIntersectRayParallelToPlane() {
        val p = Plane()
        val r = Ray(point(0.0, 10.0, 0.0), vector(0.0, 0.0, 1.0))
        val xs = p.local_intersect(r)
        assertEquals(0, xs.count())
    }

    /**
     * Scenario: Intersect with a coplanar ray
     * Given p ← plane()
     * And r ← ray(point(0, 0, 0), vector(0, 0, 1))
     * When xs ← local_intersect(p, r)
     * Then xs is empty
     */
    @Test
    fun testIntersectCoplanarRay() {
        val p = Plane()
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val xs = p.local_intersect(r)
        assertEquals(0, xs.count())
    }

    /**
     * Scenario: A ray intersecting a plane from above
     * Given p ← plane()
     * And r ← ray(point(0, 1, 0), vector(0, -1, 0))
     * When xs ← local_intersect(p, r)
     * Then xs.count = 1
     * And xs[0].t = 1
     * And xs[0].object = p
     */
    @Test
    fun testRayIntersectsPlaneFromAbove() {
        val p = Plane()
        val r = Ray(point(0.0, 1.0, 0.0), vector(0.0, -1.0, 0.0))
        val xs = p.local_intersect(r)
        assertEquals(1, xs.count())
        assertEquals(1.0, xs[0].t, delta)
        assertEquals(p, xs[0].target)
    }

    /**
     * Scenario: A ray intersecting a plane from below
     * Given p ← plane()
     * And r ← ray(point(0, -1, 0), vector(0, 1, 0))
     * When xs ← local_intersect(p, r)
     * Then xs.count = 1
     * And xs[0].t = 1
     * And xs[0].object = p
     */
    @Test
    fun testRayIntersectsFromBelow() {
        val p = Plane()
        val r = Ray(point(0.0, -1.0, 0.0), vector(0.0, 1.0, 0.0))
        val xs = p.local_intersect(r)
        assertEquals(1, xs.count())
        assertEquals(1.0, xs[0].t, delta)
        assertEquals(p, xs[0].target)
    }
}