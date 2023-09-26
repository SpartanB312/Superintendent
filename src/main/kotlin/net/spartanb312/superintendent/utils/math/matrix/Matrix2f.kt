package net.spartanb312.superintendent.utils.math.matrix

import java.nio.FloatBuffer

@JvmInline
value class Matrix2f(
    override val data: FloatArray
) : Matrix<Matrix2f> {

    constructor(
        a11: Float = 1f, a12: Float = 0f,
        a21: Float = 0f, a22: Float = 1f,
    ) : this(
        floatArrayOf(
            a11, a12,
            a21, a22,
        )
    )

    override val size get() = 2

    inline var a11
        get() = data[0]
        set(value) {
            data[0] = value
        }

    inline var a12
        get() = data[1]
        set(value) {
            data[1] = value
        }

    inline var a21
        get() = data[3]
        set(value) {
            data[3] = value
        }

    inline var a22
        get() = data[4]
        set(value) {
            data[4] = value
        }

    override fun setIdentity(): Matrix2f {
        a11 = 1f;a12 = 0f
        a21 = 0f;a22 = 1f
        return this
    }

    override fun invert(): Matrix2f? = invert(this, null)

    override fun invert(src: Matrix2f, dest: Matrix2f?): Matrix2f? {
        val determinant = src.determinant()
        return if (determinant != 0f) {
            val final = dest ?: Matrix2f()
            val determinantInv = 1f / determinant
            val t00: Float = src.a22 * determinantInv
            val t01: Float = -src.a12 * determinantInv
            val t11: Float = src.a11 * determinantInv
            val t10: Float = -src.a21 * determinantInv
            final.a11 = t00
            final.a12 = t01
            final.a21 = t10
            final.a22 = t11
            final
        } else null
    }

    override fun load(buf: FloatBuffer): Matrix2f {
        a11 = buf.get();a12 = buf.get()
        a21 = buf.get();a22 = buf.get()
        return this
    }

    override fun load(vararg values: Float): Matrix2f {
        if (values.size != size * size) throw IllegalArgumentException("Found ${values.size} values, but $size x $size matrix expect ${size * size} values")
        a11 = values[0]
        a12 = values[1]
        a21 = values[2]
        a22 = values[3]
        return this
    }

    override fun loadTranspose(buf: FloatBuffer): Matrix2f {
        a11 = buf.get()
        a21 = buf.get()
        a12 = buf.get()
        a22 = buf.get()
        return this
    }

    override fun loadTranspose(vararg values: Float): Matrix2f {
        a11 = values[0]
        a21 = values[1]
        a12 = values[2]
        a22 = values[3]
        return this
    }

    override fun negate(): Matrix2f = negate(this, null)

    override fun negate(src: Matrix2f, dest: Matrix2f?): Matrix2f {
        val final = dest ?: Matrix2f()
        final.a11 = -src.a11
        final.a12 = -src.a12
        final.a21 = -src.a21
        final.a22 = -src.a22
        return final
    }

    override fun store(buf: FloatBuffer): Matrix2f {
        buf.put(a11)
        buf.put(a12)
        buf.put(a21)
        buf.put(a22)
        return this
    }

    override fun storeTranspose(buf: FloatBuffer): Matrix2f {
        buf.put(a11)
        buf.put(a21)
        buf.put(a12)
        buf.put(a22)
        return this
    }

    override fun transpose(): Matrix2f = transpose(this, null)

    override fun transpose(src: Matrix2f, dest: Matrix2f?): Matrix2f {
        val final = dest ?: Matrix2f()
        val a11 = src.a11
        val a12 = src.a12
        val a21 = src.a21
        val a22 = src.a22
        final.a11 = a11
        final.a12 = a12
        final.a21 = a21
        final.a22 = a22
        return final
    }

    override fun setZero(): Matrix2f {
        a11 = 0f;a12 = 0f
        a21 = 0f;a22 = 0f
        return this
    }

    override fun determinant(): Float {
        return a11 * a22 - a12 * a21
    }

}