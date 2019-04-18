package com.tcmpinheiro.raytracerchallenge.features

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.PI

class CameraTest {

    val delta = 0.00001

    /**
     * Scenario: Constructing color camera
     * Given hsize ← 160
     * And vsize ← 120
     * And field_of_view ← π/2
     * When c ← camera(hsize, vsize, field_of_view)
     * Then c.hsize = 160
     * And c.vsize = 120
     * And c.field_of_view = π/2
     * And c.transform = identity_matrix
     */
    @Test
    fun testDefaultCamera() {
        val hsize = 160
        val vsize = 120
        val fieldOfView = Math.PI /2
        val c = Camera(hsize, vsize, fieldOfView)
        assertEquals(160, c.hsize)
        assertEquals(120, c.vsize)
        assertEquals(Math.PI/2, c.fieldOfView, delta)
        assertEquals(identityMatrix(), c.transform)

    }

    /**
     * Scenario: The pixel size for a horizontal canvas
     * Given c ← camera(200, 125, π/2)
     * Then c.pixel_size = 0.01
     */
    @Test
    fun testCameraHorizontalCameraPixelSize() {
        val c = Camera(200, 125, Math.PI /2)
        assertEquals(0.01, c.pixelSize, delta)
    }

    /**
     * Scenario: The pixel size for a vertical canvas
     * Given c ← camera(125, 200, π/2)
     * Then c.pixel_size = 0.01
     */
    @Test
    fun testCameraVerticalCameraPixelSize() {
        val c = Camera(125, 200, Math.PI /2)
        assertEquals(0.01, c.pixelSize, delta)
    }

    /**
     * Scenario: Constructing a ray through the center of the canvas
     * Given c ← camera(201, 101, π/2)
     * When r ← ray_for_pixel(c, 100, 50)
     * Then r.origin = point(0, 0, 0)
     * And r.direction = vector(0, 0, -1)
     */
    @Test
    fun testRayThroughCanvasCenter() {
        val c = Camera(201, 101, Math.PI / 2)
        val r = ray_for_pixel(c, 100, 50)
        assertEquals(point(0.0, 0.0, 0.0), r.origin)
        assertEquals(vector(0.0, 0.0, -1.0), r.direction)
    }
    /**
     * Scenario: Constructing a ray through a corner of the canvas
     * Given c ← camera(201, 101, π/2)
     * When r ← ray_for_pixel(c, 0, 0)
     * Then r.origin = point(0, 0, 0)
     * And r.direction = vector(0.66519, 0.33259, -0.66851)
     */
    @Test
    fun testRayThroughCanvasCorner() {
        val c = Camera(201, 101, Math.PI / 2)
        val r = ray_for_pixel(c, 0, 0)
        assertEquals(point(0.0, 0.0, 0.0), r.origin)
        assertEquals(vector(0.66519, 0.33259, -0.66851), r.direction)
    }

    /**
     * Scenario: Constructing a ray when the camera is transformed
     * Given c ← camera(201, 101, π/2)
     * When c.transform ← rotation_y(π/4) * translation(0, -2, 5)
     * And r ← ray_for_pixel(c, 100, 50)
     * Then r.origin = point(0, 2, -5)
     * And r.direction = vector(√2/2, 0, -√2/2)
     */
    @Test
    fun testRayInTransformedCamera() {
        val c = Camera(201, 101, Math.PI / 2)
        c.transform = rotationY(Math.PI /4) * translation(0.0, -2.0, 5.0)
        val r = ray_for_pixel(c, 100, 50)
        assertEquals(point(0.0, 2.0, -5.0), r.origin)
        assertEquals(vector(Math.sqrt(2.0)/2.0, 0.0, -Math.sqrt(2.0)/2.0), r.direction)
    }

    /**
     * Scenario: Rendering a world with a camera
     * Given w ← default_world()
     * And c ← camera(11, 11, π/2)
     * And from ← point(0, 0, -5)
     * And to ← point(0, 0, 0)
     * And up ← vector(0, 1, 0)
     * And c.transform ← view_transform(from, to, up)
     * When image ← render(c, w)
     * Then pixel_at(image, 5, 5) = color(0.38066, 0.47583, 0.2855)
     */
    @Test
    fun testCameraRender() {
        val w = defaultWorld()
        val c = Camera(11, 11, PI /2)
        val from = point(0.0, 0.0, -5.0)
        val to = point(0.0, 0.0, 0.0)
        val up = vector(0.0, 1.0, 0.0)
        c.transform = viewTransform(from, to, up)
        val image = render(c, w)
        assertEquals(Color(0.38066, 0.47583, 0.2855), pixel_at(image, 5, 5))
    }
}