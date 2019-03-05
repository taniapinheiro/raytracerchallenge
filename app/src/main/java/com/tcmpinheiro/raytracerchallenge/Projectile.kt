package com.tcmpinheiro.raytracerchallenge

import com.tcmpinheiro.raytracerchallenge.features.*
import java.io.File
import kotlin.math.roundToInt


data class Projectile(var position: Tuple, var velocity: Tuple) {

}

data class Environment(var gravity: Tuple, var wind:Tuple) {

}

//
//function tick(env, proj)
//position ← proj.position + proj.velocity
//velocity ← proj.velocity + env.gravity + env.wind return projectile(position, velocity)
//end function
fun tick(environment: Environment, projectile: Projectile): Projectile {
    val position = projectile.position + projectile.velocity
    val velocity = projectile.velocity + environment.gravity + environment.wind
    return Projectile(position, velocity)
}

//fun main() {
//
////    start ← point(0, 1, 0)
////    velocity ← normalize(vector(1, 1.8, 0)) * 11.25
////    p ← projectile(start, velocity)
////    gravity ← vector(0, -0.1, 0) wind ← vector(-0.01, 0, 0)
////    e ← environment(gravity, wind)
////    c ← canvas(900, 550)
//    val start = point(0.0, 1.0, 0.0)
//    val velocity = normalize(vector(1.0, 1.8, 0.0)) * 11.25
//    var p = Projectile(start, velocity)
//
//    val gravity = vector(0.0, -0.1, 0.0)
//    val wind = vector(-0.01, 0.0, 0.0)
//    val e = Environment(gravity, wind)
//
//    var c = Canvas(900, 500)
//
//    var ticks = 0
//    while (p.position.y > 0) {
//        p = tick(e, p)
//        ticks++
//        println("$ticks ${p.position.x} ${p.position.y}")
//        if (p.position.x >= 0 && p.position.x < c.width && p.position.y >=0 && p.position.y < c.height){
//            write_pixel(
//                c,
//                (p.position.x).roundToInt(),
//                (c.height - p.position.y).roundToInt(),
//                Color(1.0, 0.8, 0.6)
//            )
//        }
//
//    }
//    File("projectile.ppm").writeText(canvas_to_ppm(c))
//
//}