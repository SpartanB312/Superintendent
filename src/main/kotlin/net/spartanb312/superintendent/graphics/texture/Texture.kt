package net.spartanb312.superintendent.graphics.texture

import net.spartanb312.superintendent.Superintendent
import net.spartanb312.superintendent.graphics.core.GLStatesManager
import net.spartanb312.superintendent.graphics.core.vertex.ArrayedVertexBuffer.buffer
import net.spartanb312.superintendent.graphics.core.vertex.VertexFormat
import net.spartanb312.superintendent.graphics.texture.ImageUtils.uploadImage
import net.spartanb312.superintendent.utils.awt.ColorRGB
import org.lwjgl.opengl.EXTFramebufferObject
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12
import org.lwjgl.opengl.GL14
import org.lwjgl.opengl.GL30
import java.awt.image.BufferedImage

/**
 * Mipmap texture
 */
class Texture(
    bufferedImage: BufferedImage,
    format: Int,
    useMipmap: Boolean = true
) {

    val textureID = glGenTextures()

    var available = true
        private set

    var width = 0; private set
    var height = 0; private set

    init {
        useTexture {
            width = bufferedImage.width
            height = bufferedImage.height

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)

            // Filters
            if (Superintendent.compatibilityMode) glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
            else glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

            if (!Superintendent.compatibilityMode) {
                //if (useMipmap) glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST)

                if (useMipmap && Superintendent.Compatibility.openGL12) {
                    glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 0)
                    glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 3)
                    glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0)
                    glTexParameteri(GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 3)
                }
            }

            uploadImage(bufferedImage, format, width, height)

            if (useMipmap && !Superintendent.compatibilityMode) when {
                Superintendent.Compatibility.openGL30 -> GL30.glGenerateMipmap(GL_TEXTURE_2D)
                Superintendent.Compatibility.extFramebufferObject -> EXTFramebufferObject.glGenerateMipmapEXT(GL_TEXTURE_2D)
                Superintendent.Compatibility.openGL14 -> glTexParameteri(GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL_TRUE)
            }
        }
    }

    fun bindTexture() {
        if (available) glBindTexture(GL_TEXTURE_2D, textureID)
        else throw IllegalStateException("This texture is already deleted!")
    }

    fun unbindTexture() = glBindTexture(GL_TEXTURE_2D, 0)

    fun deleteTexture() {
        glDeleteTextures(textureID)
        available = false
    }

    override fun equals(other: Any?) =
        this === other || other is Texture
                && this.textureID == other.textureID

    override fun hashCode() = textureID

    fun drawTexture(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        u: Float = 0f,
        v: Float = 0f,
        u1: Float = width.toFloat(),
        v1: Float = height.toFloat(),
        color: ColorRGB = ColorRGB.WHITE,
    ): Texture {
        val startU = u / width.toFloat()
        val endU = u1 / width.toFloat()
        val startV = v / height.toFloat()
        val endV = v1 / height.toFloat()

        GLStatesManager.texture2d = true
        GLStatesManager.alpha = true
        GLStatesManager.blend = true
        useTexture {
            GL_QUADS.buffer(VertexFormat.Pos2fTex, 4) {
                v2Tex2fC(endX, startY, endU, startV, color)
                v2Tex2fC(startX, startY, startU, startV, color)
                v2Tex2fC(startX, endY, startU, endV, color)
                v2Tex2fC(endX, endY, endU, endV, color)
            }
        }
        GLStatesManager.texture2d = false
        return this
    }

    inline fun useTexture(
        force: Boolean = false,
        block: Texture.() -> Unit,
    ): Texture {
        if (force || available) {
            bindTexture()
            this.block()
            unbindTexture()
        }
        return this
    }

}

infix fun Texture.minFilter(mode: Int): Texture = useTexture {
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, mode)
}

infix fun Texture.magFilter(mode: Int): Texture = useTexture {
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, mode)
}

infix fun Texture.wrapS(mode: Int): Texture = useTexture {
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, mode)
}

infix fun Texture.wrapT(mode: Int): Texture = useTexture {
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, mode)
}