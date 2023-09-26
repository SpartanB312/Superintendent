package net.spartanb312.superintendent.utils.config

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.spartanb312.superintendent.utils.DoubleRange
import net.spartanb312.superintendent.utils.FloatRange
import net.spartanb312.superintendent.utils.config.AbstractValue.Companion.ALWAYS_TRUE
import java.io.*
import java.util.function.BooleanSupplier

/**
 * Created by B312
 * 2023/8/17
 */
interface Configurable {

    val name: String
    val values: MutableList<AbstractValue<*>>

    fun saveValue(): JsonObject {
        return JsonObject().apply {
            values.forEach { it.saveValue(this) }
        }
    }

    fun readValue(jsonObject: JsonObject) {
        values.forEach { it.readValue(jsonObject) }
    }

    fun <T> addValue(value: AbstractValue<T>): AbstractValue<T> {
        values.add(value)
        return value
    }

}

open class ConfigurableImpl(override val name: String) : Configurable {
    override val values = mutableListOf<AbstractValue<*>>()
}

context(Configurable)
fun setting(
    name: String,
    value: String,
    desc: String = "",
    block: BooleanSupplier = ALWAYS_TRUE
) = addValue(StringValue(name, value, desc, block))

context(Configurable)
fun setting(
    name: String,
    value: Boolean,
    desc: String = "",
    block: BooleanSupplier = ALWAYS_TRUE
) = addValue(BooleanValue(name, value, desc, block))

context(Configurable)
fun setting(
    name: String,
    value: List<String>,
    desc: String = "",
    block: BooleanSupplier = ALWAYS_TRUE
) = addValue(ListValue(name, value, desc, block))

context(Configurable)
fun setting(
    name: String,
    value: Int,
    range: IntRange,
    step: Int = 1,
    desc: String = "",
    block: BooleanSupplier = ALWAYS_TRUE
) = addValue(IntValue(name, value, range, step, desc, block))

context(Configurable)
fun setting(
    name: String,
    value: Long,
    range: LongRange,
    step: Long = 1L,
    desc: String = "",
    block: BooleanSupplier = ALWAYS_TRUE
) = addValue(LongValue(name, value, range, step, desc, block))

context(Configurable)
fun setting(
    name: String,
    value: Float,
    range: FloatRange,
    step: Float = 1F,
    desc: String = "",
    block: BooleanSupplier = ALWAYS_TRUE
) = addValue(FloatValue(name, value, range, step, desc, block))

context(Configurable)
fun setting(
    name: String,
    value: Double,
    range: DoubleRange,
    step: Double = 1.0,
    desc: String = "",
    block: BooleanSupplier = ALWAYS_TRUE
) = addValue(DoubleValue(name, value, range, step, desc, block))

val String.jsonMap: Map<String, JsonElement>
    get() {
        val loadJson = BufferedReader(FileReader(this))
        val map = mutableMapOf<String, JsonElement>()
        JsonParser().parse(loadJson).asJsonObject.entrySet().forEach {
            map[it.key] = it.value
        }
        loadJson.close()
        return map
    }

fun JsonObject.saveToFile(file: File) {
    val saveJSon = PrintWriter(FileWriter(file))
    saveJSon.println(GsonBuilder().setPrettyPrinting().create().toJson(this))
    saveJSon.close()
}

fun List<Configurable>.loadConfig(path: String) {
    val map = try {
        path.jsonMap
    } catch (ignored: Exception) {
        saveConfig(path)
        return
    }
    forEach {
        kotlin.runCatching {
            map[it.name]?.asJsonObject?.let { jo -> it.readValue(jo) }
        }
    }
}

fun List<Configurable>.saveConfig(path: String) {
    val configFile = File(path)
    if (!configFile.exists()) {
        configFile.parentFile?.mkdirs()
        configFile.createNewFile()
    }
    JsonObject().apply {
        forEach {
            add(it.name, it.saveValue())
        }
    }.saveToFile(configFile)
}

fun Configurable.loadConfig(path: String) {
    val map = try {
        path.jsonMap
    } catch (ignored: Exception) {
        saveConfig(path)
        return
    }
    kotlin.runCatching {
        map[name]?.asJsonObject?.let { jo -> readValue(jo) }
    }
}

fun Configurable.saveConfig(path: String) {
    val configFile = File(path)
    if (!configFile.exists()) {
        configFile.parentFile?.mkdirs()
        configFile.createNewFile()
    }
    JsonObject().apply {
        add(name, saveValue())
    }.saveToFile(configFile)
}
