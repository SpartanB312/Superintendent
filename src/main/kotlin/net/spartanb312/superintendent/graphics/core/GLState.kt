package net.spartanb312.superintendent.graphics.core

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class GLState<T>(valueIn: T, private val action: (T) -> Unit) : ReadWriteProperty<Any?, T> {

    private val value = valueIn

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = action.invoke(value)

}