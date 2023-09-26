package net.spartanb312.superintendent.utils.math.matrix

import java.io.Serializable
import java.nio.FloatBuffer

interface Matrix<T> : Serializable {

    val size: Int

    val data: FloatArray

    fun setIdentity(): T

    fun invert(): T?

    fun invert(src: T, dest: T? = null): T?

    fun load(buf: FloatBuffer): T

    fun load(vararg values: Float): T

    fun loadTranspose(buf: FloatBuffer): T

    fun loadTranspose(vararg values: Float): T

    fun negate(): T

    fun negate(src: T, dest: T? = null): T

    fun store(buf: FloatBuffer): T

    fun storeTranspose(buf: FloatBuffer): T

    fun transpose(): T

    fun transpose(src: T, dest: T? = null): T

    fun setZero(): T

    fun determinant(): Float

    fun determinant3x3(
        t00: Float, t01: Float, t02: Float,
        t10: Float, t11: Float, t12: Float,
        t20: Float, t21: Float, t22: Float
    ): Float = t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22) + t02 * (t10 * t21 - t11 * t20)

    fun getBuffer(): FloatBuffer = FloatBuffer.wrap(data)

}