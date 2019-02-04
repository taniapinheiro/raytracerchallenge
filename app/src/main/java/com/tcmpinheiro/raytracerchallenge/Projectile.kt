package com.tcmpinheiro.raytracerchallenge

import com.tcmpinheiro.raytracerchallenge.features.Tuple


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