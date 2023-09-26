package net.spartanb312.superintendent.graphics.core.draw.legacy

import net.spartanb312.superintendent.graphics.core.GLStatesManager
import net.spartanb312.superintendent.graphics.core.draw.I2DRenderer
import net.spartanb312.superintendent.graphics.core.draw.RenderUtils.getArcVertices
import net.spartanb312.superintendent.graphics.core.vertex.LegacyVertexPutter.begin
import net.spartanb312.superintendent.utils.awt.ColorRGB
import net.spartanb312.superintendent.utils.math.vector.Vec2d
import org.lwjgl.opengl.GL11.*

object Legacy2DRenderer : I2DRenderer {

    override fun drawPoint0(x: Double, y: Double, size: Float, color: ColorRGB): I2DRenderer {
        GLStatesManager.pointSmooth = true
        glPointSize(size)
        GL_POINTS.begin {
            put(x, y, color)
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
        GL_LINES.begin {
            put(startX, startY, color1)
            put(endX, endY, color2)
        }
        GLStatesManager.lineSmooth = false
        return this
    }

    override fun drawLinesStrip0(vertexArray: Array<Vec2d>, width: Float, color: ColorRGB): I2DRenderer {
        GLStatesManager.lineSmooth = true
        glLineWidth(width)
        GL_LINE_STRIP.begin {
            for (v in vertexArray) {
                put(v.x, v.y, color)
            }
        }
        GLStatesManager.lineSmooth = false
        return this
    }

    override fun drawLinesLoop0(vertexArray: Array<Vec2d>, width: Float, color: ColorRGB): I2DRenderer {
        GLStatesManager.lineSmooth = true
        glLineWidth(width)
        GL_LINE_LOOP.begin {
            for (v in vertexArray) {
                put(v.x, v.y, color)
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
        GL_TRIANGLES.begin {
            put(pos1X, pos1Y, color)
            put(pos2X, pos2Y, color)
            put(pos3X, pos3Y, color)
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
        GL_LINE_STRIP.begin {
            put(pos1X, pos1Y, color)
            put(pos2X, pos2Y, color)
            put(pos3X, pos3Y, color)
        }
        GLStatesManager.lineSmooth = false
        return this
    }

    override fun drawRect0(startX: Double, startY: Double, endX: Double, endY: Double, color: ColorRGB): I2DRenderer {
        GL_TRIANGLE_STRIP.begin {
            put(endX, startY, color)
            put(startX, startY, color)
            put(endX, endY, color)
            put(startX, endY, color)
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
        GL_QUADS.begin {
            put(endX, startY, color1)
            put(startX, startY, color2)
            put(startX, endY, color3)
            put(endX, endY, color4)
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
        GL_LINE_LOOP.begin {
            put(endX, startY, color1)
            put(startX, startY, color2)
            put(startX, endY, color3)
            put(endX, endY, color4)
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
        GL_TRIANGLE_FAN.begin {
            put(centerX, centerY, color)
            for (v in arcVertices) {
                put(v.x, v.y, color)
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
        GL_TRIANGLE_FAN.begin {
            put(centerX, centerY, color)
            for (v in vertices) {
                put(v.x, v.y, color)
            }
        }
        return this
    }

}
