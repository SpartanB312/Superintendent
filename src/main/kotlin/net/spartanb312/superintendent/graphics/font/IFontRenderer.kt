package net.spartanb312.superintendent.graphics.font

import net.spartanb312.superintendent.utils.ResourceHelper
import net.spartanb312.superintendent.utils.awt.ColorHSB
import net.spartanb312.superintendent.utils.awt.ColorRGB
import java.awt.Font

fun LocalUnicodeFontRenderer(
    name: String,
    size: Float,
    style: Int = Font.PLAIN,
    antiAlias: Boolean = true,
    fractionalMetrics: Boolean = false,
    imgSize: Int = 512,
    chunkSize: Int = 64,
    linearMag: Boolean = false,
    useMipmap: Boolean = true,
    scaleFactor: Float = 1f,
): UnicodeFontRenderer = UnicodeFontRenderer(
    Font(name, style, size.toInt()),
    size.toInt(), antiAlias, fractionalMetrics, imgSize, chunkSize, linearMag, useMipmap, scaleFactor
)

fun UnicodeFontRenderer(
    path: String,
    size: Float,
    type: Int = Font.TRUETYPE_FONT,
    style: Int = Font.PLAIN,
    antiAlias: Boolean = true,
    fractionalMetrics: Boolean = false,
    imgSize: Int = 512,
    chunkSize: Int = 64,
    linearMag: Boolean = false,
    useMipmap: Boolean = true,
    scaleFactor: Float = 1f,
): UnicodeFontRenderer = UnicodeFontRenderer(
    Font.createFont(type, ResourceHelper.getResourceStream(path)!!)!!
        .deriveFont(size)
        .deriveFont(style),
    size.toInt(), antiAlias, fractionalMetrics, imgSize, chunkSize, linearMag, useMipmap, scaleFactor
)

fun LazyUnicodeFontRenderer(
    path: String,
    size: Float,
    type: Int = Font.TRUETYPE_FONT,
    style: Int = Font.PLAIN,
    antiAlias: Boolean = true,
    fractionalMetrics: Boolean = false,
    imgSize: Int = 512,
    chunkSize: Int = 64,
    linearMag: Boolean = false,
    useMipmap: Boolean = true,
    scaleFactor: Float = 1f,
): Lazy<UnicodeFontRenderer> = lazy {
    UnicodeFontRenderer(
        path,
        size,
        type,
        style,
        antiAlias,
        fractionalMetrics,
        imgSize,
        chunkSize,
        linearMag,
        useMipmap,
        scaleFactor
    )
}

@JvmField
val startTime = System.currentTimeMillis()

interface IFontRenderer {

    var height: Int
    var scaleFactor: Float

    fun getHeight(scale: Float = 1f): Float = height * scale * this.scaleFactor
    fun getWidth0(text: String): Int
    fun getWidth(text: String, scale: Float = 1f): Float
    fun getWidth(char: Char, scale: Float = 1f): Float

    fun drawString0(
        text: String,
        x: Float,
        y: Float,
        color0: ColorRGB,
        gradient: Boolean = false,
        colors: Array<ColorRGB>,
        sliceMode: Boolean = true,
        scale0: Float,
        shadow: Boolean
    )

    fun drawString0(
        text: String,
        x: Float,
        y: Float,
        color0: ColorRGB,
        scale0: Float,
        shadow: Boolean
    ) = drawString0(text, x, y, color0, false, emptyArray(), false, scale0, shadow)

    fun drawString0(
        text: String,
        x: Float,
        y: Float,
        colors: Array<ColorRGB>,
        sliceMode: Boolean = true,
        scale0: Float,
        shadow: Boolean
    ) = drawString0(text, x, y, colors[0], true, colors, sliceMode, scale0, shadow)

    fun drawGradientString(
        text: String,
        x: Float,
        y: Float,
        colors: Array<ColorRGB>,
        scale: Float = 1f,
        shadow: Boolean = false,
        sliceMode: Boolean = true
    ) {
        if (shadow) drawGradientStringWithShadow(text, x, y, colors, scale)
        drawString0(text, x, y, colors, sliceMode, scale, false)
    }

    fun drawGradientStringWithShadow(
        text: String,
        x: Float,
        y: Float,
        colors: Array<ColorRGB>,
        scale: Float = 1f,
        shadowDepth: Float = 1f,
        sliceMode: Boolean = true
    ) {
        drawString0(text, x + shadowDepth, y + shadowDepth, colors, sliceMode, scale, true)
        drawString0(text, x, y, colors, sliceMode, scale, false)
    }

    fun drawString(
        text: String,
        x: Float,
        y: Float,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
        shadow: Boolean = false,
    ) {
        if (shadow) drawStringWithShadow(text, x, y, color)
        else drawString0(text, x, y, color, scale, false)
    }

    fun drawString(
        text: String,
        x: Double,
        y: Double,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
        shadow: Boolean = false,
    ) {
        if (shadow) drawStringWithShadow(text, x, y, color)
        else drawString0(text, x.toFloat(), y.toFloat(), color, scale, false)
    }

    fun drawString(
        text: String,
        x: Int,
        y: Int,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
        shadow: Boolean = false,
    ) {
        if (shadow) drawStringWithShadow(text, x, y, color)
        else drawString0(text, x.toFloat(), y.toFloat(), color, scale, false)
    }

    fun drawStringWithShadow(
        text: String,
        x: Float,
        y: Float,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
        shadowDepth: Float = 1f,
    ) {
        drawString0(text, x + shadowDepth, y + shadowDepth, color, scale, true)
        drawString0(text, x, y, color, scale, false)
    }

    fun drawStringWithShadow(
        text: String,
        x: Double,
        y: Double,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
        shadowDepth: Float = 1f,
    ) {
        drawString0(text, (x + shadowDepth).toFloat(), (y + shadowDepth).toFloat(), color, scale, true)
        drawString0(text, x.toFloat(), y.toFloat(), color, scale, false)
    }

    fun drawStringWithShadow(
        text: String,
        x: Int,
        y: Int,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
        shadowDepth: Float = 1f,
    ) {
        drawString0(text, x + shadowDepth, y + shadowDepth, color, scale, true)
        drawString0(text, x.toFloat(), y.toFloat(), color, scale, false)
    }

    fun drawCenteredString(
        text: String,
        x: Float,
        y: Float,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
        shadow: Boolean = false,
    ) {
        val width = getWidth(text, scale)
        val height = this.height * scale * scaleFactor
        val x1 = x - width / 2f
        val y1 = y - height / 2f
        drawString(text, x1, y1, color, scale, shadow)
    }

    fun drawCenteredString(
        text: String,
        x: Double,
        y: Double,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
        shadow: Boolean = false,
    ) {
        val width = getWidth(text, scale)
        val height = this.height * scale * scaleFactor
        val x1 = x - width / 2f
        val y1 = y - height / 2f
        drawString(text, x1, y1, color, scale, shadow)
    }

    fun drawCenteredString(
        text: String,
        x: Int,
        y: Int,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
        shadow: Boolean = false,
    ) {
        val width = getWidth(text, scale)
        val height = this.height * scale * scaleFactor
        val x1 = x - width / 2f
        val y1 = y - height / 2f
        drawString(text, x1, y1, color, scale, shadow)
    }

    fun drawCenteredStringWithShadow(
        text: String,
        x: Float,
        y: Float,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
    ) {
        val width = getWidth(text, scale)
        val height = this.height * scale * scaleFactor
        val x1 = x - width / 2f
        val y1 = y - height / 2f
        drawStringWithShadow(text, x1, y1, color, scale)
    }

    fun drawCenteredStringWithShadow(
        text: String,
        x: Double,
        y: Double,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
    ) {
        val width = getWidth(text, scale)
        val height = this.height * scale * scaleFactor
        val x1 = x - width / 2f
        val y1 = y - height / 2f
        drawStringWithShadow(text, x1, y1, color, scale)
    }

    fun drawCenteredStringWithShadow(
        text: String,
        x: Int,
        y: Int,
        color: ColorRGB = ColorRGB.WHITE,
        scale: Float = 1f,
    ) {
        val width = getWidth(text, scale)
        val height = this.height * scale * scaleFactor
        val x1 = x - width / 2f
        val y1 = y - height / 2f
        drawStringWithShadow(text, x1, y1, color, scale)
    }

    fun IFontRenderer.drawColoredString(
        str: String,
        x: Float,
        y: Float,
        scale: Float = 1f,
        startX: Float = x,
        circleWidth: Float = getWidth(str, scale),
        timeGap: Int = 5000
    ): Float {
        val colors = mutableListOf<ColorRGB>()
        var currentX = x
        str.forEach {
            val offset = (currentX - startX) % circleWidth / circleWidth
            val hue = ((System.currentTimeMillis() - startTime) % timeGap) / timeGap.toFloat()
            colors.add(ColorHSB(hue - offset, 1f, 1f).toRGB())
            currentX += getWidth(it, scale)
        }
        val offset = (currentX - startX) % circleWidth / circleWidth
        val hue = ((System.currentTimeMillis() - startTime) % timeGap) / timeGap.toFloat()
        colors.add(ColorHSB(hue - offset, 1f, 1f).toRGB())
        drawGradientString(str, x, y, colors.toTypedArray(), scale, sliceMode = true)
        return getWidth(str, scale)
    }

}