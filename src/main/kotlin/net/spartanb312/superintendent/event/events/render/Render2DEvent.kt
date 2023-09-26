package net.spartanb312.superintendent.event.events.render

import net.minecraft.client.gui.ScaledResolution
import net.spartanb312.superintendent.event.Event
import net.spartanb312.superintendent.event.EventBus
import net.spartanb312.superintendent.event.EventPosting

class Render2DEvent(val res: ScaledResolution, val partialTicks: Float) : Event, EventPosting by Companion {
    companion object : EventBus()
}