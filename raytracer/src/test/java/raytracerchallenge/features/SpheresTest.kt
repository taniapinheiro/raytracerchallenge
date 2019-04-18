package com.tcmpinheiro.raytracerchallenge.features

import junit.framework.Assert.assertEquals
import org.junit.Test
import kotlin.math.sqrt

class SpheresTest {

    val delta = 0.00001

    /**
     * Scenario: The normal on a sphere at a point on the x axis
     * Given s ← sphere()
     * When n ← normal_at(s, point(1, 0, 0))
     * Then n = vector(1, 0, 0)
     */
    @Test
    fun testSphereNormalX() {
        val s = Sphere()
        val n = s.normal_at(point(1.0, 0.0, 0.0))
        assertEquals(vector(1.0, 0.0, 0.0), n)
    }

    /**
     * Scenario: The normal on a sphere at a point on the y axis
     * Given s ← sphere()
     * When n ← normal_at(s, point(0, 1, 0))
     * Then n = vector(0, 1, 0)
     */
    @Test
    fun testSphereNormalY() {
        val s = Sphere()
        val n = s.normal_at(point(0.0, 1.0, 0.0))
        assertEquals(vector(0.0, 1.0, 0.0), n)
    }

    /**
     * Scenario: The normal on a sphere at a point on the z axis
     * Given s ← sphere()
     * When n ← normal_at(s, point(0, 0, 1))
     * Then n = vector(0, 0, 1)
     */
    @Test
    fun testSphereNormalZ() {
        val s = Sphere()
        val n = s.normal_at(point(0.0, 0.0, 1.0))
        assertEquals(vector(0.0, 0.0, 1.0), n)
    }

    /**
     * Scenario: The normal on a sphere at a non-axial point
     * Given s ← sphere()
     * When n ← normal_at(s, point(√3/3, √3/3, √3/3))
     * Then n = vector(√3/3, √3/3, √3/3)
     */
    @Test
    fun testSphereNormal() {
        val s = Sphere()
        val i = Math.sqrt(3.0)/3
        val n = s.normal_at(point(i, i, i))
        assertEquals(vector(i, i, i), n)
    }

    /**
     * Scenario: The normal is a normalized vector
     * Given s ← sphere()
     * When n ← normal_at(s, point(√3/3, √3/3, √3/3))
     * Then n = normalize(n)
     */
    @Test
    fun testNormalIsNormalizedVector() {
        val s = Sphere()
        val i = Math.sqrt(3.0)/3
        val n = s.normal_at(point(i, i, i))
        assertEquals(normalize(n), n)
    }

    /**
     * Scenario: Computing the normal on a translated shape
     * Given s ← Sphere()
     * And set_transform(s, translation(0, 1, 0))
     * When n ← normal_at(s, point(0, 1.70711, -0.70711))
     * Then n = vector(0, 0.70711, -0.70711)
     */
    @Test
    fun testNormalTranslatedSphere() {
        val s = Sphere(translation(0.0, 1.0, 0.0))
        val n = s.normal_at(point(0.0, 1.70711, -0.70711))
        assertEquals(vector(0.0, 0.70711, -0.70711), n)
    }

    /**
     * Scenario: Computing the normal on a transformed sphere
     * Given s ← sphere()
     * And m ← scaling(1, 0.5, 1) * rotation_z(π/5)
     * And set_transform(s, m)
     * When n ← normal_at(s, point(0, √2/2, -√2/2))
     * Then n = vector(0, 0.97014, -0.24254)
     */
    @Test
    fun testNormalTransformedSphere() {
        val t  = scaling(1.0, 0.5, 1.0) * rotationZ((Math.PI/5))
        val s = Sphere(t)
        val n = s.normal_at(point(0.0, Math.sqrt(2.0)/2, -Math.sqrt(2.0)/2))
        assertEquals(vector(0.0, 0.97014, -0.25254), n)
    }

    /**
     * Scenario: Reflecting a vector approaching at 45°
     * Given v ← vector(1, -1, 0)
     * And n ← vector(0, 1, 0)
     * When r ← reflect(v, n)
     * Then r = vector(1, 1, 0)
     */
    @Test
    fun testVectorReflection45() {
        val v = vector(1.0, -1.0, 0.0)
        val n = vector(0.0, 1.0, 0.0)
        val r = reflect(v, n)
        assertEquals(vector(1.0, 1.0, 0.0), r)
    }

    /**
     * Scenario: Reflecting a vector off a slanted surface
     * Given v ← vector(0, -1, 0)
     * And n ← vector(√2/2, √2/2, 0)
     * When r ← reflect(v, n)
     * Then r = vector(1, 0, 0)
     */
    @Test
    fun testVectorReflectionSlantedSurface() {
        val v = vector(0.0, -1.0, 0.0)
        val n = vector(Math.sqrt(2.0)/2, Math.sqrt(2.0)/2, 0.0)
        val r = reflect(v, n)
        assertEquals(vector(1.0, 0.0, 0.0), r)
    }

    /**
     * Scenario: A point light has a position and intensity
     * Given intensity ← color(1, 1, 1)
     * And position ← point(0, 0, 0)
     * When light ← point_light(position, intensity)
     * Then light.position = position
     * And light.intensity = intensity
     */
    @Test
    fun testPointLight() {
        val intensity = Color(1.0, 1.0, 1.0)
        val position = point(0.0, 0.0, 0.0)
        val light = PointLight(position, intensity)
        assertEquals(position, light.position)
        assertEquals(intensity, light.intensity)
    }

    /**
     * Scenario: The default material
     * Given m ← material()
     * Then m.color = color(1, 1, 1)
     * And m.ambient = 0.1
     * And m.diffuse = 0.9
     * And m.specular = 0.9
     * And m.shininess = 200.0
     */
    @Test
    fun testDefaultMaterial() {
        val m = Material()
        assertEquals(Color(1.0, 1.0, 1.0), m.color)
        assertEquals(0.1, m.ambient)
        assertEquals(0.9, m.diffuse)
        assertEquals(0.9, m.specular)
        assertEquals(200.0, m.shininess)
    }

    /**
     * Scenario: The default material
     * Given s ← Shape()
     * When m ← s.material
     * Then m = material()
     */
    @Test
    fun testShapeDefaultMaterial() {
        val s = Sphere()
        assertEquals(Material(), s.material)
    }

    /**
     * Scenario: A sphere may be assigned a material
     * Given s ← sphere()
     * And m ← material()
     * And m.ambient ← 1
     * When s.material ← m
     * Then s.material = m
     */
    @Test
    fun testSphereAssignMaterial() {
        val s = Sphere()
        val m = Material()
        val mm = m.copy(ambient = 1.0)
        s.material = mm
        assertEquals(mm ,s.material)
    }

    val m = Material()
    val position = point(0.0, 0.0, 0.0)

    /**
     * Scenario: Lighting with the eye between the light and the surface
     * Given eyev ← vector(0, 0, -1)
     * And normalv ← vector(0, 0, -1)
     * And light ← point_light(point(0, 0, -10), color(1, 1, 1))
     * When result ← lighting(m, light, position, eyev, normalv)
     * Then result = color(1.9, 1.9, 1.9)
     */
    @Test
    fun testLightWithEyeBetweenLightandSurface() {
        val eyeV = vector(0.0, 0.0, -1.0)
        val normalV = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), Color(1.0, 1.0, 1.0))
        val result = lighting(m, Sphere(),light, position, eyeV, normalV)
        assertEquals(Color(1.9, 1.9, 1.9), result)
    }

    /**
     * Scenario: Lighting with the eye between light and surface, eye offset 45°
     * Given eyev ← vector(0, √2/2, -√2/2)
     * And normalv ← vector(0, 0, -1)
     * And light ← point_light(point(0, 0, -10), color(1, 1, 1))
     * When result ← lighting(m, light, position, eyev, normalv)
     * Then result = color(1.0, 1.0, 1.0)
     */
    @Test
    fun testLightWithEyeOffset45() {
        val eyeV = vector(0.0, Math.sqrt(2.0)/2, -Math.sqrt(2.0)/2)
        val normalV = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), Color(1.0, 1.0, 1.0))
        val result = lighting(m,  Sphere(), light, position, eyeV, normalV)
        assertEquals(Color(1.0, 1.0, 1.0), result)
    }

    /**
     * Scenario: Lighting with eye opposite surface, light offset 45°
     * Given eyev ← vector(0, 0, -1)
     * And normalv ← vector(0, 0, -1)
     * And light ← point_light(point(0, 10, -10), color(1, 1, 1))
     * When result ← lighting(m, light, position, eyev, normalv)
     * Then result = color(0.7364, 0.7364, 0.7364)
     */
    @Test
    fun testLightOffset45() {
        val eyeV = vector(0.0, 0.0, -1.0)
        val normalV = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 10.0, -10.0), Color(1.0, 1.0, 1.0))
        val result = lighting(m,  Sphere(), light, position, eyeV, normalV)
        assertEquals(Color(0.7364, 0.7364, 0.7364), result)
    }

    /**
     * Scenario: Lighting with eye in the path of the reflection vector
     * Given eyev ← vector(0, -√2/2, -√2/2)
     * And normalv ← vector(0, 0, -1)
     * And light ← point_light(point(0, 10, -10), color(1, 1, 1))
     * When result ← lighting(m, light, position, eyev, normalv)
     * Then result = color(1.6364, 1.6364, 1.6364)
     */
    @Test
    fun testLightWithEyeAsReflectionVector() {
        val eyeV = vector(0.0, -Math.sqrt(2.0)/2, -Math.sqrt(2.0)/2)
        val normalV = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 10.0, -10.0), Color(1.0, 1.0, 1.0))
        val result = lighting(m, Sphere(),  light, position, eyeV, normalV)
        assertEquals(Color(1.6364, 1.6364, 1.6364), result)
    }


    /**
     * Scenario: Lighting with the light behind the surface
     * Given eyev ← vector(0, 0, -1)
     * And normalv ← vector(0, 0, -1)
     * And light ← point_light(point(0, 0, 10), color(1, 1, 1))
     * When result ← lighting(m, light, position, eyev, normalv)
     * Then result = color(0.1, 0.1, 0.1)
     */
    @Test
    fun testLightBehindSurface() {
        val eyeV = vector(0.0, 0.0, -1.0)
        val normalV = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), Color(1.0, 1.0, 1.0))
        val result = lighting(m,  Sphere(), light, position, eyeV, normalV)
        assertEquals(Color(0.1, 0.1, 0.1), result)
    }

    /**
     * Scenario: Lighting with the surface in shadow
     * Given eyev ← vector(0, 0, -1)
     * And normalv ← vector(0, 0, -1)
     * And light ← point_light(point(0, 0, -10), color(1, 1, 1))
     * And in_shadow ← true
     * When result ← lighting(m, light, position, eyev, normalv, in_shadow)
     * Then result = color(0.1, 0.1, 0.1)
     */
    @Test
    fun testLightingWithShadowSurface() {
        val eyeV = vector(0.0, 0.0, -1.0)
        val normalV = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), Color(1.0, 1.0, 1.0))
        val inShadow = true
        val result = lighting(m, Sphere(), light, position, eyeV, normalV, inShadow)
        assertEquals(Color(0.1, 0.1, 0.1), result)
    }

    /**
     * Scenario: Reflectivity for the default material
     * Given m ← material()
     * Then m.reflective = 0.0
     */
    @Test
    fun testMaterialReflectivity() {
        val m = Material()
        assertEquals(0.0, m.reflective, delta)
    }

    /**
     * Scenario: Precomputing the reflection vector
     * Given shape ← plane()
     * And r ← ray(point(0, 1, -1), vector(0, -√2/2, √2/2))
     * And i ← intersection(√2, shape)
     * When comps ← prepare_computations(i, r)
     * Then comps.reflectv = vector(0, √2/2, √2/2)
     */
    @Test
    fun testPreComputeReflectionVector() {
        val shape = Plane()
        val r = Ray(point(0.0, 1.0, -1.0), vector(0.0, -sqrt(2.0)/2, sqrt(2.0)/2))
        val i = Intersection(sqrt(2.0), shape)
        val comps = prepare_computations(i, r)
        assertEquals(vector(0.0, sqrt(2.0)/2, sqrt(2.0)/2), comps.reflectv)
    }
}