package net.spartanb312.superintendent.utils

import org.lwjgl.opengl.GL11
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

fun InputStream.forEachLine(charset: Charset = Charsets.UTF_8, action: (line: String) -> Unit) {
    BufferedReader(InputStreamReader(this, charset)).forEachLine(action)
}

fun InputStream.readLines(charset: Charset = Charsets.UTF_8): List<String> {
    val result = ArrayList<String>()
    forEachLine(charset) { result.add(it); }
    return result
}

inline fun scale(x: Float = 1f, y: Float = 1f, z: Float = 1f, block: () -> Unit) {
    GL11.glScalef(x, y, z)
    block()
    GL11.glScalef(1f / x, 1f / y, 1f / z)
}

inline fun translate(x: Float = 0f, y: Float = 0f, z: Float = 0f, block: () -> Unit) {
    GL11.glTranslatef(x, y, z)
    block()
    GL11.glTranslatef(-x, -y, -z)
}
