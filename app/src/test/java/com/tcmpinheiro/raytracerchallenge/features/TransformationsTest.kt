package com.tcmpinheiro.raytracerchallenge.features

import junit.framework.Assert.assertEquals
import org.junit.Test

class TransformationsTest {

    /**
     * Scenario: Multiplying by a translation matrix
     * Given transform ← translation(5, -3, 2)
     * And p ← point(-3, 4, 5)
     * Then transform * p = point(2, 1, 7)
     */
    @Test
    fun multiplyByTranslationMatrix() {
        val transform = translation(5.0, -3.0, 2.0)
        val p = point(-3.0, 4.0, 5.0)
        assertEquals(point(2.0, 1.0, 7.0), transform * p)
    }

    /**
     * Scenario: Multiplying by the inverse of a translation matrix
     * Given transform ← translation(5, -3, 2)
     * And inv ← inverse(transform)
     * And p ← point(-3, 4, 5)
     * Then inv * p = point(-8, 7, 3)
     */
    @Test
    fun multiplyByInverseTranslationMatrix() {
        val transform = translation(5.0, -3.0, 2.0)
        val inv = transform.inverse()
        val p = point(-3.0, 4.0, 5.0)
        assertEquals(point(-8.0, 7.0, 3.0), inv * p)
    }

    /**
     * Scenario: Translation does not affect vectors
     * Given transform ← translation(5, -3, 2)
     * And v ← vector(-3, 4, 5)
     * Then transform * v = v
     */
    @Test
    fun translationDoesNotAffectVectors() {
        val transform = translation(5.0, -3.0, 2.0)
        val v = vector(-3.0, 4.0, 5.0)
        assertEquals(v, transform * v)
    }

    /**
     * Scenario: A scaling matrix applied to a point
     * Given transform ← scaling(2, 3, 4)
     * And p ← point(-4, 6, 8)
     * Then transform * p = point(-8, 18, 32)
     */
    @Test
    fun scalingMatrixWithPoint() {
        val transform = scaling(2.0, 3.0, 4.0)
        val p = point(-4.0, 6.0, 8.0)
        assertEquals(point(-8.0, 18.0, 32.0), transform * p)
    }

    /**
     * Scenario: A scaling matrix applied to a vector
     * Given transform ← scaling(2, 3, 4)
     * And v ← vector(-4, 6, 8)
     * Then transform * v = vector(-8, 18, 32)
     */
    @Test
    fun scalingMatrixWithVector() {
        val transform = scaling(2.0, 3.0, 4.0)
        val v = vector(-4.0, 6.0, 8.0)
        assertEquals(vector(-8.0, 18.0, 32.0), transform * v)
    }

    /**
     * Scenario: Multiplying by the inverse of a scaling matrix
     * Given transform ← scaling(2, 3, 4)
     * And inv ← inverse(transform)
     * And v ← vector(-4, 6, 8)
     * Then inv * v = vector(-2, 2, 2)
     */
    @Test
    fun multiplyingInverseScalingMatrix() {
        val transform = scaling(2.0, 3.0, 4.0)
        val inv = transform.inverse()
        val v = vector(-4.0, 6.0, 8.0)
        assertEquals(vector(-2.0, 2.0, 2.0), inv * v)
    }

    /**
     * Scenario: Reflection is scaling by a negative value
     * Given transform ← scaling(-1, 1, 1)
     * And p ← point(2, 3, 4)
     * Then transform * p = point(-2, 3, 4)
     */
    @Test
    fun scalingByNegativeNumber() {
        val transform = scaling(-1.0, 1.0, 1.0)
        val p = point(2.0, 3.0, 4.0)
        assertEquals(point(-2.0, 3.0, 4.0), transform * p)
    }

    /**
     * Scenario: Rotating a point around the x axis
     * Given p ← point(0, 1, 0)
     * And half_quarter ← rotation_x(π / 4)
     * And full_quarter ← rotation_x(π / 2)
     * Then half_quarter * p = point(0, √2/2, √2/2)
     * And full_quarter * p = point(0, 0, 1)
     */
    @Test
    fun rotatingAPointAroundX() {
        val p = point(0.0, 1.0, 0.0)
        val halfQuarter = rotationX(Math.PI / 4)
        val fullQuarter = rotationX(Math.PI / 2)
        assertEquals(point(0.0, Math.sqrt(2.0) / 2.0, Math.sqrt(2.0) / 2.0), halfQuarter * p)
        assertEquals(point(0.0, 0.0, 1.0), fullQuarter * p)
    }

    /**
     * Scenario: The inverse of an x-rotationX rotates in the opposite direction
     * Given p ← point(0, 1, 0)
     * And half_quarter ← rotation_x(π / 4)
     * And inv ← inverse(half_quarter)
     * Then inv * p = point(0, √2/2, -√2/2)
     */
    @Test
    fun inverseRotationXOppositeDirection() {
        val p = point(0.0, 1.0, 0.0)
        val halfQuarter = rotationX(Math.PI / 4)
        val inv = halfQuarter.inverse()
        assertEquals(point(0.0, Math.sqrt(2.0) / 2.0, -Math.sqrt(2.0) / 2.0), inv * p)
    }

    /**
     * Scenario: Rotating a point around the y axis
     * Given p ← point(0, 0, 1)
     * And half_quarter ← rotation_y(π / 4)
     * And full_quarter ← rotation_y(π / 2)
     * Then half_quarter * p = point(√2/2, 0, √2/2)
     * And full_quarter * p = point(1, 0, 0)
     */
    @Test
    fun rotatingAPointAroundY() {
        val p = point(0.0, 0.0, 1.0)
        val halfQuarter = rotationY(Math.PI / 4)
        val fullQuarter = rotationY(Math.PI / 2)
        assertEquals(point(Math.sqrt(2.0) / 2.0, 0.0, Math.sqrt(2.0) / 2.0), halfQuarter * p)
        assertEquals(point(1.0, 0.0, 0.0), fullQuarter * p)
    }

    /**
     * Scenario: Rotating a point around the z axis
     * Given p ← point(0, 1, 0)
     * And half_quarter ← rotation_z(π / 4)
     * And full_quarter ← rotation_z(π / 2)
     * Then half_quarter * p = point(-√2/2, √2/2, 0)
     * And full_quarter * p = point(-1, 0, 0)
     */
    @Test
    fun rotatingAPointAroundZ() {
        val p = point(0.0, 1.0, 0.0)
        val halfQuarter = rotationZ(Math.PI / 4)
        val fullQuarter = rotationZ(Math.PI / 2)
        assertEquals(point(-Math.sqrt(2.0) / 2.0, Math.sqrt(2.0) / 2.0, 0.0), halfQuarter * p)
        assertEquals(point(-1.0, 0.0, 0.0), fullQuarter * p)
    }

    /**
     * Scenario: A shearing transformation moves x in proportion to y
     * Given transform ← shearing(1, 0, 0, 0, 0, 0)
     * And p ← point(2, 3, 4)
     * Then transform * p = point(5, 3, 4)
     */
    @Test
    fun shearingXToY() {
        val transform = shearing(1.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val p = point(2.0, 3.0, 4.0)
        assertEquals(point(5.0, 3.0, 4.0), transform * p)
    }

    /**
     * Scenario: A shearing transformation moves x in proportion to z
     * Given transform ← shearing(0, 1, 0, 0, 0, 0)
     * And p ← point(2, 3, 4)
     * Then transform * p = point(6, 3, 4)
     */
    @Test
    fun shearingXToZ() {
        val transform = shearing(0.0, 1.0, 0.0, 0.0, 0.0, 0.0)
        val p = point(2.0, 3.0, 4.0)
        assertEquals(point(6.0, 3.0, 4.0), transform * p)
    }


    /**
     *  Scenario: A shearing transformation moves y in proportion to x
     *  Given transform ← shearing(0, 0, 1, 0, 0, 0)
     *  And p ← point(2, 3, 4)
     *  Then transform * p = point(2, 5, 4)
     */
    @Test
    fun shearingYToX() {
        val transform = shearing(0.0, 0.0, 1.0, 0.0, 0.0, 0.0)
        val p = point(2.0, 3.0, 4.0)
        assertEquals(point(2.0, 5.0, 4.0), transform * p)
    }

    /**
     * Scenario: A shearing transformation moves y in proportion to z
     * Given transform ← shearing(0, 0, 0, 1, 0, 0)
     * And p ← point(2, 3, 4)
     * Then transform * p = point(2, 7, 4)
     */
    @Test
    fun shearingYToY() {
        val transform = shearing(0.0, 0.0, 0.0, 1.0, 0.0, 0.0)
        val p = point(2.0, 3.0, 4.0)
        assertEquals(point(2.0, 7.0, 4.0), transform * p)
    }

    /**
     *  Scenario: A shearing transformation moves z in proportion to x
     *  Given transform ← shearing(0, 0, 0, 0, 1, 0)
     *  And p ← point(2, 3, 4)
     *  Then transform * p = point(2, 3, 6)
     */
    @Test
    fun shearingZToX() {
        val transform = shearing(0.0, 0.0, 0.0, 0.0, 1.0, 0.0)
        val p = point(2.0, 3.0, 4.0)
        assertEquals(point(2.0, 3.0, 6.0), transform * p)
    }

    /**
     * Scenario: A shearing transformation moves z in proportion to y
     * Given transform ← shearing(0, 0, 0, 0, 0, 1)
     * And p ← point(2, 3, 4)
     * Then transform * p = point(2, 3, 7)
     */
    @Test
    fun shearingZToY() {
        val transform = shearing(0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
        val p = point(2.0, 3.0, 4.0)
        assertEquals(point(2.0, 3.0, 7.0), transform * p)
    }

    /**
     * Scenario: Individual transformations are applied in sequence
     * Given p ← point(1, 0, 1)
     * And A ← rotation_x(π / 2)
     * And B ← scaling(5, 5, 5)
     * And C ← translation(10, 5, 7) # apply rotation first
     * When p2 ← A * p
     * Then p2 = point(1, -1, 0) # then apply scaling
     * When p3 ← B * p2
     * Then p3 = point(5, -5, 0) # then apply translation
     * When p4 ← C * p3
     * Then p4 = point(15, 0, 7)
     */
    @Test
    fun individualTransformationsInSequence() {
        val p = point(1.0, 0.0, 1.0)
        val a = rotationX(Math.PI /2)
        val b = scaling(5.0, 5.0, 5.0)
        val c = translation(10.0, 5.0, 7.0)
        val p2 = a * p
        assertEquals(point(1.0, -1.0, 0.0), p2)
        val p3 = b * p2
        assertEquals(point(5.0, -5.0, 0.0), p3)
        val p4 = c * p3
        assertEquals(point(15.0, 0.0, 7.0), p4)
    }

    /**
     * Scenario: Chained transformations must be applied in reverse order
     * Given p ← point(1, 0, 1)
     * And A ← rotation_x(π / 2)
     * And B ← scaling(5, 5, 5)
     * And C ← translation(10, 5, 7)
     * When T ← C * B * A
     * Then T * p = point(15, 0, 7)
     */
    @Test
    fun chainTransformationsInReverseOrder() {
        val p = point(1.0, 0.0, 1.0)
        val a = rotationX(Math.PI /2)
        val b = scaling(5.0, 5.0, 5.0)
        val c = translation(10.0, 5.0, 7.0)
        val t = c * b * a
        assertEquals(point(15.0, 0.0, 7.0), t * p)
    }
}