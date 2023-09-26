package net.spartanb312.superintendent.graphics.texture

import net.spartanb312.superintendent.Superintendent
import net.spartanb312.superintendent.utils.createDirectByteBuffer
import net.spartanb312.superintendent.utils.timming.TickTimer
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12
import java.awt.image.BufferedImage
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.IntBuffer
import javax.imageio.ImageIO

object ImageUtils {

    private const val DEFAULT_BUFFER_SIZE = 0x800000
    private var byteBuffer = createDirectByteBuffer(DEFAULT_BUFFER_SIZE) // Max 8 MB
    private val reallocateTimer = TickTimer()

    /**
     * Dynamic memory allocation
     */
    private fun putIntArray(intArray: IntArray): IntBuffer {
        if (intArray.size * 4 > byteBuffer.capacity()) {
            byteBuffer.clear()
            byteBuffer = createDirectByteBuffer(intArray.size * 4)
            reallocateTimer.reset()
        } else if (
            byteBuffer.capacity() > DEFAULT_BUFFER_SIZE
            && intArray.size * 4 < DEFAULT_BUFFER_SIZE
            && reallocateTimer.tick(1000)
        ) {
            byteBuffer.clear()
            byteBuffer = createDirectByteBuffer(DEFAULT_BUFFER_SIZE)
        }

        byteBuffer.asIntBuffer().apply {
            clear()
            put(intArray)
            flip()
            return this
        }
    }

    fun uploadImage(bufferedImage: BufferedImage, format: Int, width: Int, height: Int) {
        val array = IntArray(width * height)
        bufferedImage.getRGB(0, 0, width, height, array, 0, width)

        if (Superintendent.Compatibility.intelGraphics) {
            GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D,
                0,
                format,
                width,
                height,
                0,
                GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE,
                putIntArray(array)
            )
            return
        }

        // Upload image
        if (Superintendent.Compatibility.openGL12) {
            // Use BGRA format
            GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D,
                0,
                format,
                width,
                height,
                0,
                GL12.GL_BGRA,
                GL11.GL_UNSIGNED_BYTE,
                putIntArray(array)
            )
        } else {
            // BGRA TO RGBA
            for (index in 0 until (width * height)) {
                array[index] = array[index] and -0x1000000 or  // ______AA
                        (array[index] and 0x00FF0000 shr 16) or  // RR______
                        (array[index] and 0x0000FF00) or  // __GG____
                        (array[index] and 0x000000FF shl 16) // ____BB__
            }
            GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D,
                0,
                format,
                width,
                height,
                0,
                GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE,
                putIntArray(array)
            )
        }
    }

    fun readImageToBuffer(imageStream: InputStream): ByteBuffer {
        val img = ImageIO.read(imageStream)
        val array = img.getRGB(0, 0, img.width, img.height, null, 0, img.width)
        val bytebuffer = ByteBuffer.allocate(4 * array.size)
        for (i in array) {
            bytebuffer.putInt(i shl 8 or (i shr 24 and 255))
        }
        bytebuffer.flip()
        return bytebuffer
    }

}