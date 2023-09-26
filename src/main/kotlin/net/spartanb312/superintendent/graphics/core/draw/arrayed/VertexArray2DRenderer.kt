package net.spartanb312.superintendent.graphics.core.draw.arrayed

import net.spartanb312.superintendent.graphics.core.GLStatesManager
import net.spartanb312.superintendent.graphics.core.draw.I2DRenderer
import net.spartanb312.superintendent.graphics.core.draw.RenderUtils.getArcVertices
import net.spartanb312.superintendent.graphics.core.vertex.ArrayedVertexBuffer.buffer
import net.spartanb312.superintendent.graphics.core.vertex.VertexFormat
import net.spartanb312.superintendent.utils.awt.ColorRGB
import net.spartanb312.superintendent.utils.math.vector.Vec2d
import org.lwjgl.opengl.GL11.*

object VertexArray2DRenderer : I2DRenderer {

    override fun drawPoint0(x: Double, y: Double, size: Float, color: ColorRGB): I2DRenderer {
        GLStatesManager.pointSmooth = true
        glPointSize(size)
        GL_POINTS.buffer(VertexFormat.Pos2dColor, 1) {
            v2dc(x, y, color)
        }
        GLStatesManager.pointSmooth = false
        return this
    }

    override fun drawLine0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float,
        color1: ColorRGB,
        color2: ColorRGB,
    ): I2DRenderer {
        GLStatesManager.lineSmooth = true
        glLineWidth(width)
        GL_LINES.buffer(VertexFormat.Pos2dColor, 2) {
            v2dc(startX, startY, color1)
            v2dc(endX, endY, color2)
        }
        GLStatesManager.lineSmooth = false
        return this
    }

    override fun drawLinesStrip0(vertexArray: Array<Vec2d>, width: Float, color: ColorRGB): I2DRenderer {
        GLStatesManager.lineSmooth = true
        glLineWidth(width)
        GL_LINE_STRIP.buffer(VertexFormat.Pos2dColor, vertexArray.size) {
            for (v in vertexArray) {
                v2dc(v.x, v.y, color)
            }
        }
        GLStatesManager.lineSmooth = false
        return this
    }

    override fun drawLinesLoop0(vertexArray: Array<Vec2d>, width: Float, color: ColorRGB): I2DRenderer {
        GLStatesManager.lineSmooth = true
        glLineWidth(width)
        GL_LINE_LOOP.buffer(VertexFormat.Pos2dColor, vertexArray.size) {
            for (v in vertexArray) {
                v2dc(v.x, v.y, color)
            }
        }
        GLStatesManager.lineSmooth = false
        return this
    }

    override fun drawTriangle0(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        color: ColorRGB,
    ): I2DRenderer {
        GL_TRIANGLES.buffer(VertexFormat.Pos2dColor, 3) {
            v2dc(pos1X, pos1Y, color)
            v2dc(pos2X, pos2Y, color)
            v2dc(pos3X, pos3Y, color)
        }
        return this
    }

    override fun drawTriangleOutline0(
        pos1X: Double,
        pos1Y: Double,
        pos2X: Double,
        pos2Y: Double,
        pos3X: Double,
        pos3Y: Double,
        width: Float,
        color: ColorRGB,
    ): I2DRenderer {
        GLStatesManager.lineSmooth = true
        glLineWidth(width)
        GL_LINE_STRIP.buffer(VertexFormat.Pos2dColor, 3) {
            v2dc(pos1X, pos1Y, color)
            v2dc(pos2X, pos2Y, color)
            v2dc(pos3X, pos3Y, color)
        }
        GLStatesManager.lineSmooth = false
        return this
    }

    override fun drawRect0(startX: Double, startY: Double, endX: Double, endY: Double, color: ColorRGB): I2DRenderer {
        GL_TRIANGLE_STRIP.buffer(VertexFormat.Pos2dColor, 4) {
            v2dc(endX, startY, color)
            v2dc(startX, startY, color)
            v2dc(endX, endY, color)
            v2dc(startX, endY, color)
        }
        return this
    }

    override fun drawGradientRect0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ): I2DRenderer {
        GL_QUADS.buffer(VertexFormat.Pos2dColor, 4) {
            v2dc(endX, startY, color1)
            v2dc(startX, startY, color2)
            v2dc(startX, endY, color3)
            v2dc(endX, endY, color4)
        }
        return this
    }

    override fun drawRectOutline0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        width: Float,
        color1: ColorRGB,
        color2: ColorRGB,
        color3: ColorRGB,
        color4: ColorRGB,
    ): I2DRenderer {
        GLStatesManager.lineSmooth = true
        glLineWidth(width)
        GL_LINE_LOOP.buffer(VertexFormat.Pos2dColor, 4) {
            v2dc(endX, startY, color1)
            v2dc(startX, startY, color2)
            v2dc(startX, endY, color3)
            v2dc(endX, endY, color4)
        }
        GLStatesManager.lineSmooth = false
        return this
    }

    override fun drawRoundedRect0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        segments: Int,
        color: ColorRGB,
    ): I2DRenderer {
        return this
    }

    override fun drawRoundedRectOutline0(
        startX: Double,
        startY: Double,
        endX: Double,
        endY: Double,
        radius: Float,
        width: Float,
        segments: Int,
        color: ColorRGB,
    ): I2DRenderer {
        return this
    }

    override fun drawArc0(
        centerX: Double,
        centerY: Double,
        radius: Float,
        angleRange: Pair<Float, Float>,
        segments: Int,
        color: ColorRGB,
    ): I2DRenderer {
        val arcVertices = getArcVertices(centerX, centerY, radius, angleRange, segments).reversed()
        GL_TRIANGLE_FAN.buffer(VertexFormat.Pos2dColor, arcVertices.size + 1) {
            v2dc(centerX, centerY, color)
            for (v in arcVertices) {
                v2dc(v.x, v.y, color)
            }
        }
        return this
    }

    override fun drawTriangleFan0(
        centerX: Double,
        centerY: Double,
        vertices: Array<Vec2d>,
        centerColor: ColorRGB,
        color: ColorRGB
    ): I2DRenderer {
        GL_TRIANGLE_FAN.buffer(VertexFormat.Pos2dColor, vertices.size + 1) {
            v2dc(centerX, centerY, centerColor)
            for (v in vertices) {
                v2dc(v.x, v.y, color)
            }
        }
        return this
    }

}