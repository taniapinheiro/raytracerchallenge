package com.tcmpinheiro.raytracerchallenge.features

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

class WorldTest {

    /**
     * Scenario: Creating a world
     * Given w ← world()
     * Then w contains no objects
     * And w has no light source
     */
    @Test
    fun testNewWorld() {
        val w = World()
        assertEquals(0, w.objects.size)
        assertEquals(null, w.light)
    }

    /**
     * Scenario: The default world
     * Given light ← point_light(point(-10, 10, -10), color(1, 1, 1))
     * And s1 ← sphere() with:
     * | material.color | (0.8, 1.0, 0.6) |
     * | material.diffuse | 0.7 |
     * | material.specular | 0.2 |
     * And s2 ← sphere() with:
     * | transform
     * | scaling(0.5, 0.5, 0.5) |
     * When w ← default_world() Then w.light = light
     * And w contains s1 And w contains s2
     */
    @Test
    fun testDefaultWorld() {
        val light = PointLight(point(-10.0, 10.0, -10.0), Color(1.0, 1.0, 1.0))
        val s1 = sphere()
        s1.material = Material()
            .copy(color = Color(0.8, 1.0, 0.6),
                diffuse = 0.7,
                specular = 0.2)
        val s2 = sphere()
        s2.transform = scaling(0.5, 0.5, 0.5)
        val w = defaultWorld()
        assertEquals(light, w.light)
        assertTrue(w.objects.contains(s1))
        assertTrue(w.objects.contains(s2))
    }

    /**
     * Scenario: Intersect a world with a ray
     * Given w ← default_world()
     * And r ← ray(point(0, 0, -5), vector(0, 0, 1))
     * When xs ← intersect_world(w, r)
     * Then xs.count = 4
     * And xs[0].t = 4
     * And xs[1].t = 4.5
     * And xs[2].t = 5.5
     * And xs[3].t = 6
     */
    @Test
    fun testWorldIntersectWithRay() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val xs = intersect_world(w, r)
        assertEquals(4 , xs.count())
        assertEquals(4.0, xs[0].t)
        assertEquals(4.5, xs[1].t)
        assertEquals(5.5, xs[2].t)
        assertEquals(6.0, xs[3].t)

    }

    /**
     * Scenario: Precomputing the state of an intersection
     * Given r ← ray(point(0, 0, -5), vector(0, 0, 1))
     * And shape ← sphere()
     * And i ← intersection(4, shape)
     * When comps ← prepare_computations(i, r)
     * Then comps.t = i.t
     * And comps.object = i.object
     * And comps.point = point(0, 0, -1)
     * And comps.eyev = vector(0, 0, -1)
     * And comps.normalv = vector(0, 0, -1)
     */
    @Test
    fun testPreComputeIntersection() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = sphere()
        val i = Intersection(4.0, shape)
        val comps = prepare_computations(i, r)
        assertEquals(i.t, comps.t)
        assertEquals(i.target, comps.shape)
        assertEquals(point(0.0, 0.0, -1.0), comps.point)
        assertEquals(vector(-0.0, -0.0, -1.0), comps.eyev)
        assertEquals(vector(0.0, 0.0, -1.0), comps.normalv)
    }

    /**
     * Scenario: The hit, when an intersection occurs on the outside
     * Given r ← ray(point(0, 0, -5), vector(0, 0, 1))
     * And shape ← sphere()
     * And i ← intersection(4, shape)
     * When comps ← prepare_computations(i, r)
     * Then comps.inside = false
     */
    @Test
    fun testIntersectionOutside() {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = sphere()
        val i = Intersection(4.0, shape)
        val comps = prepare_computations(i, r)
        assertEquals(false, comps.inside)
    }

    /**
     * Scenario: The hit, when an intersection occurs on the inside
     * Given r ← ray(point(0, 0, 0), vector(0, 0, 1))
     * And shape ← sphere()
     * And i ← intersection(1, shape)
     * When comps ← prepare_computations(i, r)
     * Then comps.point = point(0, 0, 1)
     * And comps.eyev = vector(0, 0, -1)
     * And comps.inside = true
     * normal would have been (0, 0, 1), but is inverted!
     * And comps.normalv = vector(0, 0, -1)
     */
    @Test
    fun testIntersectionInside() {
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val shape = sphere()
        val i = Intersection(1.0, shape)
        val comps = prepare_computations(i, r)
        assertEquals(point(0.0, 0.0, 1.0), comps.point)
        assertEquals(vector(-0.0, -0.0, -1.0), comps.eyev)
        assertEquals(vector(0.0, 0.0, -1.0), comps.normalv)
        assertEquals(true, comps.inside)
    }

    /**
     * Scenario: Shading an intersection
     * Given w ← default_world()
     * And r ← ray(point(0, 0, -5), vector(0, 0, 1))
     * And shape ← the first object in w
     * And i ← intersection(4, shape)
     * When comps ← prepare_computations(i, r)
     * And c ← shade_hit(w, comps)
     * Then c = color(0.38066, 0.47583, 0.2855)
     */
    @Test
    fun testShadingIntersection() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = w.objects.first()
        val i = Intersection(4.0, shape)
        val comps = prepare_computations(i, r)
        val c = shadeHit(w, comps)
        assertEquals(Color(0.38066, 0.47583, 0.2855), c)
    }

    /**
     * Scenario: Shading an intersection from the inside
     * Given w ← default_world()
     * And w.light ← point_light(point(0, 0.25, 0), color(1, 1, 1))
     * And r ← ray(point(0, 0, 0), vector(0, 0, 1))
     * And shape ← the second object in w
     * And i ← intersection(0.5, shape)
     * When comps ← prepare_computations(i, r)
     * And c ← shade_hit(w, comps)
     * Then c = color(0.90498, 0.90498, 0.90498)
     */
    @Test
    fun testIntesectionInside() {
        val w = defaultWorld()
        w.light = PointLight(point(0.0, 0.25, 0.0), Color(1.0, 1.0, 1.0))
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val shape = w.objects.last()
        val i = Intersection(0.5, shape)
        val comps = prepare_computations(i, r)
        val c = shadeHit(w, comps)
        assertEquals(Color(0.90498, 0.90498, 0.90498), c)
    }

    /**
     * Scenario: The color when a ray misses
     * Given w ← default_world()
     * And r ← ray(point(0, 0, -5), vector(0, 1, 0))
     * When c ← color_at(w, r)
     * Then c = color(0, 0, 0)
     */
    @Test
    fun testRayMiss() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 1.0, 0.0))
        val c = colorAt(w, r)
        assertEquals(Color(0.0, 0.0, 0.0), c)
    }

    /**
     * Scenario: The color when a ray hits
     * Given w ← default_world()
     * And r ← ray(point(0, 0, -5), vector(0, 0, 1))
     * When c ← color_at(w, r)
     * Then c = color(0.38066, 0.47583, 0.2855)
     */
    @Test
    fun testRayHits() {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val c = colorAt(w, r)
        assertEquals(Color(0.38066, 0.47583, 0.2855), c)
    }

    /**
     * Scenario: The color with an intersection behind the ray
     * Given w ← default_world()
     * And outer ← the first object in w
     * And outer.material.ambient ← 1
     * And inner ← the second object in w
     * And inner.material.ambient ← 1
     * And r ← ray(point(0, 0, 0.75), vector(0, 0, -1))
     * When c ← color_at(w, r)
     * Then c = inner.material.color
     */
    @Test
    fun testIntersectionBehindRay() {
        val w = defaultWorld()
        val outer = w.objects.first()
        outer.material.ambient = 1.0
        val inner = w.objects.last()
        inner.material.ambient = 1.0
        val r = Ray(point(0.0, 0.0, 0.75), vector(0.0, 0.0, -1.0))
        val c = colorAt(w, r)
        assertEquals(inner.material.color, c)

    }


}