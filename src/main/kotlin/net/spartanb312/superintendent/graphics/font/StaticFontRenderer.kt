package net.spartanb312.superintendent.graphics.font

import net.spartanb312.superintendent.Superintendent
import net.spartanb312.superintendent.graphics.texture.Texture
import net.spartanb312.superintendent.utils.awt.ColorRGB
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class StaticFontRenderer(
    val text: String,
    font: Font,
    size: Int,
    antiAlias: Boolean = true,
    fractionalMetrics: Boolean = false,
    imgSize: Int = 512,
    linearMag: Boolean = true,
    useMipmap: Boolean = true,
    override var scaleFactor: Float = 1f,
) : IFontRenderer {

    private val scaledOffset = (4 * size / 25f).toInt()

    override var height = 0

    override fun getWidth0(text: String): Int {
        TODO("Not yet implemented")
    }

    override fun getWidth(text: String, scale: Float): Float {
        TODO("Not yet implemented")
    }

    override fun getWidth(char: Char, scale: Float): Float {
        TODO("Not yet implemented")
    }

    override fun drawString0(
        text: String,
        x: Float,
        y: Float,
        color0: ColorRGB,
        gradient: Boolean,
        colors: Array<ColorRGB>,
        sliceMode: Boolean,
        scale0: Float,
        shadow: Boolean
    ) {
        TODO("Not yet implemented")
    }

    fun setScale(scale: Float): StaticFontRenderer {
        this.scaleFactor = scale
        return this
    }

    private val texture = kotlin.run {
        val img = BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB)
        (img.createGraphics()).let {
            it.font = font
            it.color = Color(255, 255, 255, 0)
            it.fillRect(0, 0, imgSize, imgSize)
            it.color = Color.white
            it.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                if (fractionalMetrics) RenderingHints.VALUE_FRACTIONALMETRICS_ON
                else RenderingHints.VALUE_FRACTIONALMETRICS_OFF
            )
            it.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                if (antiAlias) RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                else RenderingHints.VALUE_TEXT_ANTIALIAS_OFF
            )
            it.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                if (antiAlias) RenderingHints.VALUE_ANTIALIAS_ON
                else RenderingHints.VALUE_ANTIALIAS_OFF
            )
            val metrics = it.fontMetrics
            var charHeight = 0
            var posX = 0
            var posY = 1

            for (char in text) {
                val dimension = metrics.getStringBounds(char.toString(), it)
                val width = dimension.bounds.width
                val height = dimension.bounds.height
                val imgWidth = width + scaledOffset * 2
                if (height > charHeight) {
                    charHeight = height
                    if (charHeight > this.height) this.height = charHeight // Set the max height as Font height
                }
                if (posX + imgWidth > imgSize) {
                    posX = 0
                    posY += charHeight
                    charHeight = 0
                }

                it.drawString(
                    char.toString(),
                    posX + scaledOffset,
                    posY + metrics.ascent
                )
                posX += imgWidth
            }
        }

        return@run Texture(img, GL11.GL_RGBA, useMipmap && !Superintendent.compatibilityMode).useTexture {
            if (!linearMag || Superintendent.compatibilityMode) {
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)
            }
        }
    }

}