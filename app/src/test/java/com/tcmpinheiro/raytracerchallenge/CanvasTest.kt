package com.tcmpinheiro.raytracerchallenge

import com.tcmpinheiro.raytracerchallenge.features.*
import org.junit.Assert.*
import org.junit.Test

class CanvasTest {

    //    Scenario: Creating a canvas Given c ← canvas(10, 20) Then c.width = 10
//    And c.height = 20
//    And every pixel of c is color(0, 0, 0)
    @Test
    fun createCanvas() {
        val c = Canvas(10, 20)
        assertEquals(10, c.width)
        assertEquals(20, c.height)
        for (i in 0 until c.width) {
            for (j in 0 until c.height)
                assertEquals(Color(0.0, 0.0, 0.0), c.getColor(i, j))
        }
    }

    //    Scenario: Writing pixels to a canvas Given c ← canvas(10, 20)
//    And red ← color(1, 0, 0) When write_pixel(c, 2, 3, red) Then pixel_at(c, 2, 3) = red
    @Test
    fun writingPixels() {
        val c = Canvas(10, 20)
        val red = Color(1.0, 0.0, 0.0)
        write_pixel(c, 2, 3, red)
        assertEquals(red, pixel_at(c, 2, 3))
    }

    //    Scenario: Constructing the PPM header Given c ← canvas(5, 3)
//    When ppm ← canvas_to_ppm(c)
//    Then lines 1-3 of ppm are
//    """
//    P3
//    5 3
//    255
//    """
    @Test
    fun constructPPMHeader() {
        val c = Canvas(5, 3)
        val ppm = canvas_to_ppm_header(c)
        assertEquals(getPPMHeader(5, 3), ppm)
    }

//    Scenario: Constructing the PPM pixel data Given c ← canvas(5, 3)
//    And c1 ← color(1.5, 0, 0) And c2 ← color(0, 0.5, 0) And c3 ← color(-0.5, 0, 1)
//    When write_pixel(c, 0, 0, c1) And write_pixel(c, 2, 1, c2) And write_pixel(c, 4, 2, c3) And ppm ← canvas_to_ppm(c)
//    Then lines 4-6 of ppm are """
//                     255 0 0 0 0 0 0 0 0 0 0 0 0 0 0
//                     0 0 0 0 0 0 0 128 0 0 0 0 0 0 0
//                     0 0 0 0 0 0 0 0 0 0 0 0 0 0 255
//                     """
    @Test
    fun constructPPMPixelData() {
        val c = Canvas(5, 3)
        val c1 = Color(1.5, 0.0, 0.0)
        val c2 = Color(0.0, 0.5, 0.0)
        val c3 = Color(-0.5, 0.0, 1.0)
    write_pixel(c, 0, 0, c1)
    write_pixel(c, 2, 1, c2)
    write_pixel(c, 4, 2, c3)
        val ppm = canvas_to_ppm(c)
        assertEquals(getPPMHeader(5, 3) + "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 128 0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 255\n",
            ppm)
    }


//    Scenario: Splitting long lines in PPM files
//    Given c ← canvas(10, 2)
//    When every pixel of c is set to color(1, 0.8, 0.6)
//    And ppm ← canvas_to_ppm(c)
//    Then lines 4-7 of ppm are """
//    255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204
//    153 255 204 153 255 204 153 255 204 153 255 204 153
//    255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204
//    153 255 204 153 255 204 153 255 204 153 255 204 153
    @Test
    fun splitLongLines() {
        val c = Canvas(10, 2)
        c.mapIndexed{ index, it
            -> c.setColor(index, Color(1.0, 0.8, 0.6)) }
        val ppm = canvas_to_ppm(c)
        assertEquals(
            getPPMHeader(10, 2) + "255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204\n" +
                     "153 255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255\n" +
                     "204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204 153\n" +
                     "255 204 153 255 204 153 255 204 153\n", ppm)
    }

    private fun getPPMHeader(width: Int, height: Int): String {
        return "P3\n$width $height\n255\n"
    }

}



