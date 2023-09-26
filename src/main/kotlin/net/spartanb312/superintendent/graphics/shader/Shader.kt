package net.spartanb312.superintendent.graphics.shader

import org.lwjgl.opengl.GL20.*
import java.nio.FloatBuffer
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@Suppress("NOTHING_TO_INLINE") // Make calling faster
open class Shader(
    private val vsh: String,
    private val fsh: String,
) {

    val id = run {
        val id = glCreateProgram()
        val vshID = createShader(vsh, GL_VERTEX_SHADER)
        val fshID = createShader(fsh, GL_FRAGMENT_SHADER)

        glAttachShader(id, vshID)
        glAttachShader(id, fshID)
        glLinkProgram(id)

        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            println("ERROR: Failed to link shader " + glGetProgramInfoLog(id, 1024))
            glDeleteProgram(id)
            glDeleteShader(vshID)
            glDeleteShader(fshID)
            return@run 0
        }

        glDetachShader(id, vshID)
        glDetachShader(id, fshID)
        glDeleteShader(vshID)
        glDeleteShader(fshID)

        return@run id
    }

    val isValidShader get() = id != 0

    private fun createShader(path: String, shaderType: Int): Int =
        createShader(javaClass.getResourceAsStream(path)!!.readBytes(), shaderType)

    private fun createShader(bytes: ByteArray, shaderType: Int): Int {
        val srcString = bytes.decodeToString()
        val shaderId = glCreateShader(shaderType)

        glShaderSource(shaderId, srcString)
        glCompileShader(shaderId)

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            glDeleteShader(shaderId)
            return 0
        }
        return shaderId
    }

    fun bind(validate: Boolean) {
        if (validate && !isValidShader) throw Exception("Invalid shader(vsh:$vsh, fsh:$fsh)")
        else glUseProgram(id)
    }

    fun bind() = glUseProgram(id)

    fun unbind() = glUseProgram(0)

    fun destroy() = glDeleteProgram(id)

    @Deprecated("Get uniform location when called", ReplaceWith("glUniform1i(gl)", "org.lwjgl.opengl.GL20.glUniform1i"))
    fun setBoolean(name: String, value: Boolean) {
        setBoolean(getUniformLocation(name), value)
    }

    @Deprecated("Get uniform location when called", ReplaceWith("glUniform1i(gl)", "org.lwjgl.opengl.GL20.glUniform1i"))
    fun setInt(name: String, value: Int) {
        setInt(getUniformLocation(name), value)
    }

    @Deprecated("Get uniform location when called", ReplaceWith("glUniform1f(gl)", "org.lwjgl.opengl.GL20.glUniform1f"))
    fun setFloat(name: String, value: Float) {
        setFloat(getUniformLocation(name), value)
    }

    fun setVec4(name: String, x: Float, y: Float, z: Float, w: Float) {
        glUniform4f(getUniformLocation(name), x, y, z, w)
    }

    fun setVec3(name: String, x: Float, y: Float, z: Float) {
        glUniform3f(getUniformLocation(name), x, y, z)
    }

    fun setVec2(name: String, x: Float, y: Float) {
        glUniform2f(getUniformLocation(name), x, y)
    }

    inline fun getUniformLocation(name: String) = glGetUniformLocation(id, name)

    inline fun setBoolean(location: Int, value: Boolean) {
        glUniform1i(location, if (value) 1 else 0)
    }

    inline fun setInt(location: Int, value: Int) {
        glUniform1i(location, value)
    }

    inline fun setFloat(location: Int, value: Float) {
        glUniform1f(location, value)
    }

    inline fun uploadMatrix(location: Int, matrix: FloatBuffer) {
        glUniformMatrix4(location, false, matrix)
    }

}

@OptIn(ExperimentalContracts::class)
inline fun <T : Shader> T.use(block: T.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    bind()
    block.invoke(this)
    glUseProgram(0)
}