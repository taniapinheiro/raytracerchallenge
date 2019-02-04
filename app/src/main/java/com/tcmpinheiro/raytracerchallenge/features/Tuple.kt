package com.tcmpinheiro.raytracerchallenge.features


data class Tuple (val x:Double, val y:Double, val z:Double, val w:Double){

    fun isPoint(): Boolean {
        return w == 1.0
    }

    fun isVector(): Boolean {
        return w == 0.0
    }

    operator fun plus(other: Tuple): Tuple {
        return Tuple(x + other.x, y + other.y, z + other.z, w + other.w)
    }

    operator fun minus(other: Tuple): Tuple {
        return Tuple(x - other.x, y - other.y, z - other.z, w - other.w)
    }

    operator fun unaryMinus(): Tuple {
        return Tuple(-x, -y, -z, -w)
    }

    operator fun times(scalar: Double): Tuple {
        return Tuple(scalar * x, scalar * y, scalar * z, scalar * w)
    }

    operator fun div(scalar: Double): Tuple {
        return Tuple(x / scalar, y / scalar, z / scalar, w / scalar)
    }
}


fun point(x:Double, y:Double, z:Double) = Tuple(x, y, z, 1.0)

fun vector(x:Double, y:Double, z:Double) = Tuple(x, y, z, 0.0)

fun magnitude(vector: Tuple) : Double {
    return Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z)
}

fun normalize(v: Tuple): Tuple {
    return Tuple(
        v.x / magnitude(v),
        v.y / magnitude(v),
        v.z / magnitude(v),
        v.w / magnitude(v)
    )
}

fun dot(a: Tuple, b: Tuple): Double {
    return a.x * b.x + a.y * b.y + a.z * b.z +
            a.w * b.w
}

fun cross(a: Tuple, b: Tuple): Tuple {
    return vector(
        a.y * b.z - a.z * b.y,
        a.z * b.x - a.x * b.z,
        a.x * b.y - a.y * b.x
    )
}


data class Color(var red: Double, var green: Double, var blue: Double) {

    operator fun plus(other: Color): Color {
        return Color(
            red + other.red,
            green + other.green,
            blue + other.blue
        )
    }

    operator fun minus(other: Color): Color {
        return Color(
            red.minus(other.red),
            green - other.green,
            blue - other.blue
        )
    }

    operator fun times(scalar: Double): Color {
        return Color(red * scalar, green * scalar, blue * scalar)
    }

    operator fun times(other: Color): Color {
        val r = red * other.red
        val g = green * other.green
        val b = blue * other.blue
        return Color(r, g, b)
    }

    private val delta: Double = 0.00001

    override fun equals(other: Any?): Boolean {
        return when(other) {
            is Color -> ((red -other.red) < delta) && ((green -other.green) < delta) && ((blue -other.blue) < delta)
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = red.hashCode()
        result = 31 * result + green.hashCode()
        result = 31 * result + blue.hashCode()
        result = 31 * result + delta.hashCode()
        return result
    }


}