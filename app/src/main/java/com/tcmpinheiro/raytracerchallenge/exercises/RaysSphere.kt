package com.tcmpinheiro.raytracerchallenge.exercises

import com.tcmpinheiro.raytracerchallenge.features.*
import java.io.File

fun main() {

    // start the ray at z = -5
    val rayOrigin = point(0.0, 0.0, -5.0)

    // put the wall at z = 10
    val wallZ = 10.0

    //with wall at z = 10, it needs to be at least 6 units across
    // in order to capture the sphere’s entire shadow.
    // Give yourself a bit of margin, and call it 7.
    // (Just assume the wall is a square.)
    val wallSize = 7.0

    val canvasPixels = 100

    val pixelSize = wallSize / canvasPixels

    val half = wallSize /2

    val canvas = Canvas(canvasPixels, canvasPixels)
    val color = Color(1.0, 0.0, 0.0)
    val shape = sphere()

    //for each row of pixels in the canvas
    for (y in 0 until canvasPixels - 1) {
        // compute the world y coordinate (top = +half, bottom = -half)
        val world_y = half - pixelSize * y

        // for each pixel in the row
        for (x in 0 until canvasPixels - 1) {

            // compute the world x coordinate (left = -half, right = half)
            val world_x = -half + pixelSize * x

            //describe the point on the wall that the ray will target
            val position = point(world_x, world_y, wallZ)

            val r = Ray(rayOrigin, normalize(position - rayOrigin))

            hit(intersect(shape, r))?.let {
                write_pixel(canvas, x, y, color)
            }
        }

    }
    File("raysphere.ppm").writeText(canvas_to_ppm(canvas))
}