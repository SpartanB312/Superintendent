package net.spartanb312.superintendent.event.events.render

import net.spartanb312.superintendent.event.Event
import net.spartanb312.superintendent.event.EventBus
import net.spartanb312.superintendent.event.EventPosting

class Render3DEvent(val pass: Int, val partialTicks: Float) : Event, EventPosting by Companion {
    companion object : EventBus()
}