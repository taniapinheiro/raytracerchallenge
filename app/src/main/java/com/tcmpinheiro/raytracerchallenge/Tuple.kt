package com.tcmpinheiro.raytracerchallenge


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

fun magnitude(vector:Tuple) : Double {
    return Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z)
}

fun normalize(v: Tuple): Tuple {
    return Tuple(v.x / magnitude(v),
        v.y / magnitude(v),
        v.z / magnitude(v),
        v.w / magnitude(v))
}

fun dot(a: Tuple, b: Tuple): Double {
    return a.x * b.x + a.y * b.y + a.z * b.z +
            a.w * b.w
}

fun cross(a: Tuple, b: Tuple): Tuple {
    return vector(a.y * b.z - a.z * b.y,
        a.z * b.x - a.x * b.z,
        a.x * b.y - a.y * b.x)
}