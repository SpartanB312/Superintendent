package net.spartanb312.superintendent.utils

import net.minecraft.client.Minecraft

typealias FloatRange = ClosedFloatingPointRange<Float>
typealias DoubleRange = ClosedFloatingPointRange<Double>

val mc get() = Minecraft.getMinecraft()