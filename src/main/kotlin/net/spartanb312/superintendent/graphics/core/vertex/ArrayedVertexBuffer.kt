@file:Suppress("NOTHING_TO_INLINE")

package net.spartanb312.superintendent.graphics.core.vertex

import net.spartanb312.superintendent.graphics.core.draw.RenderUtils.glSafe
import net.spartanb312.superintendent.utils.awt.ColorRGB
import net.spartanb312.superintendent.utils.createDirectByteBuffer
import org.lwjgl.opengl.GL11.*
import sun.nio.ch.DirectBuffer
import java.nio.ByteBuffer

/**
 * Use vertex array
 */
object ArrayedVertexBuffer {

    fun Int.buffer(
        format: VertexFormat = VertexFormat.Pos2fColor,
        count: Int = 16,
        draw: Boolean = true,
        block: BufferBuilder.() -> Unit,
    ): BufferBuilder = BufferBuilder(format, format.totalLength * count).apply {
        block()
        if (draw) draw(this@buffer)
    }

    fun Int.safeBuffer(
        format: VertexFormat = VertexFormat.Pos2fColor,
        count: Int = 16,
        draw: Boolean = true,
        block: BufferBuilder.() -> Unit,
    ): BufferBuilder = BufferBuilder(format, format.totalLength * count).apply {
        block()
        glSafe {
            if (draw) draw(this@safeBuffer)
        }
    }


    fun getBuffer(
        format: VertexFormat,
        count: Int,
        blocK: BufferBuilder.() -> Unit,
    ): ByteBuffer {
        val bufferBuilder = BufferBuilder(format, format.totalLength * count)
        bufferBuilder.blocK()
        return bufferBuilder.buffer.also { it.flip() }
    }

    class BufferBuilder(private val format: VertexFormat, size: Int) {

        companion object {
            private var directBuffer = createDirectByteBuffer(1024)
            private var currentSize = 1024
            private fun getDirectBuffer(size: Int): ByteBuffer {
                if (size > currentSize) {
                    (directBuffer as DirectBuffer).cleaner().clean()
                    currentSize = size
                    directBuffer = createDirectByteBuffer(size)
                } else directBuffer.clear()
                return directBuffer
            }
        }

        val buffer = getDirectBuffer(size)

        var vertexCount = 0; private set

        fun v2f(x: Float, y: Float): BufferBuilder {
            buffer.putFloat(x)
            buffer.putFloat(y)
            vertexCount++
            return this
        }

        fun v3f(x: Float, y: Float, z: Float): BufferBuilder {
            buffer.putFloat(x)
            buffer.putFloat(y)
            buffer.putFloat(z)
            vertexCount++
            return this
        }

        fun v2d(x: Double, y: Double): BufferBuilder {
            buffer.putDouble(x)
            buffer.putDouble(y)
            vertexCount++
            return this
        }

        fun v3d(x: Double, y: Double, z: Double): BufferBuilder {
            buffer.putDouble(x)
            buffer.putDouble(y)
            buffer.putDouble(z)
            vertexCount++
            return this
        }

        fun v2i(x: Int, y: Int): BufferBuilder {
            buffer.putInt(x)
            buffer.putInt(y)
            vertexCount++
            return this
        }

        fun v3i(x: Int, y: Int, z: Int): BufferBuilder {
            buffer.putInt(x)
            buffer.putInt(y)
            buffer.putInt(z)
            vertexCount++
            return this
        }

        fun tex(u: Float, v: Float): BufferBuilder {
            buffer.putFloat(u)
            buffer.putFloat(v)
            return this
        }

        fun color(colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            buffer.put(colorRGB.r.toByte())
            buffer.put(colorRGB.g.toByte())
            buffer.put(colorRGB.b.toByte())
            buffer.put(colorRGB.a.toByte())
            return this
        }

        fun color(r: Int, g: Int, b: Int, a: Int = 255): BufferBuilder {
            buffer.put(r.toByte())
            buffer.put(g.toByte())
            buffer.put(b.toByte())
            buffer.put(a.toByte())
            return this
        }

        fun color(r: Float, g: Float, b: Float, a: Float = 1f): BufferBuilder = color(
            (r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt(), (a * 255).toInt()
        )

        fun v2fc(x: Float, y: Float, colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            v2f(x, y)
            color(colorRGB)
            return this
        }

        fun v3fc(x: Float, y: Float, z: Float, colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            v3f(x, y, z)
            color(colorRGB)
            return this
        }

        fun v2dc(x: Double, y: Double, colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            v2d(x, y)
            color(colorRGB)
            return this
        }

        fun v3dc(x: Double, y: Double, z: Double, colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            v3d(x, y, z)
            color(colorRGB)
            return this
        }

        fun v2ic(x: Int, y: Int, colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            v2i(x, y)
            color(colorRGB)
            return this
        }

        fun v3ic(x: Int, y: Int, z: Int, colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            v3i(x, y, z)
            color(colorRGB)
            return this
        }

        fun v2Tex2fC(x: Float, y: Float, u: Float, v: Float, colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            v2f(x, y)
            color(colorRGB)
            tex(u, v)
            return this
        }

        fun v2Tex2dC(x: Double, y: Double, u: Float, v: Float, colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            v2d(x, y)
            color(colorRGB)
            tex(u, v)
            return this
        }

        fun v2Tex2iC(x: Int, y: Int, u: Float, v: Float, colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            v2i(x, y)
            color(colorRGB)
            tex(u, v)
            return this
        }

        fun v3Tex2fC(
            x: Float,
            y: Float,
            z: Float,
            u: Float,
            v: Float,
            colorRGB: ColorRGB = ColorRGB.WHITE,
        ): BufferBuilder {
            v3f(x, y, z)
            color(colorRGB)
            tex(u, v)
            return this
        }

        fun v3Tex2dC(
            x: Double,
            y: Double,
            z: Double,
            u: Float,
            v: Float,
            colorRGB: ColorRGB = ColorRGB.WHITE,
        ): BufferBuilder {
            v3d(x, y, z)
            color(colorRGB)
            tex(u, v)
            return this
        }

        fun v3Tex2iC(x: Int, y: Int, z: Int, u: Float, v: Float, colorRGB: ColorRGB = ColorRGB.WHITE): BufferBuilder {
            v3i(x, y, z)
            color(colorRGB)
            tex(u, v)
            return this
        }

        fun n3f(x: Float, y: Float, z: Float): BufferBuilder {
            buffer.putFloat(x)
            buffer.putFloat(y)
            buffer.putFloat(z)
            return this
        }

        private inline fun preDraw() {
            buffer.flip()
            var index = 0
            format.elements.forEach {
                if (buffer.limit() < it.length) return
                when (it.category) {
                    VertexFormat.Element.Category.Position -> {
                        glEnableClientState(GL_VERTEX_ARRAY)
                        glVertexPointer(
                            it.count,
                            it.constant,
                            format.totalLength,
                            buffer.position(index) as ByteBuffer
                        ) // JRE8 NEEDS USELESS CAST
                        index += it.length
                    }

                    VertexFormat.Element.Category.Color -> {
                        glEnableClientState(GL_COLOR_ARRAY)
                        glColorPointer(it.count, it.constant, format.totalLength, buffer.position(index) as ByteBuffer)
                        index += it.length
                    }

                    VertexFormat.Element.Category.Texture -> {
                        glTexCoordPointer(
                            it.count,
                            it.constant,
                            format.totalLength,
                            buffer.position(index) as ByteBuffer
                        )
                        glEnableClientState(GL_TEXTURE_COORD_ARRAY)
                        index += it.length
                    }
                }
            }
        }

        fun draw(mode: Int) {
            preDraw()
            glDrawArrays(mode, 0, vertexCount)
            postDraw()
        }

        private inline fun postDraw() {
            format.elements.forEach {
                when (it.category) {
                    VertexFormat.Element.Category.Position -> glDisableClientState(GL_VERTEX_ARRAY)
                    VertexFormat.Element.Category.Color -> glDisableClientState(GL_COLOR_ARRAY)
                    VertexFormat.Element.Category.Texture -> glDisableClientState(GL_TEXTURE_COORD_ARRAY)
                }
            }
        }
    }

}