package net.spartanb312.superintendent.features

import net.spartanb312.superintendent.event.ListenerOwner
import net.spartanb312.superintendent.utils.config.AbstractValue
import net.spartanb312.superintendent.utils.config.Configurable
import net.spartanb312.superintendent.utils.config.setting

abstract class Component(componentName: String) : ListenerOwner(), Configurable {
    final override val name = componentName
    final override val values = mutableListOf<AbstractValue<*>>()
    var enabled by setting("Enabled", true)
    abstract fun loadConfig(path: String)
    abstract fun saveConfig(path: String)
}