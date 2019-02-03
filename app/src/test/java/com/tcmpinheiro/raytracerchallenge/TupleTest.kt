package com.tcmpinheiro.raytracerchallenge

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class TupleTest {

//    Scenario: A tuple with w=1.0 is a testIsPoint
//    Given a ← tuple(4.3, -4.2, 3.1, 1.0) Then a.x = 4.3
//    And a.y = -4.2
//    And a.z = 3.1
//    And a.w = 1.0
//    And a is a testIsPoint
//    And a is not a vector

    val delta = 0.000001
    @Test
    fun tupleIsPoint() {
        val a = Tuple(4.3, -4.2, 3.1, 1.0)
        assertEquals(4.3, a.x, delta)
        assertEquals(-4.2, a.y, delta)
        assertEquals(3.1, a.z, delta)
        assertEquals(1.0, a.w, delta)
        assertTrue(a.isPoint())
        assertFalse(a.isVector())
    }

//    Scenario: A tuple with w=0 is a vector Given a ← tuple(4.3, -4.2, 3.1, 0.0) Then a.x = 4.3
//    And a.y = -4.2
//    And a.z = 3.1
//    And a.w = 0.0
//    And a is not a testIsPoint And a is a vector
    @Test
    fun tupleIsVector() {
    val a = Tuple(4.3, -4.2, 3.1, 0.0)
    assertEquals(4.3, a.x, delta)
    assertEquals(-4.2, a.y, delta)
    assertEquals(3.1, a.z, delta)
    assertEquals(0.0, a.w, delta)
    assertFalse(a.isPoint())
    assertTrue(a.isVector())
    }

//    Scenario: testIsPoint() creates tuples with w=1 Given p ← testIsPoint(4, -4, 3)
//    Then p = tuple(4, -4, 3, 1)
    @Test
    fun testIsPoint() {
        val p = point(4.0, -4.0, 3.0)
        assertEquals(Tuple(4.0, -4.0, 3.0, 1.0), p)

    }

//    Scenario: vector() creates tuples with w=0
//    Given v ← vector(4, -4, 3)
//    Then v = tuple(4, -4, 3, 0)
    @Test
    fun testIsVector() {
        val v = vector(4.0, -4.0, 3.0)
        assertEquals(Tuple(4.0, -4.0, 3.0, 0.0), v)
    }


//    Scenario: Adding two tuples
//    Given a1 ← tuple(3, -2, 5, 1)
//    And a2 ← tuple(-2, 3, 1, 0)
//    Then a1 + a2 = tuple(1, 1, 6, 1)
    @Test
    fun addTuples() {
        val a1 = Tuple(3.0, -2.0, 5.0, 1.0)
        val a2 = Tuple(-2.0, 3.0, 1.0, 0.0)
        assertEquals(Tuple(1.0, 1.0, 6.0, 1.0), a1 + a2)
    }

//    Scenario: Subtracting two points Given p1 ← testIsPoint(3, 2, 1)
//    And p2 ← testIsPoint(5, 6, 7)
//    Then p1 - p2 = vector(-2, -4, -6)
    @Test
    fun subtractTuples() {
        val p1 = point(3.0, 2.0, 1.0)
        val p2 = point(5.0, 6.0, 7.0)
        assertEquals(vector(-2.0, -4.0, -6.0), p1 - p2)

    }

//    Scenario: Subtracting a vector from a testIsPoint
//    Given p ← testIsPoint(3, 2, 1)
//    And v ← vector(5, 6, 7)
//    Then p - v = testIsPoint(-2, -4, -6)
    @Test
    fun subtractingVectorFromPoint() {
        val p = point(3.0, 2.0, 1.0)
        val v = vector(5.0, 6.0, 7.0)
        assertEquals(point(-2.0,-4.0,-6.0), p - v)
    }

//    Scenario: Subtracting two vectors
//    Given v1 ← vector(3, 2, 1)
//    And v2 ← vector(5, 6, 7)
//    Then v1 - v2 = vector(-2, -4, -6)
    @Test
    fun subtractingTwoVectors() {
        val v1 = vector(3.0, 2.0, 1.0)
        val v2 = vector(5.0, 6.0, 7.0)
        assertEquals(vector(-2.0, -4.0, -6.0), v1 - v2)
    }

//    Scenario: Subtracting a vector from the zero vector
//    Given zero ← vector(0, 0, 0)
//    And v ← vector(1, -2, 3)
//    Then zero - v = vector(-1, 2, -3)
    @Test
    fun subtractVectorFromZero() {
        val zero = vector(0.0, 0.0, 0.0)
        val v = vector(1.0, -2.0, 3.0)
        assertEquals(vector(-1.0, 2.0, -3.0), zero - v)
    }

//    Scenario: Negating a tuple
//    Given a ← tuple(1, -2, 3, -4) Then -a = tuple(-1, 2, -3, 4)
    @Test
    fun negateTuple() {
        val a = Tuple(1.0, -2.0, 3.0, -4.0)
        assertEquals(Tuple(-1.0, 2.0, -3.0, 4.0), -a)
    }

    //    Scenario: Multiplying a tuple by a scalar Given a ← tuple(1, -2, 3, -4)
//    Then a * 3.5 = tuple(3.5, -7, 10.5, -14)
    @Test
    fun multiplyTupleByScalar() {
        val a = Tuple(1.0, -2.0, 3.0, -4.0)
        assertEquals(Tuple(3.5, -7.0, 10.5, -14.0), a * 3.5)
    }
//    Scenario: Multiplying a tuple by a fraction Given a ← tuple(1, -2, 3, -4)
//    Then a * 0.5 = tuple(0.5, -1, 1.5, -2)
    @Test
    fun multiplyTupleByFraction() {
    val a = Tuple(1.0, -2.0, 3.0, -4.0)
    assertEquals(Tuple(0.5, -1.0, 1.5, -2.0), a * 0.5)
    }

//    Scenario: Dividing a tuple by a scalar Given a ← tuple(1, -2, 3, -4)
//    Then a / 2 = tuple(0.5, -1, 1.5, -2)
    @Test
    fun divideTupleByScalar() {
        val a = Tuple(1.0, -2.0, 3.0, -4.0)
        assertEquals(Tuple(0.5, -1.0, 1.5, -2.0), a / 2.0)
    }


//    Scenario: Computing the magnitude of vector(1, 0, 0) Given v ← vector(1, 0, 0)
//    Then magnitude(v) = 1
//    Scenario: Computing the magnitude of vector(0, 1, 0) Given v ← vector(0, 1, 0)
//    Then magnitude(v) = 1
//    Scenario: Computing the magnitude of vector(0, 0, 1) Given v ← vector(0, 0, 1)
//    Then magnitude(v) = 1
//    Scenario: Computing the magnitude of vector(1, 2, 3) Given v ← vector(1, 2, 3)
//    Then magnitude(v) = √14
//    Scenario: Computing the magnitude of vector(-1, -2, -3) Given v ← vector(-1, -2, -3)
//    Then magnitude(v) = √14
    @Test
    fun computeMagnitude() {
        val v1 = vector(1.0, 0.0, 0.0)
        assertEquals(1.0, magnitude(v1), delta)
        val v2 = vector(0.0, 1.0, 0.0)
        assertEquals(1.0, magnitude(v2), delta)
        val v3 = vector(0.0, 0.0, 1.0)
        assertEquals(1.0, magnitude(v3), delta)
        val v4 = vector(1.0, 2.0, 3.0)
        assertEquals(Math.sqrt(14.0), magnitude(v4), delta)
        val v5 = vector(-1.0, -2.0, -3.0)
        assertEquals(Math.sqrt(14.0), magnitude(v5), delta)
    }

//    Scenario: Normalizing vector(4, 0, 0) gives (1, 0, 0) Given v ← vector(4, 0, 0)
//    Then normalize(v) = vector(1, 0, 0)
//    Scenario: Normalizing vector(1, 2, 3)
//    Given v ← vector(1, 2, 3)
//    Then normalize(v) = approximately vector(1 / sqrt 14, 2 / sqrt 14, 3 / sqrt 14)
//    Scenario: The magnitude of a normalized vector Given v ← vector(1, 2, 3)
//    When norm ← normalize(v)
//    Then magnitude(norm) = 1
    @Test
    fun normalizingVector() {
        val v = vector(4.0, 0.0, 0.0)
        assertEquals(vector(1.0, 0.0, 0.0), normalize(v))
    }

//    Scenario: The dot product of two tuples Given a ← vector(1, 2, 3)
//    And b ← vector(2, 3, 4) Then dot(a, b) = 20
    @Test
    fun dotProduct() {
        val a = vector(1.0, 2.0, 3.0)
        val b = vector(2.0, 3.0, 4.0)
        assertEquals(20.0, dot(a, b), delta)
    }

//    Scenario: The cross product of two vectors Given a ← vector(1, 2, 3)
//    And b ← vector(2, 3, 4)
//    Then cross(a, b) = vector(-1, 2, -1)
//    And cross(b, a) = vector(1, -2, 1)
    @Test
    fun crossProduct() {
        var a = vector(1.0, 2.0, 3.0)
        var b = vector(2.0, 3.0, 4.0)
        assertEquals(vector(-1.0, 2.0, -1.0), cross(a, b))
    assertEquals(vector(1.0, -2.0, 1.0), cross(b, a))
    }

//    Scenario: Colors are (red, green, blue) tuples Given c ← color(-0.5, 0.4, 1.7)
//    Then c.red = -0.5
//    And c.green = 0.4 And c.blue = 1.7
    @Test
    fun colorsAreTuples() {
        val c = Color(-0.5, 0.4, 1.7)
        assertEquals(-0.5, c.red, delta)
        assertEquals(0.4, c.green, delta)
        assertEquals(1.7, c.blue, delta)

    }

    //    Scenario: Adding colors
//    Given c1 ← color(0.9, 0.6, 0.75)
    //    And c2 ← color(0.7, 0.1, 0.25) Then c1 + c2 = color(1.6, 0.7, 1.0)
    @Test
    fun addingColors() {
        val c1 = Color(.9, .6, .75)
        val c2 = Color(.7, .1, .25)
        assertEquals(Color(1.6, 0.7, 1.0), c1 + c2)
    }

    //    Scenario: Subtracting colors
//    Given c1 ← color(0.9, 0.6, 0.75) And c2 ← color(0.7, 0.1, 0.25)
//    Then c1 - c2 = color(0.2, 0.5, 0.5)
    @Test
    fun subtractingColors() {
        val c1 = Color(0.9, 0.6, 0.75)
        val c2 = Color(0.7, 0.1, 0.25)
        assertEquals(Color(0.2, 0.5, 0.5), c1 - c2)
    }
    //    Scenario: Multiplying a color by a scalar Given c ← color(0.2, 0.3, 0.4)
//    Then c * 2 = color(0.4, 0.6, 0.8)
    @Test
    fun multiplyingColors() {
        val c = Color(0.2, 0.3, 0.4)
        assertEquals(Color(.4, 0.6, .8), c * 2.0)
    }

//    Scenario: Multiplying colors Given c1 ← color(1, 0.2, 0.4) And c2 ← color(0.9, 1, 0.1)
//    Then c1 * c2 = color(0.9, 0.2, 0.04)
    @Test
    fun multiplyingTwoColors() {
        val c1 = Color(1.0, 0.2, 0.4)
        val c2 = Color(0.9, 1.0, 0.1)
        assertEquals(Color(0.9, 0.2, 0.04), c1 * c2)
    }
}
