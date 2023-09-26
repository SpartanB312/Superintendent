package net.spartanb312.superintendent.utils.math.matrix

import java.nio.FloatBuffer

@JvmInline
value class Matrix4f(
    override val data: FloatArray
) : Matrix<Matrix4f> {

    constructor(
        a11: Float = 1f, a12: Float = 0f, a13: Float = 0f, a14: Float = 0f,
        a21: Float = 0f, a22: Float = 1f, a23: Float = 0f, a24: Float = 0f,
        a31: Float = 0f, a32: Float = 0f, a33: Float = 1f, a34: Float = 0f,
        a41: Float = 0f, a42: Float = 0f, a43: Float = 0f, a44: Float = 1f,
    ) : this(
        floatArrayOf(
            a11, a12, a13, a14,
            a21, a22, a23, a24,
            a31, a32, a33, a34,
            a41, a42, a43, a44,
        )
    )

    override val size get() = 4

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

    inline var a14
        get() = data[3]
        set(value) {
            data[3] = value
        }

    inline var a21
        get() = data[4]
        set(value) {
            data[4] = value
        }

    inline var a22
        get() = data[5]
        set(value) {
            data[5] = value
        }

    inline var a23
        get() = data[6]
        set(value) {
            data[6] = value
        }

    inline var a24
        get() = data[7]
        set(value) {
            data[7] = value
        }

    inline var a31
        get() = data[8]
        set(value) {
            data[8] = value
        }

    inline var a32
        get() = data[9]
        set(value) {
            data[9] = value
        }

    inline var a33
        get() = data[10]
        set(value) {
            data[10] = value
        }

    inline var a34
        get() = data[11]
        set(value) {
            data[11] = value
        }

    inline var a41
        get() = data[12]
        set(value) {
            data[12] = value
        }

    inline var a42
        get() = data[13]
        set(value) {
            data[13] = value
        }

    inline var a43
        get() = data[14]
        set(value) {
            data[14] = value
        }

    inline var a44
        get() = data[15]
        set(value) {
            data[15] = value
        }

    override fun setIdentity(): Matrix4f {
        a11 = 1f;a12 = 0f;a13 = 0f;a14 = 0f
        a21 = 0f;a22 = 1f;a23 = 0f;a24 = 0f
        a31 = 0f;a32 = 0f;a33 = 1f;a34 = 0f
        a41 = 0f;a42 = 0f;a43 = 0f;a44 = 1f
        return this
    }

    override fun invert(): Matrix4f? = invert(this, null)

    override fun invert(src: Matrix4f, dest: Matrix4f?): Matrix4f? {
        val determinant = src.determinant()
        return if (determinant != 0f) {
            val determinantInv = 1f / determinant

            // first row
            val t00 = determinant3x3(src.a22, src.a23, src.a24, src.a32, src.a33, src.a34, src.a42, src.a43, src.a44)
            val t01 = -determinant3x3(src.a21, src.a23, src.a24, src.a31, src.a33, src.a34, src.a41, src.a43, src.a44)
            val t02 = determinant3x3(src.a21, src.a22, src.a24, src.a31, src.a32, src.a34, src.a41, src.a42, src.a44)
            val t03 = -determinant3x3(src.a21, src.a22, src.a23, src.a31, src.a32, src.a33, src.a41, src.a42, src.a43)
            // second row
            val t10 = -determinant3x3(src.a12, src.a13, src.a14, src.a32, src.a33, src.a34, src.a42, src.a43, src.a44)
            val t11 = determinant3x3(src.a11, src.a13, src.a14, src.a31, src.a33, src.a34, src.a41, src.a43, src.a44)
            val t12 = -determinant3x3(src.a11, src.a12, src.a14, src.a31, src.a32, src.a34, src.a41, src.a42, src.a44)
            val t13 = determinant3x3(src.a11, src.a12, src.a13, src.a31, src.a32, src.a33, src.a41, src.a42, src.a43)
            // third row
            val t20 = determinant3x3(src.a12, src.a13, src.a14, src.a22, src.a23, src.a24, src.a42, src.a43, src.a44)
            val t21 = -determinant3x3(src.a11, src.a13, src.a14, src.a21, src.a23, src.a24, src.a41, src.a43, src.a44)
            val t22 = determinant3x3(src.a11, src.a12, src.a14, src.a21, src.a22, src.a24, src.a41, src.a42, src.a44)
            val t23 = -determinant3x3(src.a11, src.a12, src.a13, src.a21, src.a22, src.a23, src.a41, src.a42, src.a43)
            // fourth row
            val t30 = -determinant3x3(src.a12, src.a13, src.a14, src.a22, src.a23, src.a24, src.a32, src.a33, src.a34)
            val t31 = determinant3x3(src.a11, src.a13, src.a14, src.a21, src.a23, src.a24, src.a31, src.a33, src.a34)
            val t32 = -determinant3x3(src.a11, src.a12, src.a14, src.a21, src.a22, src.a24, src.a31, src.a32, src.a34)
            val t33 = determinant3x3(src.a11, src.a12, src.a13, src.a21, src.a22, src.a23, src.a31, src.a32, src.a33)

            val final = dest ?: Matrix4f()
            final.a11 = t00 * determinantInv
            final.a22 = t11 * determinantInv
            final.a33 = t22 * determinantInv
            final.a44 = t33 * determinantInv
            final.a12 = t10 * determinantInv
            final.a21 = t01 * determinantInv
            final.a41 = t02 * determinantInv
            final.a13 = t20 * determinantInv
            final.a23 = t21 * determinantInv
            final.a31 = t12 * determinantInv
            final.a14 = t30 * determinantInv
            final.a41 = t03 * determinantInv
            final.a24 = t31 * determinantInv
            final.a42 = t13 * determinantInv
            final.a43 = t23 * determinantInv
            final.a34 = t32 * determinantInv
            final
        } else null
    }

    override fun load(buf: FloatBuffer): Matrix4f {
        a11 = buf.get();a12 = buf.get();a13 = buf.get();a14 = buf.get()
        a21 = buf.get();a22 = buf.get();a23 = buf.get();a24 = buf.get()
        a31 = buf.get();a32 = buf.get();a33 = buf.get();a34 = buf.get()
        a41 = buf.get();a42 = buf.get();a43 = buf.get();a44 = buf.get()
        return this
    }

    override fun load(vararg values: Float): Matrix4f {
        if (values.size != size * size) throw IllegalArgumentException("Found ${values.size} values, but $size x $size matrix expect ${size * size} values")
        a11 = values[0]
        a12 = values[1]
        a13 = values[2]
        a14 = values[3]
        a21 = values[4]
        a22 = values[5]
        a23 = values[6]
        a24 = values[7]
        a31 = values[8]
        a32 = values[9]
        a33 = values[10]
        a34 = values[11]
        a41 = values[12]
        a42 = values[13]
        a43 = values[14]
        a44 = values[15]
        return this
    }

    override fun loadTranspose(buf: FloatBuffer): Matrix4f {
        a11 = buf.get()
        a21 = buf.get()
        a31 = buf.get()
        a41 = buf.get()
        a12 = buf.get()
        a22 = buf.get()
        a32 = buf.get()
        a42 = buf.get()
        a13 = buf.get()
        a23 = buf.get()
        a33 = buf.get()
        a43 = buf.get()
        a14 = buf.get()
        a24 = buf.get()
        a34 = buf.get()
        a44 = buf.get()
        return this
    }

    override fun loadTranspose(vararg values: Float): Matrix4f {
        a11 = values[0]
        a21 = values[1]
        a31 = values[2]
        a41 = values[3]
        a12 = values[4]
        a22 = values[5]
        a32 = values[6]
        a42 = values[7]
        a13 = values[8]
        a23 = values[9]
        a33 = values[10]
        a43 = values[11]
        a14 = values[12]
        a24 = values[13]
        a34 = values[14]
        a44 = values[15]
        return this
    }

    override fun negate(): Matrix4f = negate(this, null)

    override fun negate(src: Matrix4f, dest: Matrix4f?): Matrix4f {
        val final = dest ?: Matrix4f()
        final.a11 = -src.a11
        final.a12 = -src.a12
        final.a13 = -src.a13
        final.a14 = -src.a14
        final.a21 = -src.a21
        final.a22 = -src.a22
        final.a23 = -src.a23
        final.a24 = -src.a24
        final.a31 = -src.a31
        final.a32 = -src.a32
        final.a33 = -src.a33
        final.a34 = -src.a34
        final.a41 = -src.a41
        final.a42 = -src.a42
        final.a43 = -src.a43
        final.a44 = -src.a44
        return final
    }

    override fun store(buf: FloatBuffer): Matrix4f {
        buf.put(a11)
        buf.put(a12)
        buf.put(a13)
        buf.put(a14)
        buf.put(a21)
        buf.put(a22)
        buf.put(a23)
        buf.put(a24)
        buf.put(a31)
        buf.put(a32)
        buf.put(a33)
        buf.put(a34)
        buf.put(a41)
        buf.put(a42)
        buf.put(a43)
        buf.put(a44)
        return this
    }

    override fun storeTranspose(buf: FloatBuffer): Matrix4f {
        buf.put(a11)
        buf.put(a21)
        buf.put(a31)
        buf.put(a41)
        buf.put(a12)
        buf.put(a22)
        buf.put(a32)
        buf.put(a42)
        buf.put(a13)
        buf.put(a23)
        buf.put(a33)
        buf.put(a43)
        buf.put(a14)
        buf.put(a24)
        buf.put(a34)
        buf.put(a44)
        return this
    }

    override fun transpose(): Matrix4f = transpose(this, null)

    override fun transpose(src: Matrix4f, dest: Matrix4f?): Matrix4f {
        val final = dest ?: Matrix4f()
        val a11 = src.a11
        val a12 = src.a12
        val a13 = src.a13
        val a14 = src.a14
        val a21 = src.a21
        val a22 = src.a22
        val a23 = src.a23
        val a24 = src.a24
        val a31 = src.a31
        val a32 = src.a32
        val a33 = src.a33
        val a34 = src.a34
        val a41 = src.a41
        val a42 = src.a42
        val a43 = src.a43
        val a44 = src.a44
        final.a11 = a11
        final.a12 = a12
        final.a13 = a13
        final.a14 = a14
        final.a21 = a21
        final.a22 = a22
        final.a23 = a23
        final.a24 = a24
        final.a31 = a31
        final.a32 = a32
        final.a33 = a33
        final.a34 = a34
        final.a41 = a41
        final.a42 = a42
        final.a43 = a43
        final.a44 = a44
        return final
    }

    override fun setZero(): Matrix4f {
        a11 = 0f;a12 = 0f;a13 = 0f;a14 = 0f
        a21 = 0f;a22 = 0f;a23 = 0f;a24 = 0f
        a31 = 0f;a32 = 0f;a33 = 0f;a34 = 0f
        a41 = 0f;a42 = 0f;a43 = 0f;a44 = 0f
        return this
    }

    override fun determinant(): Float {
        var result = 0f
        result += (a11 * (a22 * a33 * a44 + a23 * a34 * a42 + a24 * a32 * a43 - a24 * a33 * a42 - a22 * a34 * a43 - a23 * a32 * a44))
        result -= (a12 * (a21 * a33 * a44 + a23 * a34 * a41 + a24 * a31 * a43 - a24 * a33 * a41 - a21 * a34 * a43 - a23 * a31 * a44))
        result += (a13 * (a21 * a32 * a44 + a22 * a34 * a41 + a24 * a31 * a42 - a24 * a32 * a41 - a21 * a34 * a42 - a22 * a31 * a44))
        result -= (a14 * (a21 * a32 * a43 + a22 * a33 * a41 + a23 * a31 * a42 - a23 * a32 * a41 - a21 * a33 * a42 - a22 * a31 * a43))
        return result
    }

}