package net.spartanb312.superintendent.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

fun createDirectByteBuffer(capacity: Int): ByteBuffer {
    return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder())
}

fun createDirectIntBuffer(capacity: Int): IntBuffer {
    return createDirectByteBuffer(capacity shl 2).asIntBuffer()
}

fun createDirectFloatBuffer(capacity: Int): FloatBuffer {
    return createDirectByteBuffer(capacity shl 2).asFloatBuffer()
}