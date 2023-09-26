package net.spartanb312.superintendent.utils

@Suppress("UNCHECKED_CAST")
val <T> Class<out T>.instance
    get() = this.getDeclaredField("INSTANCE")[null] as T