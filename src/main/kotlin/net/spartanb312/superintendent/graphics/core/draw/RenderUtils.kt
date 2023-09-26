package net.spartanb312.superintendent.graphics.core.draw

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.spartanb312.superintendent.graphics.core.GLStatesManager
import net.spartanb312.superintendent.graphics.core.draw.arrayed.VertexArray2DRenderer
import net.spartanb312.superintendent.graphics.core.draw.legacy.Legacy2DRenderer
import net.spartanb312.superintendent.utils.awt.ColorRGB
import net.spartanb312.superintendent.utils.mapArray
import net.spartanb312.superintendent.utils.math.MathUtils
import net.spartanb312.superintendent.utils.math.vector.Vec2d
import net.spartanb312.superintendent.utils.math.vector.Vec2f
import net.spartanb312.superintendent.utils.math.vector.toVec2d
import org.lwjgl.opengl.GL11
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

object RenderUtils {

    var renderer: I2DRenderer = VertexArray2DRenderer; private set
    var minecraftMode = true; private set

    fun setRenderer(renderer: Renderer) {
        RenderUtils.renderer = when (renderer) {
            Renderer.Legacy -> Legacy2DRenderer
            Renderer.Arrayed -> VertexArray2DRenderer
        }
    }

    enum class Renderer {
        Legacy,
        Arrayed
    }

    /**
     * Point
     */
    fun drawPoint(
        x: Double,
        y: Double,
        size: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawPoint0(x, y, size, color)
    }

    fun drawPoint(
        x: Int,
        y: Int,
        size: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawPoint0(x.toDouble(), y.toDouble(), size, color)
    }

    fun drawPoint(
        x: Float,
        y: Float,
        size: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawPoint0(x.toDouble(), y.toDouble(), size, color)
    }

    /**
     * Line
     */
    fun drawLine(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
    ) = glSafe {
        renderer.drawLine0(startX, startY, endX, endY, width, color1, color2)
    }

    fun drawLine(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
    ) = glSafe {
        renderer.drawLine0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            width,
            color1,
            color2
        )
    }

    fun drawLine(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
    ) = glSafe {
        renderer.drawLine0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            width,
            color1,
            color2
        )
    }

    fun drawLine(
        start: Vec2d,
        end: Vec2d,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
    ) = glSafe {
        renderer.drawLine0(start.x, start.y, end.x, end.y, width, color1, color2)
    }

    fun drawLine(
        start: Vec2f,
        end: Vec2f,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
    ) = glSafe {
        renderer.drawLine0(
            start.x.toDouble(),
            start.y.toDouble(),
            end.x.toDouble(),
            end.y.toDouble(),
            width,
            color1,
            color2
        )
    }

    fun drawLinesStrip(
        vertexArray: Array<Vec2d>,
        width: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawLinesStrip0(vertexArray, width, color)
    }

    fun drawLinesLoop(
        vertexArray: Array<Vec2d>,
        width: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawLinesLoop0(vertexArray, width, color)
    }

    fun drawLinesStrip(
        vertexArray: Array<Vec2f>,
        width: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawLinesStrip0(vertexArray.mapArray { it.toVec2d() }, width, color)
    }

    fun drawLinesLoop(
        vertexArray: Array<Vec2f>,
        width: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawLinesLoop0(vertexArray.mapArray { it.toVec2d() }, width, color)
    }

    /**
     * Triangle
     */
    fun drawTriangle(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawTriangle0(pos1X, pos1Y, pos2X, pos2Y, pos3X, pos3Y, color)
    }

    fun drawTriangle(
        pos1X: Int,
        pos1Y: Int,
        pos2X: Int,
        pos2Y: Int,
        pos3X: Int,
        pos3Y: Int,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawTriangle0(
            pos1X.toDouble(),
            pos1Y.toDouble(),
            pos2X.toDouble(),
            pos2Y.toDouble(),
            pos3X.toDouble(),
            pos3Y.toDouble(),
            color
        )
    }

    fun drawTriangle(
        pos1X: Float,
        pos1Y: Float,
        pos2X: Float,
        pos2Y: Float,
        pos3X: Float,
        pos3Y: Float,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawTriangle0(
            pos1X.toDouble(),
            pos1Y.toDouble(),
            pos2X.toDouble(),
            pos2Y.toDouble(),
            pos3X.toDouble(),
            pos3Y.toDouble(),
            color
        )
    }

    fun drawTriangle(
        pos1: Vec2d,
        pos2: Vec2d,
        pos3: Vec2d,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawTriangle0(
            pos1.x,
            pos1.y,
            pos2.x,
            pos2.y,
            pos3.x,
            pos3.y,
            color
        )
    }

    fun drawTriangle(
        pos1: Vec2f,
        pos2: Vec2f,
        pos3: Vec2f,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawTriangle0(
            pos1.x.toDouble(),
            pos1.y.toDouble(),
            pos2.x.toDouble(),
            pos2.y.toDouble(),
            pos3.x.toDouble(),
            pos3.y.toDouble(),
            color
        )
    }

    //Triangle outline
    fun drawTriangleOutline(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        width: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawTriangleOutline0(pos1X, pos1Y, pos2X, pos2Y, pos3X, pos3Y, width, color)
    }

    fun drawTriangleOutline(
        pos1X: Int,
        pos1Y: Int,
        pos2X: Int,
        pos2Y: Int,
        pos3X: Int,
        pos3Y: Int,
        width: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawTriangleOutline0(
            pos1X.toDouble(),
            pos1Y.toDouble(),
            pos2X.toDouble(),
            pos2Y.toDouble(),
            pos3X.toDouble(),
            pos3Y.toDouble(),
            width,
            color
        )
    }

    fun drawTriangleOutline(
        pos1X: Float,
        pos1Y: Float,
        pos2X: Float,
        pos2Y: Float,
        pos3X: Float,
        pos3Y: Float,
        width: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawTriangleOutline0(
            pos1X.toDouble(),
            pos1Y.toDouble(),
            pos2X.toDouble(),
            pos2Y.toDouble(),
            pos3X.toDouble(),
            pos3Y.toDouble(),
            width,
            color
        )
    }

    fun drawTriangleOutline(
        pos1: Vec2d,
        pos2: Vec2d,
        pos3: Vec2d,
        width: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawTriangleOutline0(
            pos1.x,
            pos1.y,
            pos2.x,
            pos2.y,
            pos3.x,
            pos3.y,
            width,
            color
        )
    }

    fun drawTriangleOutline(
        pos1: Vec2f,
        pos2: Vec2f,
        pos3: Vec2f,
        width: Float = 1F,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawTriangleOutline0(
            pos1.x.toDouble(),
            pos1.y.toDouble(),
            pos2.x.toDouble(),
            pos2.y.toDouble(),
            pos3.x.toDouble(),
            pos3.y.toDouble(),
            width,
            color
        )
    }

    /**
     * Rectangle
     */
    fun drawRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRect0(startX, startY, endX, endY, color)
    }

    fun drawRect(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRect0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), color)
    }

    fun drawRect(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRect0(startX.toDouble(), startY.toDouble(), endX.toDouble(), endY.toDouble(), color)
    }

    fun drawRect(
        start: Vec2d,
        end: Vec2d,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRect0(start.x, start.y, end.x, end.y, color)
    }

    fun drawRect(
        start: Vec2f,
        end: Vec2f,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRect0(start.x.toDouble(), start.y.toDouble(), end.x.toDouble(), end.y.toDouble(), color)
    }

    //Gradient Rectangle
// color2  color1
// color3  color4
    fun drawGradientRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(startX, startY, endX, endY, color1, color2, color3, color4)
    }

    fun drawGradientRect(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            color1,
            color2,
            color3,
            color4
        )
    }

    fun drawGradientRect(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            color1,
            color2,
            color3,
            color4
        )
    }

    fun drawGradientRect(
        start: Vec2d,
        end: Vec2d,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(start.x, start.y, end.x, end.y, color1, color2, color3, color4)
    }

    fun drawGradientRect(
        start: Vec2f,
        end: Vec2f,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            start.x.toDouble(),
            start.y.toDouble(),
            end.x.toDouble(),
            end.y.toDouble(),
            color1,
            color2,
            color3,
            color4
        )
    }

    //Horizontal
    fun drawHorizontalRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(startX, startY, endX, endY, endColorRGB, startColor, startColor, endColorRGB)
    }

    fun drawHorizontalRect(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            endColorRGB,
            startColor,
            startColor,
            endColorRGB
        )
    }

    fun drawHorizontalRect(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            endColorRGB,
            startColor,
            startColor,
            endColorRGB
        )
    }

    fun drawHorizontalRect(
        start: Vec2d,
        end: Vec2d,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            start.x,
            start.y,
            end.x,
            end.y,
            endColorRGB,
            startColor,
            startColor,
            endColorRGB
        )
    }

    fun drawHorizontalRect(
        start: Vec2f,
        end: Vec2f,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            start.x.toDouble(),
            start.y.toDouble(),
            end.x.toDouble(),
            end.y.toDouble(),
            endColorRGB,
            startColor,
            startColor,
            endColorRGB
        )
    }

    //Vertical
    fun drawVerticalRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(startX, startY, endX, endY, startColor, startColor, endColorRGB, endColorRGB)
    }

    fun drawVerticalRect(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            startColor,
            startColor,
            endColorRGB,
            endColorRGB
        )
    }

    fun drawVerticalRect(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            startColor,
            startColor,
            endColorRGB,
            endColorRGB
        )
    }

    fun drawVerticalRect(
        start: Vec2d,
        end: Vec2d,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            start.x,
            start.y,
            end.x,
            end.y,
            startColor,
            startColor,
            endColorRGB,
            endColorRGB
        )
    }

    fun drawVerticalRect(
        start: Vec2f,
        end: Vec2f,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawGradientRect0(
            start.x.toDouble(),
            start.y.toDouble(),
            end.x.toDouble(),
            end.y.toDouble(),
            startColor,
            startColor,
            endColorRGB,
            endColorRGB
        )
    }

    //Rectangle outline
    fun drawRectOutline(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1,
    ) = glSafe {
        renderer.drawRectOutline0(startX, startY, endX, endY, width, color1, color2, color3, color4)
    }

    fun drawRectOutline(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1,
    ) = glSafe {
        renderer.drawRectOutline0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            width,
            color1,
            color2,
            color3,
            color4
        )
    }

    fun drawRectOutline(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1,
    ) = glSafe {
        renderer.drawRectOutline0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            width,
            color1,
            color2,
            color3,
            color4
        )
    }

    fun drawRectOutline(
        start: Vec2d,
        end: Vec2d,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1,
    ) = glSafe {
        renderer.drawRectOutline0(start.x, start.y, end.x, end.y, width, color1, color2, color3, color4)
    }

    fun drawRectOutline(
        start: Vec2f,
        end: Vec2f,
        width: Float = 1F,
        color1: ColorRGB,
        color2: ColorRGB = color1,
        color3: ColorRGB = color1,
        color4: ColorRGB = color1,
    ) = glSafe {
        renderer.drawRectOutline0(
            start.x.toDouble(),
            start.y.toDouble(),
            end.x.toDouble(),
            end.y.toDouble(),
            width,
            color1,
            color2,
            color3,
            color4
        )
    }

    //Horizontal
    fun drawHorizontalRectOutline(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawRectOutline0(startX, startY, endX, endY, width, endColorRGB, startColor, startColor, endColorRGB)
    }

    fun drawHorizontalRectOutline(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawRectOutline0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            width,
            endColorRGB,
            startColor,
            startColor,
            endColorRGB
        )
    }

    fun drawHorizontalRectOutline(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawRectOutline0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            width,
            endColorRGB,
            startColor,
            startColor,
            endColorRGB
        )
    }

    fun drawHorizontalRectOutline(
        start: Vec2d,
        end: Vec2d,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawRectOutline0(
            start.x,
            start.y,
            end.x,
            end.y,
            width,
            endColorRGB,
            startColor,
            startColor,
            endColorRGB
        )
    }

    fun drawHorizontalRectOutline(
        start: Vec2f,
        end: Vec2f,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawRectOutline0(
            start.x.toDouble(),
            start.y.toDouble(),
            end.x.toDouble(),
            end.y.toDouble(),
            width,
            endColorRGB,
            startColor,
            startColor,
            endColorRGB
        )
    }

    //Vertical
    fun drawVerticalRectOutline(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawRectOutline0(startX, startY, endX, endY, width, startColor, startColor, endColorRGB, endColorRGB)
    }

    fun drawVerticalRectOutline(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawRectOutline0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            width,
            startColor,
            startColor,
            endColorRGB,
            endColorRGB
        )
    }

    fun drawVerticalRectOutline(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawRectOutline0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            width,
            startColor,
            startColor,
            endColorRGB,
            endColorRGB
        )
    }

    fun drawVerticalRectOutline(
        start: Vec2d,
        end: Vec2d,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawRectOutline0(
            start.x,
            start.y,
            end.x,
            end.y,
            width,
            startColor,
            startColor,
            endColorRGB,
            endColorRGB
        )
    }

    fun drawVerticalRectOutline(
        start: Vec2f,
        end: Vec2f,
        width: Float = 1F,
        startColor: ColorRGB,
        endColorRGB: ColorRGB,
    ) = glSafe {
        renderer.drawRectOutline0(
            start.x.toDouble(),
            start.y.toDouble(),
            end.x.toDouble(),
            end.y.toDouble(),
            width,
            startColor,
            startColor,
            endColorRGB,
            endColorRGB
        )
    }

    /**
     * RoundedRect
     */
    fun drawRoundedRect(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRoundedRect0(startX, startY, endX, endY, radius, segments, color)
    }

    fun drawRoundedRect(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRoundedRect0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            radius,
            segments,
            color
        )
    }

    fun drawRoundedRect(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRoundedRect0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            radius,
            segments,
            color
        )
    }

    fun drawRoundedRect(
        start: Vec2d,
        end: Vec2d,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRoundedRect0(
            start.x,
            start.y,
            end.x,
            end.y,
            radius,
            segments,
            color
        )
    }

    fun drawRoundedRect(
        start: Vec2f,
        end: Vec2f,
        radius: Float,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRoundedRect0(
            start.x.toDouble(),
            start.y.toDouble(),
            end.x.toDouble(),
            end.y.toDouble(),
            radius,
            segments,
            color
        )
    }

    fun drawRoundedRectOutline(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRoundedRectOutline0(startX, startY, endX, endY, radius, width, segments, color)
    }

    fun drawRoundedRectOutline(
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRoundedRectOutline0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            radius,
            width,
            segments,
            color
        )
    }

    fun drawRoundedRectOutline(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRoundedRectOutline0(
            startX.toDouble(),
            startY.toDouble(),
            endX.toDouble(),
            endY.toDouble(),
            radius,
            width,
            segments,
            color
        )
    }

    fun drawRoundedRectOutline(
        start: Vec2d,
        end: Vec2d,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRoundedRectOutline0(
            start.x,
            start.y,
            end.x,
            end.y,
            radius,
            width,
            segments,
            color
        )
    }

    fun drawRoundedRectOutline(
        start: Vec2f,
        end: Vec2f,
        radius: Float,
        width: Float = 1F,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawRoundedRectOutline0(
            start.x.toDouble(),
            start.y.toDouble(),
            end.x.toDouble(),
            end.y.toDouble(),
            radius,
            width,
            segments,
            color
        )
    }

    fun drawArc(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawArc0(centerX, centerY, radius, angleRange, segments, color)
    }

    fun drawArc(
        centerX: Int,
        centerY: Int,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawArc0(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments, color)
    }

    fun drawArc(
        centerX: Float,
        centerY: Float,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawArc0(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments, color)
    }

    fun drawArc(
        center: Vec2d,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawArc0(center.x, center.y, radius, angleRange, segments, color)
    }

    fun drawArc(
        center: Vec2f,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawArc0(center.x.toDouble(), center.y.toDouble(), radius, angleRange, segments, color)
    }

    fun drawTriangleFan(
        centerX: Double,
        centerY: Double,
        vertices: Array<Vec2d>,
        centerColor: ColorRGB,
        color: ColorRGB = centerColor
    ) = glSafe {
        renderer.drawTriangleFan0(centerX, centerY, vertices, centerColor, color)
    }

    fun drawTriangleFan(
        centerX: Float,
        centerY: Float,
        vertices: Array<Vec2d>,
        centerColor: ColorRGB,
        color: ColorRGB = centerColor
    ) = glSafe {
        renderer.drawTriangleFan0(centerX.toDouble(), centerY.toDouble(), vertices, centerColor, color)
    }

    fun drawArcOutline(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawLinesStrip0(
            getArcVertices(centerX, centerY, radius, angleRange, segments),
            lineWidth,
            color
        )
    }

    fun drawArcOutline(
        centerX: Int,
        centerY: Int,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawLinesStrip0(
            getArcVertices(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments),
            lineWidth,
            color
        )
    }

    fun drawArcOutline(
        centerX: Float,
        centerY: Float,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawLinesStrip0(
            getArcVertices(centerX.toDouble(), centerY.toDouble(), radius, angleRange, segments),
            lineWidth,
            color
        )
    }

    fun drawArcOutline(
        center: Vec2d,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawLinesStrip0(
            getArcVertices(center.x, center.y, radius, angleRange, segments),
            lineWidth,
            color
        )
    }

    fun drawArcOutline(
        center: Vec2f,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int = 0,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawLinesStrip0(
            getArcVertices(center.x.toDouble(), center.y.toDouble(), radius, angleRange, segments),
            lineWidth,
            color
        )
    }

    fun drawArcOutline(
        vertices: Array<Vec2d>,
        lineWidth: Float = 1f,
        color: ColorRGB,
    ) = glSafe {
        renderer.drawLinesStrip0(
            vertices,
            lineWidth,
            color
        )
    }

    @JvmStatic
    fun getArcVertices(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float> = Pair(0f, 360f),
        segments: Int = 0,
    ): Array<Vec2d> {
        val range = max(angleRange.first, angleRange.second) - min(angleRange.first, angleRange.second)
        val seg = MathUtils.calcSegments(segments, radius.toDouble(), range)
        val segAngle = (range.toDouble() / seg.toDouble())

        return Array(seg + 1) {
            val angle = Math.toRadians(it * segAngle + angleRange.first.toDouble())
            val unRounded = Vec2d(sin(angle), -cos(angle)).times(radius.toDouble()).plus(Vec2d(centerX, centerY))
            Vec2d(MathUtils.round(unRounded.x, 8), MathUtils.round(unRounded.y, 8))
        }
    }

    fun prepareGL() {
        GLStatesManager.texture2d = false
        GLStatesManager.alpha = true
        GLStatesManager.shadeModel = GL11.GL_SMOOTH
        GLStatesManager.blend = true
    }

    fun releaseGL() {
        GLStatesManager.texture2d = true
    }

    inline fun glSafe(block: () -> Unit) {
        if (minecraftMode) {
            prepareGL()
            block()
            releaseGL()
        } else block()
    }

    inline fun scissor(x: Int, y: Int, x1: Int, y1: Int, sr: ScaledResolution, block: () -> Unit) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST)
        GL11.glScissor(
            (x * sr.scaleFactor),
            (Minecraft.getMinecraft().displayHeight - y1 * sr.scaleFactor),
            ((x1 - x) * sr.scaleFactor),
            ((y1 - y) * sr.scaleFactor)
        )
        block()
        GL11.glDisable(GL11.GL_SCISSOR_TEST)
    }

}