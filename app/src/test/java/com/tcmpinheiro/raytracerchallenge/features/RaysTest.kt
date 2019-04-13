package com.tcmpinheiro.raytracerchallenge.features

import org.junit.Assert.*
import org.junit.Test


class RaysTest {

    val delta = 0.00001

    /**
     * Scenario: Creating and querying a ray
     * Given origin ← point(1, 2, 3)
     * And direction ← vector(4, 5, 6)
     * When r ← ray(origin, direction)
     * Then r.origin = origin
     * And r.direction = direction
     */
    @Test
    fun testCreateRay() {
        val origin = point(1.0, 2.0, 3.0)
        val direction = vector(4.0, 5.0, 6.0)
        val r = Ray(origin, direction)
        assertEquals(origin, r.origin)
        assertEquals(direction, r.direction)
    }

    /**
     * Scenario: Computing a point from a distance
     * Given r ← ray(point(2, 3, 4), vector(1, 0, 0))
     * Then position(r, 0) = point(2, 3, 4)
     * And position(r, 1) = point(3, 3, 4)
     * And position(r, -1) = point(1, 3, 4)
     * And position(r, 2.5) = point(4.5, 3, 4)
     */
    @Test
    fun testComputingPoint() {
        val r = Ray(point(2.0, 3.0, 4.0), vector(1.0, 0.0, 0.0))
        assertEquals(point(2.0, 3.0, 4.0), position(r, 0.0))
        assertEquals(point(3.0, 3.0, 4.0), position(r, 1.0))
        assertEquals(point(4.5, 3.0, 4.0), position(r, 2.5))
    }

    /**
     * Scenario: A ray intersects a sphere at two points
     * Given r ← ray(point(0, 0, -5), vector(0, 0, 1))
     * And s ← sphere()
     * When xs ← intersect(s, r)
     * Then xs.count = 2
     * And xs[0] = 4.0
     * And xs[1] = 6.0
     */
    @Test
    fun testSphereIntersection() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()
        val xs = s.intersect(r)
        assertEquals(2, xs.count())
        assertEquals(4.0, xs[0].t, delta)
        assertEquals(6.0, xs[1].t, delta)
    }

    /**
     * Scenario: A ray intersects a sphere at a tangent
     * Given r ← ray(point(0, 1, -5), vector(0, 0, 1))
     * And s ← sphere()
     * When xs ← intersect(s, r)
     * Then xs.count = 2
     * And xs[0] = 5.0 And xs[1] = 5.0
     */
    @Test
    fun testSphereIntersectionTangent() {
        val r = Ray(point(0.0, 1.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()
        val xs = s.intersect(r)
        assertEquals(2, xs.count())
        assertEquals(5.0, xs[0].t, delta)
        assertEquals(5.0, xs[1].t, delta)
    }

    /**
     * Scenario: A ray misses a sphere
     * Given r ← ray(point(0, 2, -5), vector(0, 0, 1))
     * And s ← sphere()
     * When xs ← intersect(s, r)
     * Then xs.count = 0
     */
    @Test
    fun testSphereIntersectionMiss() {
        val r = Ray(point(0.0, 2.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()
        val xs = s.intersect(r)
        assertEquals(0, xs.count())
    }

    /**
     * Scenario: A ray originates inside a sphere
     * Given r ← ray(point(0, 0, 0), vector(0, 0, 1))
     * And s ← sphere()
     * When xs ← intersect(s, r)
     * Then xs.count = 2
     * And xs[0] = -1.0
     * And xs[1] = 1.0
     */
    @Test
    fun testRayOriginatesInsideSphere() {
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()
        val xs = s.intersect(r)
        assertEquals(2, xs.count())
        assertEquals(-1.0, xs[0].t, delta)
        assertEquals(1.0, xs[1].t, delta)
    }

    /**
     * Scenario: A sphere is behind a ray
     * Given r ← ray(point(0, 0, 5), vector(0, 0, 1))
     * And s ← sphere()
     * When xs ← intersect(s, r)
     * Then xs.count = 2
     * And xs[0] = -6.0
     * And xs[1] = -4.0
     */
    @Test
    fun testRayBehindSphere() {
        val r = Ray(point(0.0, 0.0, 5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()
        val xs = s.intersect(r)
        assertEquals(2, xs.count())
        assertEquals(-6.0, xs[0].t, delta)
        assertEquals(-4.0, xs[1].t, delta)
    }

    /**
     * Scenario: An intersection encapsulates t and object
     * Given s ← sphere()
     * When i ← intersection(3.5, s)
     * Then i.t = 3.5
     * And i.object = s
     */
    @Test
    fun testCreateIntersection() {
        val s = Sphere()
        val i = Intersection(3.5, s)
        assertEquals(3.5, i.t, delta)
        assertEquals(s, i.target)
    }

    /**
     * Scenario: Aggregating intersections
     * Given s ← sphere()
     * And i1 ← intersection(1, s)
     * And i2 ← intersection(2, s)
     * When xs ← intersections(i1, i2)
     * Then xs.count = 2
     * And xs[0].t = 1 And xs[1].t = 2
     */
    @Test
    fun testAggregateIntersections() {
        val s = Sphere()
        val i1 = Intersection(1.0, s)
        val i2 = Intersection(2.0, s)
        val xs = intersections(i1, i2)
        assertEquals(2, xs.count())
        assertEquals(1.0, xs[0].t, delta)
        assertEquals(2.0, xs[1].t, delta)
    }

    /**
     * Scenario: Intersect sets the object on the intersection
     * Given r ← ray(point(0, 0, -5), vector(0, 0, 1))
     * And s ← sphere()
     * When xs ← intersect(s, r)
     * Then xs.count = 2
     * And xs[0].object = s
     * And xs[1].object = s
     */
    @Test
    fun testIntersectSetsObjectOnIntersection() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()
        val xs = s.intersect(r)
        assertEquals(2, xs.count())
        assertEquals(s, xs[0].target)
        assertEquals(s, xs[1].target)
    }

    /**
     * Scenario: The hit, when all intersections have positive t
     * Given s ← sphere()
     * And i1 ← intersection(1, s)
     * And i2 ← intersection(2, s)
     * And xs ← intersections(i2, i1)
     * When i ← hit(xs)
     * Then i = i1
     */
    @Test
    fun testIntersectionsAllPositive() {
        val s = Sphere()
        val i1 = Intersection(1.0, s)
        val i2 = Intersection(2.0, s)
        val xs = intersections(i1, i2)
        val i = hit(xs)
        assertEquals(i1, i)
    }

    /**
     * Scenario: The hit, when some intersections have negative t
     * Given s ← sphere()
     * And i1 ← intersection(-1, s)
     * And i2 ← intersection(1, s)
     * And xs ← intersections(i2, i1)
     * When i ← hit(xs) Then i = i2
     */
    @Test
    fun testIntersectionsSomeNegative() {
        val s = Sphere()
        val i1 = Intersection(-1.0, s)
        val i2 = Intersection(1.0, s)
        val xs = intersections(i2, i1)
        val i = hit(xs)
        assertEquals(i2, i)
    }

    /**
     * Scenario: The hit, when all intersections have negative t
     * Given s ← sphere()
     * And i1 ← intersection(-2, s)
     * And i2 ← intersection(-1, s)
     * And xs ← intersections(i2, i1)
     * When i ← hit(xs)
     * Then i is nothing
     */
    @Test
    fun testIntersectionsAllNegative() {
        val s = Sphere()
        val i1 = Intersection(-2.0, s)
        val i2 = Intersection(-1.0, s)
        val xs = intersections(i2, i1)
        val i = hit(xs)
        assertEquals(null, i)
    }

    /**
     * Scenario: The hit is always the lowest non-negative intersection
     * Given s ← sphere()
     * And i1 ← intersection(5, s)
     * And i2 ← intersection(7, s)
     * And i3 ← intersection(-3, s)
     * And i4 ← intersection(2, s)
     * And xs ← intersections(i1, i2, i3, i4)
     * When i ← hit(xs) Then i = i4
     */
    @Test
    fun testHitIsLowestNonNegativeIntersection() {
        val s = Sphere()
        val i1 = Intersection(5.0, s)
        val i2 = Intersection(7.0, s)
        val i3 = Intersection(-3.0, s)
        val i4 = Intersection(2.0, s)
        val xs = intersections(i1, i2, i3, i4)
        val i = hit(xs)
        assertEquals(i4, i)
    }

    /**
     * Scenario: Translating a ray
     * Given r ← ray(point(1, 2, 3), vector(0, 1, 0))
     * And m ← translation(3, 4, 5)
     * When r2 ← transform(r, m)
     * Then r2.origin = point(4, 6, 8)
     * And r2.direction = vector(0, 1, 0)
     */
    @Test
    fun testTranslateRay() {
        val r = Ray(point(1.0, 2.0, 3.0), vector(0.0, 1.0, 0.0))
        val m = translation(3.0, 4.0, 5.0)
        val r2 = transform(r, m)
        assertEquals(point(4.0, 6.0, 8.0), r2.origin)
        assertEquals(vector(0.0, 1.0, 0.0), r2.direction)
    }

    /**
     * Scenario: Scaling a ray
     * Given r ← ray(point(1, 2, 3), vector(0, 1, 0))
     * And m ← scaling(2, 3, 4)
     * When r2 ← transform(r, m)
     * Then r2.origin = point(2, 6, 12)
     * And r2.direction = vector(0, 3, 0)
     */
    @Test
    fun testScalingRay() {
        val r = Ray(point(1.0, 2.0, 3.0), vector(0.0, 1.0, 0.0))
        val m = scaling(2.0, 3.0, 4.0)
        val r2 = transform(r, m)
        assertEquals(point(2.0, 6.0, 12.0), r2.origin)
        assertEquals(vector(0.0, 3.0, 0.0), r2.direction)
    }

    /**
     * Scenario: The default transformation
     * Given s ← sphere()
     * Then s.transform = identity_matrix
     */
    @Test
    fun testSphereDefaultTransformation() {
        val s = Sphere()
        assertEquals(identityMatrix(), s.transform)
    }

    /**
     * Scenario: Changing a sphere's transformation
     * Given s ← sphere()
     * And t ← translation(2, 3, 4)
     * When set_transform(s, t)
     * Then s.transform = t
     */
    @Test
    fun testChangeSphereTransformation() {
        val t = translation(2.0, 3.0, 4.0)
        val s = Sphere(t)
        assertEquals(t, s.transform)
    }

    /**
     * Scenario: Intersecting a scaled shape with a ray
     * Given r ← ray(point(0, 0, -5), vector(0, 0, 1))
     * And s ← sphere()
     * When set_transform(s, scaling(2, 2, 2))
     * And xs ← intersect(s, r)
     * Then s.saved_ray.origin = point(0, 0, -2.5)
     * And s.saved_ray.direction = vector(0, 0, 0.5)
     */
    @Test
    fun testIntersectScaledSphere() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere()
        s.transform = scaling(2.0, 2.0, 2.0)
        val xs = s.intersect(r)
        assertEquals(point(0.0, 0.0, -2.5), s.localRay.origin)
        assertEquals(vector(0.0, 0.0, 0.5), s.localRay.direction)
    }
    /**
     * Scenario: Intersecting a translated shape with a ray
     * Given r ← ray(point(0, 0, -5), vector(0, 0, 1))
     * And s ← Sphere()
     * When set_transform(s, translation(5, 0, 0))
     * And xs ← intersect(s, r)
     * Then s.saved_ray.origin = point(-5, 0, -5)
     * And s.saved_ray.direction = vector(0, 0, 1)
     */
    @Test
    fun testIntersectTranslatedSphere() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val s = Sphere(translation(5.0, 0.0, 0.0))
        val xs = s.intersect(r)
        assertEquals(point(-5.0, 0.0, -5.0), s.localRay.origin)
        assertEquals(vector(0.0, 0.0, 1.0), s.localRay.direction)
    }


}