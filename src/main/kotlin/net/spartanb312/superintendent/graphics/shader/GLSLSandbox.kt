package net.spartanb312.superintendent.graphics.shader

import net.spartanb312.superintendent.graphics.core.vertex.LegacyVertexPutter.begin
import net.spartanb312.superintendent.utils.awt.ColorRGB
import org.lwjgl.opengl.GL11.GL_QUADS
import org.lwjgl.opengl.GL20.*

open class GLSLSandbox(fragShaderPath: String) : Shader(
    "/assets/DefaultVertex.vsh",
    fragShaderPath
) {

    val timeUniform = glGetUniformLocation(id, "time")
    val mouseUniform = glGetUniformLocation(id, "mouse")
    val resolutionUniform = glGetUniformLocation(id, "resolution")

    fun render(width: Float, height: Float, mouseX: Float, mouseY: Float, initTime: Long) {
        use {
            glUniform2f(resolutionUniform, width, height)
            glUniform2f(mouseUniform, mouseX / width, (height - 1.0f - mouseY) / height)
            glUniform1f(timeUniform, ((System.currentTimeMillis() - initTime) / 1000.0).toFloat())
            GL_QUADS.begin {
                put(-1.0, -1.0, ColorRGB.WHITE)
                put(1.0, -1.0, ColorRGB.WHITE)
                put(1.0, 1.0, ColorRGB.WHITE)
                put(-1.0, 1.0, ColorRGB.WHITE)
            }
        }
    }

}