package net.spartanb312.superintendent.event.events

import net.spartanb312.superintendent.event.Event
import net.spartanb312.superintendent.event.EventBus
import net.spartanb312.superintendent.event.EventPosting

sealed class TickEvent : Event {
    object Pre : TickEvent(), EventPosting by EventBus()
    object Post : TickEvent(), EventPosting by EventBus()
}