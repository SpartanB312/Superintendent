package net.spartanb312.superintendent.features.rendering

import net.spartanb312.superintendent.event.events.TickEvent
import net.spartanb312.superintendent.event.events.render.Render2DEvent
import net.spartanb312.superintendent.event.events.render.Render3DEvent
import net.spartanb312.superintendent.event.listener
import net.spartanb312.superintendent.features.Component
import net.spartanb312.superintendent.features.rendering.hud.EnergyShield
import net.spartanb312.superintendent.features.rendering.hud.ItemsHUD
import net.spartanb312.superintendent.features.rendering.hud.Notification
import net.spartanb312.superintendent.features.rendering.hud.Radar
import net.spartanb312.superintendent.utils.config.loadConfig
import net.spartanb312.superintendent.utils.config.saveConfig

object RenderManager : Component("Rendering") {

    val renderModules = mutableListOf<RenderModule>()
    val render2DModules = mutableListOf<RenderModule.HUD>()
    val render3DModules = mutableListOf<RenderModule.Rendering>()

    init {
        listener<TickEvent.Post> {
            renderModules.forEach { it.enabled = true }
            renderModules.forEach { if (it.enabled) it.onTick() }
        }
        listener<Render2DEvent> { event ->
            render2DModules.forEach {
                if (it.enabled) it.render2D(event)
            }
        }
        listener<Render3DEvent> { event ->
            render3DModules.forEach {
                if (it.enabled) it.render3D(event)
            }
        }
    }

    fun init() {
        initModule(EnergyShield)
        initModule(ItemsHUD)
        initModule(Notification)
        initModule(Radar)
        render2DModules.solveIndex()
        RenderManager.subscribe()
    }

    fun initModule(module: RenderModule) {
        when (module) {
            is RenderModule.HUD -> render2DModules.add(module)
            is RenderModule.Rendering -> render3DModules.add(module)
        }
        renderModules.add(module)
    }

    fun MutableList<RenderModule.HUD>.solveIndex() {
        sortedBy { it.renderIndex }.forEachIndexed { index, renderModule ->
            renderModule.renderIndex = index
        }
    }

    override fun loadConfig(path: String) {
        renderModules.forEach {
            it.loadConfig("${path}${it.name}.json")
        }
    }

    override fun saveConfig(path: String) {
        renderModules.forEach {
            it.saveConfig("${path}${it.name}.json")
        }
    }

}