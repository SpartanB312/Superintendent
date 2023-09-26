package net.spartanb312.superintendent.graphics.font

import net.minecraft.client.renderer.GlStateManager
import net.spartanb312.superintendent.Superintendent
import net.spartanb312.superintendent.graphics.core.GLStatesManager
import net.spartanb312.superintendent.graphics.core.MatrixUtils.popMatrix
import net.spartanb312.superintendent.graphics.core.MatrixUtils.pushMatrix
import net.spartanb312.superintendent.graphics.core.vertex.ArrayedVertexBuffer.buffer
import net.spartanb312.superintendent.graphics.core.vertex.VertexFormat
import net.spartanb312.superintendent.graphics.texture.Texture
import net.spartanb312.superintendent.utils.awt.ColorRGB
import net.spartanb312.superintendent.utils.math.ceilToInt
import net.spartanb312.superintendent.utils.math.floorToInt
import org.lwjgl.opengl.GL11.*
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class UnicodeFontRenderer(
    private val font: Font,
    size: Int,
    private val antiAlias: Boolean = true,
    private val fractionalMetrics: Boolean = false,
    private val imgSize: Int = 512,
    private val chunkSize: Int = 64,
    private val linearMag: Boolean = true,
    private val useMipmap: Boolean = true,
    override var scaleFactor: Float = 1f,
) : IFontRenderer {

    private val charDataArray = arrayOfNulls<CharData>(65536)
    private val scaledOffset = (4 * size / 25f).toInt()

    private val textures = arrayOfNulls<Texture>(65536 / chunkSize)
    private val badChunks = Array(65536 / chunkSize) { 0 }

    override var height = 0

    fun setScale(scale: Float): UnicodeFontRenderer {
        this.scaleFactor = scale
        return this
    }

    init {
        // Init ASCII chunks
        for (chunk in 0 until (256 / chunkSize)) {
            initChunk(chunk)
        }
    }

    private fun initChunk(chunk: Int): Texture {
        val img = BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB)

        (img.createGraphics()).let {
            it.font = this.font
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
            for (index in 0 until chunkSize) {
                val dimension = metrics.getStringBounds((chunk * chunkSize + index).toChar().toString(), it)
                CharData(
                    dimension.bounds.width,
                    dimension.bounds.height
                ).also { charData ->
                    val imgWidth = charData.width + scaledOffset * 2
                    if (charData.height > charHeight) {
                        charHeight = charData.height
                        if (charHeight > height) height = charHeight // Set the max height as Font height
                    }
                    if (posX + imgWidth > imgSize) {
                        posX = 0
                        posY += charHeight
                        charHeight = 0
                    }
                    charData.u = (posX + scaledOffset) / imgSize.toFloat()
                    charData.v = posY / imgSize.toFloat()
                    charData.u1 = (posX + scaledOffset + charData.width) / imgSize.toFloat()
                    charData.v1 = (posY + charData.height) / imgSize.toFloat()
                    charDataArray[chunk * chunkSize + index] = charData
                    it.drawString(
                        (chunk * chunkSize + index).toChar().toString(),
                        posX + scaledOffset,
                        posY + metrics.ascent
                    )
                    posX += imgWidth
                }
            }
        }
        val texture = Texture(img, GL_RGBA, useMipmap && !Superintendent.compatibilityMode).useTexture {
            if (!linearMag || Superintendent.compatibilityMode) {
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
            }
        }
        textures[chunk] = texture
        return texture
    }

    inner class CharData(
        val width: Int,
        val height: Int,
    ) {
        var u = 0f
        var v = 0f
        var u1 = 0f
        var v1 = 1f
    }

    override fun getHeight(scale: Float): Float = height * scale * this.scaleFactor

    override fun getWidth(text: String, scale: Float): Float {
        return getWidth0(text) * scale * this.scaleFactor
    }

    override fun getWidth(char: Char, scale: Float): Float {
        return (charDataArray.getOrNull(char.code)?.width ?: 0) * scale * this.scaleFactor
    }

    override fun getWidth0(text: String): Int {
        var sum = 0
        var shouldSkip = false
        for (index in text.indices) {
            if (shouldSkip) {
                shouldSkip = false
                continue
            }
            val char = text[index]
            val chunk = char.code / chunkSize
            if (badChunks[chunk] == 1) continue
            if (textures.getOrNull(chunk) == null) {
                val newTexture = try {
                    initChunk(chunk)
                } catch (ignore: Exception) {
                    badChunks[chunk] = 1
                    null
                }
                textures[chunk] = newTexture
            }
            val delta = charDataArray.getOrNull(char.code)?.width ?: 0
            if (char == '§' || char == '&') {
                val next = text.getOrNull(index + 1)
                if (next != null && next in "0123456789abcdefr") {
                    shouldSkip = true
                }
                continue
            } else sum += delta
        }
        return sum
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
        shadow: Boolean,
    ) {
        val shadowColor = ColorRGB.BLACK.alpha(128)
        GLStatesManager.alpha = true
        GLStatesManager.shadeModel = GL_SMOOTH
        GLStatesManager.blend = true
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
        var startX = x
        var startY = y

        //Color
        val alpha = color0.a
        var currentColor = color0

        //Scale
        val scale = scale0 * this.scaleFactor

        //Gradient
        val width = if (gradient) {
            assert(colors.size > 1)
            getWidth0(text).toFloat() / (colors.size - 1)
        } else 0f
        if (gradient && sliceMode && colors.size < text.length) throw Exception("Slice mode enabled. colors size should >= string length")
        val missingLastColor = colors.size == text.length

        if (scale != 1f) {
            pushMatrix(GL_MODELVIEW)
            glTranslatef(x, y, 0f)
            glScalef(scale, scale, 1f)
            startX = 0f
            startY = 0f
        }
        var chunk = -1
        var shouldSkip = false
        for (index in text.indices) {
            if (shouldSkip) {
                shouldSkip = false
                continue
            }
            val char = text[index]
            if (char == '\n') {
                startY += height
                startX = x
                continue
            }
            if (char == '§' || char == '&') {
                val next = text.getOrNull(index + 1)
                if (next != null) {
                    //Color
                    val newColor = next.getColor(color0)
                    if (newColor != null) {
                        if (!gradient) currentColor = newColor.alpha(alpha)
                        shouldSkip = true
                        continue
                    }
                }
            }
            val currentChunk = char.code / chunkSize
            if (currentChunk != chunk) {
                chunk = currentChunk
                val texture = textures[chunk]
                if (texture == null) {
                    // If this is a bad chunk then we skip it
                    if (badChunks[chunk] == 1) continue
                    val newTexture = try {
                        initChunk(chunk)
                    } catch (ignore: Exception) {
                        badChunks[chunk] = 1
                        null
                    }
                    if (newTexture == null) {
                        continue
                    } else {
                        textures[chunk] = newTexture
                        newTexture.bindTexture()
                    }
                } else texture.bindTexture()
            }
            val data = charDataArray.getOrNull(char.code) ?: continue

            val endX = startX + data.width
            val endY = startY + data.height

            var leftColor = currentColor
            var rightColor = if (gradient) {
                if (sliceMode) {
                    if (missingLastColor && index == text.length - 1) colors[0]
                    else colors[index + 1]
                } else {
                    val ratio = ((endX - x) / width).coerceIn(0f, colors.size - 1f)
                    colors[ratio.floorToInt()].mix(colors[ratio.ceilToInt()], ratio - ratio.floorToInt())
                }
            } else currentColor

            if (shadow) {
                leftColor = shadowColor
                rightColor = shadowColor
            }

            GL_QUADS.buffer(VertexFormat.Pos3fTex, 4) {
                //RT
                v3Tex2fC(
                    endX,
                    startY,
                    0f,
                    data.u1,
                    data.v,
                    rightColor
                )
                //LT
                v3Tex2fC(
                    startX,
                    startY,
                    0f,
                    data.u,
                    data.v,
                    leftColor
                )
                //LB
                v3Tex2fC(
                    startX,
                    endY,
                    0f,
                    data.u,
                    data.v1,
                    leftColor
                )
                //RB
                v3Tex2fC(
                    endX,
                    endY,
                    0f,
                    data.u1,
                    data.v1,
                    rightColor
                )

                startX = endX
                currentColor = rightColor
            }
        }
        GlStateManager.bindTexture(0)
        if (scale != 1f) {
            popMatrix(GL_MODELVIEW)
        }
    }

    private fun Char.getColor(prev: ColorRGB = ColorRGB.WHITE): ColorRGB? = when (this) {
        '0' -> ColorRGB(0, 0, 0)
        '1' -> ColorRGB(0, 0, 170)
        '2' -> ColorRGB(0, 170, 0)
        '3' -> ColorRGB(0, 170, 170)
        '4' -> ColorRGB(170, 0, 0)
        '5' -> ColorRGB(170, 0, 170)
        '6' -> ColorRGB(255, 170, 0)
        '7' -> ColorRGB(170, 170, 170)
        '8' -> ColorRGB(85, 85, 85)
        '9' -> ColorRGB(85, 85, 255)
        'a' -> ColorRGB(85, 255, 85)
        'b' -> ColorRGB(85, 255, 255)
        'c' -> ColorRGB(255, 85, 85)
        'd' -> ColorRGB(255, 85, 255)
        'e' -> ColorRGB(255, 255, 85)
        'f' -> ColorRGB(255, 255, 255)
        'r' -> prev
        else -> null
    }

}