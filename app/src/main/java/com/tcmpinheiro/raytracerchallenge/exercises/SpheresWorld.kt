package com.tcmpinheiro.raytracerchallenge.exercises

import com.tcmpinheiro.raytracerchallenge.features.*
import java.io.File
import kotlin.math.PI

fun main() {

    val floor = Sphere()
    floor.transform = scaling(10.0, 0.01, 10.0)
    floor.material = Material(color = Color(1.0, 0.9, 0.9),
            specular = 0.0)

    val left_wall = Sphere()
    left_wall.transform = translation(0.0, 0.0, 5.0) *
                    rotationY(-PI/4) *
                    rotationX(PI/2) *
                    scaling(10.0, 0.01, 10.0)
    left_wall.material = floor.material

    val right_wall = Sphere()
    right_wall.transform = translation(0.0, 0.0, 5.0) *
                    rotationY(PI/4) *
                    rotationX(PI/2) *
                    scaling(10.0, 0.01, 10.0)
    right_wall.material = floor.material

    val middle = Sphere()
    middle.transform = translation(-0.5, 1.0, 0.5)
    middle.material = Material(color = Color(0.1, 1.0, 0.5), diffuse = 0.7,  specular = 0.3)

    val right = Sphere()
    right.transform = translation(1.5, 0.5, -0.5) * scaling(0.5, 0.5, 0.5)
    right.material = Material(color = Color(0.5, 1.0, 0.1), diffuse = 0.7, specular = 0.3)


    val left = Sphere()
    left.transform = translation(-1.5, 0.33, -0.75) * scaling(0.33, 0.33, 0.33)
    left.material = Material(color = Color(1.0, 0.8, 0.1), diffuse = 0.7, specular = 0.3)

    val world = World(PointLight(point(-10.0, 10.0, -10.0), Color(1.0, 1.0, 1.0)))
    world.objects.addAll(setOf(floor, left_wall, right_wall, middle, left, right))

    val camera = Camera(500, 500, PI/3)
    camera.transform = viewTransform(point(0.0, 1.5, -5.0), point(0.0, 1.0, 0.0),
        vector(0.0, 1.0, 0.0))
    val canvas = render(camera, world)

    File("sphereworld.ppm").writeText(canvas_to_ppm(canvas))
}