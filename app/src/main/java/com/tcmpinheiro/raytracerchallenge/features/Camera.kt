package com.tcmpinheiro.raytracerchallenge.features

data class Camera(
    val hsize:Int,
    val vsize:Int,
    val fieldOfView: Double,
    var transform:Matrix = identityMatrix()
) {

    val pixelSize:Double
    val halfWidth:Double
    val halfHeight:Double

    init {
        val halfView = Math.tan(fieldOfView / 2.0)
        val aspect = hsize.toDouble() / vsize.toDouble()
        if (aspect >= 1) {
            halfWidth = halfView
            halfHeight = halfView / aspect
        } else {
            halfWidth = halfView * aspect
            halfHeight = halfView
        }

        pixelSize = (halfWidth * 2) / hsize
    }
}

fun ray_for_pixel(camera: Camera, px:Int, py:Int): Ray {
    // the offset from the edge of the canvas to the pixel's center
    val xoffset = (px + 0.5) * camera.pixelSize
    val yoffset = (py + 0.5) * camera.pixelSize

    // the untransformed coordinates of the pixel in world space.
    //(remember that the camera looks toward -z, so +x is to the *left*.)
    val worldX = camera.halfWidth - xoffset
    val worldY = camera.halfHeight - yoffset

    // using the camera matrix, transform the canvas point and the origin,
    // and then compute the ray's direction vector.
    // (remember that the canvas is at z=-1)
    val pixel = camera.transform.inverse() * point(worldX, worldY, -1.0)
    val origin = camera.transform.inverse() * point(0.0, 0.0, 0.0)
    val direction = normalize(pixel - origin)
    return Ray(origin, direction)
}

fun render(camera: Camera, world: World): Canvas {
    val image = Canvas(camera.hsize, camera.vsize)
    for (y in 0 until camera.vsize) {
        for (x in 0 until camera.hsize){
            val ray = ray_for_pixel(camera, x, y)
            val color = colorAt(world, ray)
            write_pixel(image, x, y, color)
        }
    }
    return image
}