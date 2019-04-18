package raytracerchallenge.features

import com.tcmpinheiro.raytracerchallenge.features.*
import raytracerchallenge.vendor.Perlin
import kotlin.math.floor
import kotlin.math.sqrt


abstract class Pattern{

    var transform: Matrix = identityMatrix()

    abstract fun pattern_at(point: Tuple):Color

    fun pattern_at_shape(shape: Shape, point: Tuple): Color {
        val local_point = shape.transform.inverse() * point
        val pattern_point = transform.inverse() * local_point
        return pattern_at(pattern_point)
    }
}

class TestPattern: Pattern() {

    override fun pattern_at(point: Tuple): Color {
        return Color(point.x, point.y, point.z)
    }
}

class StripePattern(val a:Pattern = SolidPattern(Color(0.0, 0.0, 0.0)), val b:Pattern = SolidPattern(Color(0.0, 0.0, 0.0))): Pattern() {

    override fun pattern_at(point: Tuple): Color {
        if(floor(point.x) % 2  == 0.0) {
            return a.pattern_at(point)
        }
        return b.pattern_at(point)
    }
}

class GradientPattern(val a:Pattern = SolidPattern(Color(0.0, 0.0, 0.0)), val b:Pattern = SolidPattern(Color(0.0, 0.0, 0.0))): Pattern() {

    // blending function
    override fun pattern_at(point: Tuple): Color {
        val distance = b.pattern_at(point) - a.pattern_at(point)
        val fraction = point.x - floor(point.x)
        return a.pattern_at(point) + distance * fraction
    }
}

class RingPattern(val a:Pattern = SolidPattern(Color(0.0, 0.0, 0.0)), val b:Pattern = SolidPattern(Color(0.0, 0.0, 0.0))): Pattern() {
    override fun pattern_at(point: Tuple): Color {
        if (floor(sqrt(point.x * point.x + point.z * point.z)) % 2 == 0.0){
            return a.pattern_at(point)
        }
        return b.pattern_at(point)
    }

}

class CheckersPattern(val a:Pattern = SolidPattern(Color(0.0, 0.0, 0.0)), val b:Pattern = SolidPattern(Color(0.0, 0.0, 0.0))): Pattern() {
    override fun pattern_at(point: Tuple): Color {
       if ((floor(point.x) + floor(point.y) + floor(point.z)) % 2 == 0.0){
           return a.pattern_at(point)
       }
        return b.pattern_at(point)
    }
}

class RadialGradientPattern(val a:Color = Color(0.0, 0.0, 0.0), val b:Color = Color(0.0, 0.0, 0.0)): Pattern() {
    override fun pattern_at(point: Tuple): Color {
        if (floor(sqrt(point.x * point.x + point.z * point.z)) % 2 == 0.0){
            val distance = b - a
            val fraction = point.x - floor(point.x)
            return a + distance * fraction
        }
        val distance = b - a
        val fraction = point.x - floor(point.x)
        return b - distance * fraction
    }
}


class SolidPattern(val color:Color): Pattern() {
    override fun pattern_at(point: Tuple): Color {
        return color
    }
}

class NestedCheckersPattern(val a:Pattern = SolidPattern(Color(0.0, 0.0, 0.0)), val b:Pattern = SolidPattern(Color(0.0, 0.0, 0.0))): Pattern() {
    override fun pattern_at(point: Tuple): Color {
        if ((floor(point.x) + floor(point.y) + floor(point.z)) % 2 == 0.0){
            return a.pattern_at(point)
        }
        return b.pattern_at(point)
    }
}

class BlendedPattern(val a:Pattern = SolidPattern(Color(0.0, 0.0, 0.0)), val b:Pattern = SolidPattern(Color(0.0, 0.0, 0.0))): Pattern() {
    override fun pattern_at(point: Tuple): Color {
        val distance = b.pattern_at(point) - a.pattern_at(point)
        val fraction = point.x - floor(point.x)
        return (a.pattern_at(point) + b.pattern_at(point)) + distance * fraction
    }
}

class NoisePattern(val pattern: Pattern = SolidPattern(Color(0.0, 0.0, 0.0))):Pattern(){
    override fun pattern_at(point: Tuple): Color {
        val noise = Perlin.noise(point.x, point.y, point.z)
        val pointColor = pattern.pattern_at(point)
        return Color(pointColor.red + noise, pointColor.green, pointColor.blue + noise)
    }

}
