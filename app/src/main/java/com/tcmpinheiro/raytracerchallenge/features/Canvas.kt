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

    inline fun <T> MutableList<T>.mapInPlace(mutator: (T)->T) {
        val iterate = this.listIterator()
        while (iterate.hasNext()) {
            val oldValue = iterate.next()
            val newValue = mutator(oldValue)
            if (newValue !== oldValue) {
                iterate.set(newValue)
            }
        }
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
    var ppm = ""
    val arrayPixel = ArrayList<String>()
    for (pixel in c) {
        val rPixel = convertToPPM(pixel.red)
        val gPixel = convertToPPM(pixel.green)
        val bPixel = convertToPPM(pixel.blue)
        arrayPixel.add("$rPixel")
        arrayPixel.add("$gPixel")
        arrayPixel.add("$bPixel")
    }

    var line = ""
    for (pixel in arrayPixel) {
        if (line.length + pixel.length > 69) {
            line = line.trimEnd()
            line += "\n"
            ppm += line
            line = ""
        }
        line += "$pixel "
    }
    line = line.trimEnd()
    line += "\n"
    ppm += line

    return ppm
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

