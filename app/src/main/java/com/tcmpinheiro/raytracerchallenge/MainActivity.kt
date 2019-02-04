package com.tcmpinheiro.raytracerchallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tcmpinheiro.raytracerchallenge.features.normalize
import com.tcmpinheiro.raytracerchallenge.features.point
import com.tcmpinheiro.raytracerchallenge.features.vector

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        # projectile starts one unit above the origin.
//        # velocity is normalized to 1 unit/tick.
//        p ← projectile(point(0, 1, 0), normalize(vector(1, 1, 0)))
//        # gravity -0.1 unit/tick, and wind is -0.01 unit/tick.
//        e ← environment(vector(0, -0.1, 0), vector(-0.01, 0, 0))
//        Then, run tick repeatedly until the projectile’s y position is less
//        than or equal to 0. Report the projectile’s position after each tick,
//        and the number of ticks it takes for the projectile to hit the ground.
//        Try multiplying the projectile’s initial velocity by larger and larger
//        numbers to see how much farther the projectile goes!

        var p = Projectile(point(0.0, 1.0, 0.0), normalize(vector(0.1,0.1,0.0)))
        val e = Environment(vector(0.0,-0.1, 0.0), vector(-0.01, 0.0, 0.0))

        var ticks = 0
        while (p.position.y > 0){
            p = tick(e, p)
            ticks++
            Log.d("Projectile", "$ticks ${p.position.y}")
        }
    }
}
