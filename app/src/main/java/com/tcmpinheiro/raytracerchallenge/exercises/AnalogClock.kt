package com.tcmpinheiro.raytracerchallenge.exercises

import com.tcmpinheiro.raytracerchallenge.features.*
import java.io.File
import kotlin.math.roundToInt


fun main() {

    val color = Color(1.0, 0.8, 0.6)
    val canvas = Canvas(200, 200)
    val twelve = point(0.0, 0.0, 1.0)
    val clockRadius = 3* canvas.width/8.0


    //    # compute y-axis rotation for hour #3
    //    r ← rotation_y(3 * π/6)
    //    # given: position of 12 o'clock
    //    twelve ← point(0,0,1)
    //    # compute position of 3 o'clock by rotating 12 o'clock
    //    three ← r * twelve
    for (hours in 0 until 12){
        val r = rotationY(hours * Math.PI /6)
        val hourPoint = r * twelve * clockRadius
        paint_square(canvas, hourPoint, color)
    }
    File("analogclock.ppm").writeText(canvas_to_ppm(canvas))
}

fun paint_square(canvas: Canvas, center: Tuple, color: Color) {
    val canvasI = canvas.width/2
    val canvasJ = canvas.height/2
    for (i in -1 until 2){
        for (j in -1 until 2){
            write_pixel(canvas,  canvasI + center.x.roundToInt() + i, canvasJ + center.z.roundToInt() + j, color)
        }
    }
}

