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
    val shape = sphere()

    //sphere.material ← material()
    // sphere.material.color ← color(1, 0.2, 1)
    shape.material = Material(Color(1.0, 0.2, 1.0))

    //add light
    val lightPosition = point(-10.0, 10.0, -20.0)
    val lightColor = Color(1.0, 1.0, 1.0)
    val light = PointLight(lightPosition, lightColor)


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

                //point ← position(ray, hit.t)
                val point = position(r, it.t)
                //normal ← normal_at(hit.object, point)
                val normal = normal_at(it.target, point)
                //eye ← -ray.direction
                val eye = -r.direction

                //color ← lighting(hit.object.material, light, point, eye, normal)
                val color = lighting(it.target.material, light, point, eye, normal)
                write_pixel(canvas, x, y, color)
            }
        }

    }
    File("raysphere.ppm").writeText(canvas_to_ppm(canvas))
}