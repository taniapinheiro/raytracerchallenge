package com.tcmpinheiro.raytracerchallenge.features

data class World(val light: PointLight, val objects: MutableSet<TargetObject> = mutableSetOf())


fun defaultWorld(pointLight: PointLight = PointLight(point(-10.0, 10.0, -10.0), Color(1.0, 1.0, 1.0))): World{
    val world = World(pointLight)
    val s1 = sphere()
    s1.material = Material()
        .copy(color = Color(0.8, 1.0, 0.6),
            diffuse = 0.7,
            specular = 0.2)
    val s2 = sphere()
    s2.transform = scaling(0.5, 0.5, 0.5)

    world.objects.add(s1)
    world.objects.add(s2)
    return world
}


fun intersect_world(world: World, ray: Ray) : List<Intersection>{
    return world.objects.flatMap { shape -> intersect(shape, ray) }
        .sortedBy { it.t}
}

class Computations{
    var t: Double = 0.0
    lateinit var shape: TargetObject
    lateinit var point: Tuple
    lateinit var eyev: Tuple
    lateinit var normalv: Tuple
    var inside:Boolean = false
    lateinit var overPoint: Tuple

}

const val EPSILON = 0.00001

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
    comps.overPoint = comps.point + comps.normalv * EPSILON
    if (dot(comps.normalv, comps.eyev) < 0) {
        comps.inside = true
        comps.normalv = -comps.normalv
    }
    return comps
}

fun shadeHit(world: World, comps: Computations): Color {
    val shadowed = is_shadowed(world, comps.overPoint)
    return lighting(
        comps.shape.material,
        world.light,
        comps.point,
        comps.eyev,
        comps.normalv,
        shadowed)
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

fun is_shadowed(world: World, point:Tuple): Boolean {
    val v = world.light.position - point
    val distance = magnitude(v)
    val ray = Ray(point, normalize(v))
    val intersections = intersect_world(world, ray)
    val hit = hit(intersections)
    return hit != null && hit.t < distance
}