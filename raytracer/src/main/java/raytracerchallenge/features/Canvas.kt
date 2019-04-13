package com.tcmpinheiro.raytracerchallenge.features

import kotlin.math.roundToInt

data class Canvas(var width:Int, var height:Int) : Iterable<Color>{

    private var canvas: Array<Color> = Array(width * height) {
        Color(
            0.0,
            0.0,
            0.0
        )
    }


    fun getColor(i: Int, j: Int): Color {
        return canvas[i + (j * width)]
    }

    fun setColor(i: Int, j: Int, color: Color) {
        canvas[i + (j * width)] = color
    }

    fun setColor(i: Int, color: Color) {
        canvas[i] = color
    }

    override fun iterator(): Iterator<Color> {
        return canvas.iterator()
    }

}

fun write_pixel(c: Canvas, i: Int, j: Int, color: Color) {
    c.setColor(i, j, color)
}

fun pixel_at(c: Canvas, i: Int, j: Int): Color {
    return c.getColor(i, j)
}

val PPM_VERSION = "P3"
val MAX_COLOR_VALUE = 255.0

fun canvas_to_ppm_header(c: Canvas): String {
    return """$PPM_VERSION
        |${c.width} ${c.height}
        |${MAX_COLOR_VALUE.roundToInt()}
        |
    """.trimMargin()
}

fun canvas_to_ppm_pixel_data(c: Canvas): String {
//    val arrayPixel = c.flatMap {pixel ->
//        val rPixel = convertToPPM(pixel.red)
//        val gPixel = convertToPPM(pixel.green)
//        val bPixel = convertToPPM(pixel.blue)
//        listOf("$rPixel","$gPixel", "$bPixel")
//    }
    val arrayPixel = c
        .flatMap { listOf(it.red, it.green, it.blue) }
        .map { convertToPPM(it) }
        .map {it.toString()}

    return chunkByPixel(arrayPixel.asSequence()).joinToString("")

}

fun chunkByPixel(pixels: Sequence<String>): Sequence<String> = sequence {
    var line = String()
    for (pixel in pixels){
        if(line.length + pixel.length > 69){
            line = line.trimEnd()
            line += "\n"
            yield(line)
            line = String()
        }
        line += "$pixel "

    }
    line = line.trimEnd()
    line += "\n"
    yield(line)
}

fun canvas_to_ppm(c: Canvas): String {
    return canvas_to_ppm_header(c) + canvas_to_ppm_pixel_data(
        c
    )
}


fun convertToPPM(channel: Double): Int {
    var component = channel * MAX_COLOR_VALUE
    component = Math.max(0.0, Math.min(component, MAX_COLOR_VALUE))
    return component.roundToInt()
}

