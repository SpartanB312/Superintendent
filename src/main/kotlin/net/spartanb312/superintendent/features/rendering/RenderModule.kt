package net.spartanb312.superintendent.features.rendering

import net.spartanb312.superintendent.event.events.render.Render2DEvent
import net.spartanb312.superintendent.event.events.render.Render3DEvent
import net.spartanb312.superintendent.features.Module
import net.spartanb312.superintendent.utils.config.setting

sealed class RenderModule(name: String) : Module(name) {

    open fun onTick() {}

    abstract class HUD(name: String) : RenderModule(name) {
        var renderIndex by setting("RenderIndex", 0, 0..1000) { false }
        abstract fun render2D(event: Render2DEvent)
    }

    abstract class Rendering(name: String) : RenderModule(name) {
        abstract fun render3D(event: Render3DEvent)
    }

}