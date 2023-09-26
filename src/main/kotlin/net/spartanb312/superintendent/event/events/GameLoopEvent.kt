package net.spartanb312.superintendent.event.events

import net.spartanb312.superintendent.event.Event
import net.spartanb312.superintendent.event.EventBus
import net.spartanb312.superintendent.event.EventPosting

sealed class GameLoopEvent : Event {
    object Pre : GameLoopEvent(), EventPosting by EventBus()
    object Post : GameLoopEvent(), EventPosting by EventBus()
}