package net.spartanb312.superintendent.utils.math.vector

data class Vec3d(val x: Double = 0.0, val y: Double = 0.0, val z: Double = 0.0) {

    constructor(x: Number, y: Number, z: Number) : this(x.toDouble(), y.toDouble(), z.toDouble())

    constructor(vec3d: Vec3d) : this(vec3d.x, vec3d.y, vec3d.z)

    constructor(vec2d: Vec2d, z: Double = 0.0) : this(vec2d.x, vec2d.y, z)

    inline val xInt get() = x.toInt()
    inline val yInt get() = y.toInt()
    inline val zInt get() = z.toInt()
    inline val xLong get() = x.toLong()
    inline val yLong get() = y.toLong()
    inline val zLong get() = z.toLong()
    inline val xFloat get() = x.toFloat()
    inline val yFloat get() = y.toFloat()
    inline val zFloat get() = z.toFloat()

    // Divide
    operator fun div(vec3d: Vec3d) = div(vec3d.x, vec3d.y, vec3d.z)

    operator fun div(divider: Double) = div(divider, divider, divider)

    fun div(x: Double, y: Double, z: Double) = Vec3d(this.x / x, this.y / y, this.z / z)

    // Multiply
    operator fun times(vec3d: Vec3d) = times(vec3d.x, vec3d.y, vec3d.z)

    operator fun times(multiplier: Double) = times(multiplier, multiplier, multiplier)

    fun times(x: Double, y: Double, z: Double) = Vec3d(this.x * x, this.y * y, this.y * z)

    // Minus
    operator fun minus(vec3d: Vec3d) = minus(vec3d.x, vec3d.y, vec3d.z)

    operator fun minus(sub: Double) = minus(sub, sub, sub)

    fun minus(x: Double, y: Double, z: Double) = plus(-x, -y, -z)

    // Plus
    operator fun plus(vec3d: Vec3d) = plus(vec3d.x, vec3d.y, vec3d.z)

    operator fun plus(add: Double) = plus(add, add, add)

    fun plus(x: Double, y: Double, z: Double) = Vec3d(this.x + x, this.y + y, this.z + z)

    infix fun dot(target: Vec3d): Double = x * target.x + y * target.y + z * target.z

    infix fun cross(target: Vec3d): Vec3d = Vec3d(
        y * target.z - z * target.y,
        z * target.x - x * target.z,
        x * target.y - y * target.x
    )

    override fun toString(): String {
        return "Vec3d[${this.x}, ${this.y}, ${this.z}]"
    }

    companion object {
        @JvmField
        val ZERO = Vec3d(0.0, 0.0)
    }

}