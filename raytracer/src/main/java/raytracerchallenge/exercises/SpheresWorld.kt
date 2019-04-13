package com.tcmpinheiro.raytracerchallenge.exercises

import com.tcmpinheiro.raytracerchallenge.features.*
import java.io.File
import kotlin.math.PI


class SpheresWorld {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val floor = Plane()
            floor.material = Material(
                color = Color(1.0, 0.9, 0.9),
                specular = 0.0
            )

            val middle = Sphere()
            middle.transform = translation(-0.5, 1.0, 0.5)
            middle.material = Material(color = Color(0.1, 1.0, 0.5), diffuse = 0.7, specular = 0.3)

            val right = Sphere()
            right.transform = translation(1.5, 0.5, -0.5) * scaling(0.5, 0.5, 0.5)
            right.material = Material(color = Color(0.5, 1.0, 0.1), diffuse = 0.7, specular = 0.3)


            val left = Sphere()
            left.transform = translation(-1.5, 0.33, -0.75) * scaling(0.33, 0.33, 0.33)
            left.material = Material(color = Color(1.0, 0.8, 0.1), diffuse = 0.7, specular = 0.3)

            val world = World(PointLight(point(-10.0, 10.0, -10.0), Color(1.0, 1.0, 1.0)))
            world.objects.addAll(setOf(floor, middle, left, right))

            val camera = Camera(200, 100, PI / 3)
            camera.transform = viewTransform(
                point(0.0, 1.5, -5.0), point(0.0, 1.0, 0.0),
                vector(0.0, 1.0, 0.0)
            )
            val canvas = render(camera, world)

            File("sphereworld.ppm").writeText(canvas_to_ppm(canvas))
        }
    }
}