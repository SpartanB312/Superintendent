package net.spartanb312.superintendent.utils.config

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.spartanb312.superintendent.utils.DoubleRange
import net.spartanb312.superintendent.utils.FloatRange
import java.text.DecimalFormat
import java.util.function.BooleanSupplier
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by B312
 * 2023/8/17
 */
abstract class AbstractValue<T>(
    val name: String,
    var value: T,
    val desc: String,
    val block: BooleanSupplier
) : ReadWriteProperty<Any?, T> {
    abstract fun saveValue(jsonObject: JsonObject)
    abstract fun readValue(jsonObject: JsonObject)

    val default: T = value
    fun resetValue() {
        value = default
    }

    final override fun getValue(thisRef: Any?, property: KProperty<*>): T = value
    final override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

    val isVisible get() = block.asBoolean

    companion object {
        val ALWAYS_TRUE = BooleanSupplier { true }
    }
}

abstract class NumberValue<T>(
    name: String,
    value: T,
    val range: ClosedRange<T>,
    val step: T,
    desc: String,
    block: BooleanSupplier
) : AbstractValue<T>(name, value, desc, block) where T : Number, T : Comparable<T> {
    abstract fun setAndCorrect(value: T)
    abstract fun setByPercent(percentage: Float)
    abstract fun getAsString(): String

    companion object {
        val FLOAT_FORMAT = DecimalFormat("#.#")
        val DOUBLE_FORMAT = DecimalFormat("#.##")
    }
}

class IntValue(
    name: String,
    value: Int,
    range: IntRange,
    step: Int,
    desc: String,
    block: BooleanSupplier
) : NumberValue<Int>(name, value, range, step, desc, block) {
    override fun getAsString(): String = value.toString()
    override fun setAndCorrect(value: Int) {
        val fixedValue = value.coerceIn(range)
        val delta = fixedValue - range.start
        this.value = range.start + (delta / step.toFloat()).toInt() * step
    }

    override fun setByPercent(percentage: Float) {
        val gap = range.endInclusive - range.start
        this.value = range.start + (percentage.coerceIn(0f..1f) * gap).toInt()
    }

    override fun saveValue(jsonObject: JsonObject) = jsonObject.addProperty(name, value)
    override fun readValue(jsonObject: JsonObject) {
        value = jsonObject[name]?.asInt ?: value
    }
}

class LongValue(
    name: String,
    value: Long,
    range: LongRange,
    step: Long,
    desc: String,
    block: BooleanSupplier
) : NumberValue<Long>(name, value, range, step, desc, block) {
    override fun getAsString(): String = value.toString()
    override fun setAndCorrect(value: Long) {
        val fixedValue = value.coerceIn(range)
        val delta = fixedValue - range.start
        this.value = range.start + (delta / step.toFloat()).toInt() * step
    }

    override fun setByPercent(percentage: Float) {
        val gap = range.endInclusive - range.start
        this.value = range.start + (percentage.coerceIn(0f..1f) * gap).toInt()
    }

    override fun saveValue(jsonObject: JsonObject) = jsonObject.addProperty(name, value)
    override fun readValue(jsonObject: JsonObject) {
        value = jsonObject[name]?.asLong ?: value
    }
}

class FloatValue(
    name: String,
    value: Float,
    range: FloatRange,
    step: Float,
    desc: String,
    block: BooleanSupplier
) : NumberValue<Float>(name, value, range, step, desc, block) {
    override fun getAsString(): String = FLOAT_FORMAT.format(value)
    override fun setAndCorrect(value: Float) {
        val fixedValue = value.coerceIn(range)
        val delta = fixedValue - range.start
        this.value = range.start + (delta / step).toInt() * step
    }

    override fun setByPercent(percentage: Float) {
        val gap = range.endInclusive - range.start
        this.value = range.start + (percentage.coerceIn(0F..1F) * gap).toInt()
    }

    override fun saveValue(jsonObject: JsonObject) = jsonObject.addProperty(name, value)
    override fun readValue(jsonObject: JsonObject) {
        value = jsonObject[name]?.asFloat ?: value
    }
}

class DoubleValue(
    name: String,
    value: Double,
    range: DoubleRange,
    step: Double,
    desc: String,
    block: BooleanSupplier
) : NumberValue<Double>(name, value, range, step, desc, block) {
    override fun getAsString(): String = DOUBLE_FORMAT.format(value)
    override fun setAndCorrect(value: Double) {
        val fixedValue = value.coerceIn(range)
        val delta = fixedValue - range.start
        this.value = range.start + (delta / step).toInt() * step
    }

    override fun setByPercent(percentage: Float) {
        val gap = range.endInclusive - range.start
        this.value = range.start + (percentage.coerceIn(0f..1f) * gap).toInt()
    }

    override fun saveValue(jsonObject: JsonObject) = jsonObject.addProperty(name, value)
    override fun readValue(jsonObject: JsonObject) {
        value = jsonObject[name]?.asDouble ?: value
    }
}

class StringValue(
    name: String,
    value: String,
    desc: String,
    block: BooleanSupplier
) : AbstractValue<String>(name, value, desc, block) {
    override fun saveValue(jsonObject: JsonObject) = jsonObject.addProperty(name, value)
    override fun readValue(jsonObject: JsonObject) {
        value = jsonObject[name]?.asString ?: value
    }
}

class BooleanValue(
    name: String,
    value: Boolean,
    desc: String,
    block: BooleanSupplier
) : AbstractValue<Boolean>(name, value, desc, block) {
    override fun saveValue(jsonObject: JsonObject) = jsonObject.addProperty(name, value)
    override fun readValue(jsonObject: JsonObject) {
        value = jsonObject[name]?.asBoolean ?: value
    }
}

class ListValue(
    name: String,
    value: List<String>,
    desc: String,
    block: BooleanSupplier
) : AbstractValue<List<String>>(name, value, desc, block) {
    override fun saveValue(jsonObject: JsonObject) = jsonObject.add(name, JsonArray().apply {
        value.forEach { add(it) }
    })

    override fun readValue(jsonObject: JsonObject) {
        value = jsonObject[name]?.asJsonArray?.map { it.asString } ?: value
    }
}