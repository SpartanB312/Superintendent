package net.spartanb312.superintendent.utils.timming

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LateUpdateValue<T>(val updateTime: Int, private val initializer: () -> T) : ReadOnlyProperty<Any?, T> {

    private val updateTimer = TickTimer()
    private var currentValue: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (updateTimer.tick(updateTime) || currentValue == null) {
            updateTimer.reset()
            currentValue = initializer.invoke()
        }
        return currentValue!!
    }

}