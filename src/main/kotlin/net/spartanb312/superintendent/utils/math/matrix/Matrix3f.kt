package net.spartanb312.superintendent.utils.math.matrix

import java.nio.FloatBuffer

@JvmInline
value class Matrix3f(
    override val data: FloatArray
) : Matrix<Matrix3f> {

    constructor(
        a11: Float = 1f, a12: Float = 0f, a13: Float = 0f,
        a21: Float = 0f, a22: Float = 1f, a23: Float = 0f,
        a31: Float = 0f, a32: Float = 0f, a33: Float = 1f,
    ) : this(
        floatArrayOf(
            a11, a12, a13,
            a21, a22, a23,
            a31, a32, a33,
        )
    )

    override val size get() = 3

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

    inline var a13
        get() = data[2]
        set(value) {
            data[2] = value
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

    inline var a23
        get() = data[5]
        set(value) {
            data[5] = value
        }

    inline var a31
        get() = data[6]
        set(value) {
            data[6] = value
        }

    inline var a32
        get() = data[7]
        set(value) {
            data[7] = value
        }

    inline var a33
        get() = data[8]
        set(value) {
            data[8] = value
        }

    override fun setIdentity(): Matrix3f {
        a11 = 1f;a12 = 0f;a13 = 0f
        a21 = 0f;a22 = 1f;a23 = 0f
        a31 = 0f;a32 = 0f;a33 = 1f
        return this
    }

    override fun invert(): Matrix3f? = invert(this, null)

    override fun invert(src: Matrix3f, dest: Matrix3f?): Matrix3f? {
        val determinant = src.determinant()
        return if (determinant != 0f) {
            val final = dest ?: Matrix3f()
            val determinantInv = 1f / determinant
            val t00: Float = src.a22 * src.a33 - src.a23 * src.a32
            val t01: Float = -src.a21 * src.a33 + src.a23 * src.a31
            val t02: Float = src.a21 * src.a32 - src.a22 * src.a31
            val t10: Float = -src.a12 * src.a33 + src.a13 * src.a32
            val t11: Float = src.a11 * src.a33 - src.a13 * src.a31
            val t12: Float = -src.a11 * src.a32 + src.a12 * src.a31
            val t20: Float = src.a12 * src.a23 - src.a13 * src.a22
            val t21: Float = -src.a11 * src.a23 + src.a13 * src.a21
            val t22: Float = src.a11 * src.a22 - src.a12 * src.a21
            final.a11 = t00 * determinantInv
            final.a22 = t11 * determinantInv
            final.a33 = t22 * determinantInv
            final.a12 = t10 * determinantInv
            final.a21 = t01 * determinantInv
            final.a31 = t02 * determinantInv
            final.a13 = t20 * determinantInv
            final.a23 = t21 * determinantInv
            final.a32 = t12 * determinantInv
            final
        } else null
    }

    override fun load(buf: FloatBuffer): Matrix3f {
        a11 = buf.get();a12 = buf.get();a13 = buf.get()
        a21 = buf.get();a22 = buf.get();a23 = buf.get()
        a31 = buf.get();a32 = buf.get();a33 = buf.get()
        return this
    }

    override fun load(vararg values: Float): Matrix3f {
        if (values.size != size * size) throw IllegalArgumentException("Found ${values.size} values, but $size x $size matrix expect ${size * size} values")
        a11 = values[0]
        a12 = values[1]
        a13 = values[2]
        a21 = values[3]
        a22 = values[4]
        a23 = values[5]
        a31 = values[6]
        a32 = values[7]
        a33 = values[8]
        return this
    }

    override fun loadTranspose(buf: FloatBuffer): Matrix3f {
        a11 = buf.get()
        a21 = buf.get()
        a31 = buf.get()
        a12 = buf.get()
        a22 = buf.get()
        a32 = buf.get()
        a13 = buf.get()
        a23 = buf.get()
        a33 = buf.get()
        return this
    }

    override fun loadTranspose(vararg values: Float): Matrix3f {
        a11 = values[0]
        a21 = values[1]
        a31 = values[2]
        a12 = values[3]
        a22 = values[4]
        a32 = values[5]
        a13 = values[6]
        a23 = values[7]
        a33 = values[8]
        return this
    }

    override fun negate(): Matrix3f = negate(this, null)

    override fun negate(src: Matrix3f, dest: Matrix3f?): Matrix3f {
        val final = dest ?: Matrix3f()
        final.a11 = -src.a11
        final.a12 = -src.a12
        final.a13 = -src.a13
        final.a21 = -src.a21
        final.a22 = -src.a22
        final.a23 = -src.a23
        final.a31 = -src.a31
        final.a32 = -src.a32
        final.a33 = -src.a33
        return final
    }

    override fun store(buf: FloatBuffer): Matrix3f {
        buf.put(a11)
        buf.put(a12)
        buf.put(a13)
        buf.put(a21)
        buf.put(a22)
        buf.put(a23)
        buf.put(a31)
        buf.put(a32)
        buf.put(a33)
        return this
    }

    override fun storeTranspose(buf: FloatBuffer): Matrix3f {
        buf.put(a11)
        buf.put(a21)
        buf.put(a31)
        buf.put(a12)
        buf.put(a22)
        buf.put(a32)
        buf.put(a13)
        buf.put(a23)
        buf.put(a33)
        return this
    }

    override fun transpose(): Matrix3f = transpose(this, null)

    override fun transpose(src: Matrix3f, dest: Matrix3f?): Matrix3f {
        val final = dest ?: Matrix3f()
        val a11 = src.a11
        val a12 = src.a12
        val a13 = src.a13
        val a21 = src.a21
        val a22 = src.a22
        val a23 = src.a23
        val a31 = src.a31
        val a32 = src.a32
        val a33 = src.a33
        final.a11 = a11
        final.a12 = a12
        final.a13 = a13
        final.a21 = a21
        final.a22 = a22
        final.a23 = a23
        final.a31 = a31
        final.a32 = a32
        final.a33 = a33
        return final
    }

    override fun setZero(): Matrix3f {
        a11 = 0f;a12 = 0f;a13 = 0f
        a21 = 0f;a22 = 0f;a23 = 0f
        a31 = 0f;a32 = 0f;a33 = 0f
        return this
    }

    override fun determinant(): Float {
        return a11 * (a22 * a33 - a23 * a32) + a12 * (a23 * a31 - a21 * a33) + a13 * (a21 * a32 - a22 * a31)
    }

}