package com.tcmpinheiro.raytracerchallenge.features

fun translation(x: Double, y: Double, z:Double): Matrix {
    val matrix = identityMatrix()
    matrix[0, 3] = x
    matrix[1, 3] = y
    matrix[2, 3] = z
    return matrix
}

fun scaling(x: Double, y: Double, z: Double): Matrix {
    val matrix = identityMatrix()
    matrix[0, 0] = x
    matrix[1, 1] = y
    matrix[2, 2] = z
    return matrix
}

fun rotationX(radians: Double): Matrix {
    val matrix = identityMatrix()
    matrix[1, 1] = Math.cos(radians)
    matrix[1, 2] = -Math.sin(radians)
    matrix[2, 1] = Math.sin(radians)
    matrix[2, 2] = Math.cos(radians)
    return matrix
}

fun rotationY(radians: Double): Matrix {
    val matrix = identityMatrix()
    matrix[0, 0] = Math.cos(radians)
    matrix[0, 2] = Math.sin(radians)
    matrix[2, 0] = -Math.sin(radians)
    matrix[2, 2] = Math.cos(radians)
    return matrix
}

fun rotationZ(radians: Double): Matrix {
    val matrix = identityMatrix()
    matrix[0, 0] = Math.cos(radians)
    matrix[0, 1] = -Math.sin(radians)
    matrix[1, 0] = Math.sin(radians)
    matrix[1, 1] = Math.cos(radians)
    return matrix
}

fun shearing(xy: Double, xz:Double, yx:Double, yz:Double, zx:Double, zy:Double ): Matrix{
    val matrix = identityMatrix()
    matrix[1, 0] = yx
    matrix[2, 0] = zx
    matrix[0, 1] = xy
    matrix[2, 1] = zy
    matrix[0, 2] = xz
    matrix[1, 2] = yz
    return matrix
}

fun viewTransform(from:Tuple, to:Tuple, up:Tuple): Matrix{
    val forward = normalize(to - from)
    val left = cross(forward, normalize(up))
    val trueUp = cross(left, forward)
    val orientation = Matrix(
        4,
        4,
        doubleArrayOf(left.x, left.y, left.z, 0.0,
            trueUp.x, trueUp.y, trueUp.z, 0.0,
            -forward.x, -forward.y, -forward.z, 0.0,
            0.0, 0.0, 0.0, 1.0))
    return orientation * translation(-from.x, -from.y, -from.z)
}