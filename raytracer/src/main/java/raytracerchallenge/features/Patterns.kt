package raytracerchallenge.features

import com.tcmpinheiro.raytracerchallenge.features.*
import kotlin.math.floor


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

class StripePattern(val a:Color = Color(0.0, 0.0, 0.0), val b:Color = Color(0.0, 0.0, 0.0)): Pattern() {

    override fun pattern_at(point: Tuple): Color {
        if(floor(point.x) % 2  == 0.0) {
            return a
        }
        return b
    }

}