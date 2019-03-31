package com.tcmpinheiro.raytracerchallenge.features

class World{
    var light:PointLight? = null
    var objects: Set<TargetObject> = hashSetOf()
}

fun defaultWorld() : World{
    val world = World()
    val light = PointLight(point(-10.0, 10.0, -10.0), Color(1.0, 1.0, 1.0))
    val s1 = sphere()
    s1.material = Material()
        .copy(color = Color(0.8, 1.0, 0.6),
            diffuse = 0.7,
            specular = 0.2)
    val s2 = sphere()
    s2.transform = scaling(0.5, 0.5, 0.5)

    world.light = light
    world.objects = setOf(s1, s2)
    return world
}


fun intersect_world(world: World, ray: Ray) : List<Intersection>{
    val intersections = arrayListOf<Intersection>()
    for (shape in world.objects){
        intersections.addAll(intersect(shape,ray))
    }
    return intersections.sortedBy { it.t }
}

class Computations{
    var t: Double = 0.0
    lateinit var shape: TargetObject
    lateinit var point: Tuple
    lateinit var eyev: Tuple
    lateinit var normalv: Tuple
    var inside:Boolean = false

}

fun prepare_computations(intersection: Intersection, ray: Ray): Computations {
    //instantiate a data structure for storing some precomputed values
    val comps = Computations()
    //copy the intersection's properties, for convenience
    comps.t = intersection.t
    comps.shape = intersection.target
    //precompute some useful values
    comps.point = position(ray, comps.t)
    comps.eyev = -ray.direction
    comps.normalv = normal_at(comps.shape, comps.point)
    if (dot(comps.normalv, comps.eyev) < 0) {
        comps.inside = true
        comps.normalv = -comps.normalv
    }
    return comps
}

fun shadeHit(world: World, comps: Computations): Color {
    return lighting(
        comps.shape.material,
        world.light!!,
        comps.point,
        comps.eyev,
        comps.normalv)
}

fun colorAt(world: World, ray: Ray): Color {
    val intersections = intersect_world(world, ray)
    val hit = hit(intersections)
    return if (hit == null){
        Color(0.0, 0.0, 0.0)
    } else {
        val comps = prepare_computations(hit, ray)
        shadeHit(world, comps)
    }
}