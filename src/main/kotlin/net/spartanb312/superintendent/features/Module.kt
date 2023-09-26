package net.spartanb312.superintendent.features

import net.spartanb312.superintendent.event.ListenerOwner
import net.spartanb312.superintendent.utils.config.AbstractValue
import net.spartanb312.superintendent.utils.config.Configurable
import net.spartanb312.superintendent.utils.config.setting

open class Module(final override val name: String) : ListenerOwner(), Configurable {
    override val values = mutableListOf<AbstractValue<*>>()
    var enabled by setting("Enabled", false) { false }
}