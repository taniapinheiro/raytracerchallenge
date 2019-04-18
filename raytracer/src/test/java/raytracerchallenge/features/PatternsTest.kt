package raytracerchallenge.features

import com.tcmpinheiro.raytracerchallenge.features.*
import org.junit.Assert.assertEquals
import org.junit.Test

class PatternsTest {

    val black = Color(0.0, 0.0, 0.0)
    val white = Color(1.0, 1.0, 1.0)

    /**
     * Scenario: Creating a stripe pattern
     * Given pattern ← stripe_pattern(white, black)
     * Then pattern.a = white
     * And pattern.b = black
     */
    @Test
    fun testStripePattern() {
        val pattern = StripePattern(white, black)
        assertEquals(white, pattern.a)
        assertEquals(black, pattern.b)
    }

    /**
     * Scenario: A stripe pattern is constant in y
     * Given pattern ← stripe_pattern(white, black)
     * Then stripe_at(pattern, point(0, 0, 0)) = white
     * And stripe_at(pattern, point(0, 1, 0)) = white
     * And stripe_at(pattern, point(0, 2, 0)) = white
     */
    @Test
    fun testPatternConstantInY() {
        val pattern = StripePattern(white, black)
        assertEquals(white, pattern.pattern_at(point(0.0, 0.0, 0.0)))
        assertEquals(white, pattern.pattern_at(point(0.0, 1.0, 0.0)))
        assertEquals(white, pattern.pattern_at(point(0.0, 2.0, 0.0)))
    }

    /**
     * Scenario: A stripe pattern is constant in z
     * Given pattern ← stripe_pattern(white, black)
     * Then stripe_at(pattern, point(0, 0, 0)) = white
     * And stripe_at(pattern, point(0, 0, 1)) = white
     * And stripe_at(pattern, point(0, 0, 2)) = white
     */
    @Test
    fun testStripePatternConstantInZ() {
        val pattern = StripePattern(white, black)
        assertEquals(white, pattern.pattern_at(point(0.0, 0.0, 0.0)))
        assertEquals(white, pattern.pattern_at(point(0.0, 0.0, 1.0)))
        assertEquals(white, pattern.pattern_at(point(0.0, 0.0, 2.0)))
    }

    /**
     * Scenario: A stripe pattern alternates in x
     * Given pattern ← stripe_pattern(white, black)
     * Then stripe_at(pattern, point(0, 0, 0)) = white
     * And stripe_at(pattern, point(0.9, 0, 0)) = white
     * And stripe_at(pattern, point(1, 0, 0)) = black
     * And stripe_at(pattern, point(-0.1, 0, 0)) = black
     * And stripe_at(pattern, point(-1, 0, 0)) = black
     * And stripe_at(pattern, point(-1.1, 0, 0)) = white
     */
    @Test
    fun testStripePatternAlternatesInX() {
        val pattern = StripePattern(white, black)
        assertEquals(white, pattern.pattern_at(point(0.0, 0.0, 0.0)))
        assertEquals(white, pattern.pattern_at(point(0.9, 0.0, 0.0)))
        assertEquals(black, pattern.pattern_at(point(1.0, 0.0, 0.0)))
        assertEquals(black, pattern.pattern_at(point(-0.1, 0.0, 0.0)))
        assertEquals(black, pattern.pattern_at(point(-1.0, 0.0, 0.0)))
        assertEquals(white, pattern.pattern_at(point(-1.1, 0.0, 0.0)))
    }

    /**
     * Scenario: Lighting with a pattern applied
     * Given m.pattern ← stripe_pattern(color(1, 1, 1), color(0, 0, 0))
     * And m.ambient ← 1
     * And m.diffuse ← 0
     * And m.specular ← 0
     * And eyev ← vector(0, 0, -1)
     * And normalv ← vector(0, 0, -1)
     * And light ← point_light(point(0, 0, -10), color(1, 1, 1))
     * When c1 ← lighting(m, light, point(0.9, 0, 0), eyev, normalv, false)
     * And c2 ← lighting(m, light, point(1.1, 0, 0), eyev, normalv, false)
     * Then c1 = color(1, 1, 1) And c2 = color(0, 0, 0)
     */
    @Test
    fun testLightingWithPattern() {
        val m = Material(
            pattern = StripePattern(white, black),
            ambient = 1.0,
            diffuse = 0.0,
            specular = 0.0)
        val eyev = vector(0.0, 0.0, -1.0)
        val normalv = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), Color(1.0, 1.0, 1.0))
        val c1 = lighting(m, Sphere(), light, point(0.9, 0.0, 0.0), eyev, normalv, false)
        val c2 = lighting(m, Sphere(), light, point(1.1, 0.0, 0.0), eyev, normalv, false)
        assertEquals(white, c1)
        assertEquals(black, c2)
    }

    /**
     * Scenario: Stripes with an object transformation
     * Given object ← sphere()
     * And set_transform(object, scaling(2, 2, 2))
     * And pattern ← test_pattern()
     * When c ← pattern_at_shape(pattern, object, point(2, 3, 4))
     * Then c = color(1, 1.5, 2)
     */
    @Test
    fun testStripesWithObjectTransformation() {
        val shape = Sphere(transform = scaling(2.0, 2.0, 2.0))
        val pattern = TestPattern()
        val c = pattern.pattern_at_shape(shape, point(2.0, 3.0, 4.0))
        assertEquals(Color(1.0, 1.5, 2.0), c)

    }
    /**
     * Scenario: Stripes with a pattern transformation
     * Given object ← sphere()
     * And pattern ← test_pattern()
     * And set_pattern_transform(pattern, scaling(2, 2, 2))
     * When c ← pattern_at_shape(pattern, shape, point(2, 3, 4))
     * Then c = color(1, 1.5, 2)
     */
    @Test
    fun testStripesWithPatternTransformation() {
        val shape = Sphere()
        val pattern = TestPattern()
        pattern.transform = scaling(2.0, 2.0, 2.0)
        val c = pattern.pattern_at_shape(shape, point(2.0, 3.0, 4.0))
        assertEquals(Color(1.0, 1.5, 2.0), c)
    }

    /**
     * Scenario: Stripes with both an object and a pattern transformation
     * Given object ← sphere()
     * And set_transform(shape, scaling(2, 2, 2))
     * And pattern ← test_pattern()
     * And set_pattern_transform(pattern, translation(0.5, 1, 1.5))
     * When c ← pattern_at_shape(pattern, shape, point(2.5, 3, 3.5))
     * Then c = color(0.75, 0.5, 0.25)
     */
    @Test
    fun testStripesWithObjectAndPatternTransformation() {
        val shape = Sphere(transform = scaling(2.0, 2.0, 2.0))
        val pattern = TestPattern()
        pattern.transform = translation(0.5, 1.0, 1.5)
        val c = pattern.pattern_at_shape(shape, point(2.5, 3.0, 3.5))
        assertEquals(Color(0.75, 0.5, 0.25), c)
    }

    /**
     * Scenario: The default pattern transformation
     * Given pattern ← test_pattern()
     * Then pattern.transform = identity_matrix
     */
    @Test
    fun testPatternDefaultTransformation() {
        val p = TestPattern()
        assertEquals(identityMatrix(), p.transform)
    }

    /**
     * Scenario: Assigning a transformation
     * Given pattern ← test_pattern()
     * When set_pattern_transform(pattern, translation(1, 2, 3))
     * Then pattern.transform = translation(1, 2, 3)
     */
    @Test
    fun testPatternAssignTransformation() {
        val p = TestPattern()
        p.transform = translation(1.0, 2.0, 3.0)
        assertEquals(translation(1.0, 2.0, 3.0), p.transform)
    }


}