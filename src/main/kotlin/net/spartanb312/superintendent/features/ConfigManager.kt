package net.spartanb312.superintendent.features

import net.spartanb312.superintendent.utils.config.*

object ConfigManager : ConfigurableImpl("Configs") {

    var configPath by setting("ConfigPath", "Superintendent/")
    private val configs = mutableListOf<Configurable>(this).apply { addAll(FeatureManager.features) }

    fun loadAll() {
        loadMainConfig()
        loadFeaturesConfig()
    }

    fun saveAll() {
        saveMainConfig()
        saveFeaturesConfig()
    }

    fun loadFeaturesConfig() {
        FeatureManager.features.forEach {
            it.loadConfig("${configPath}Features/${it.name}/")
        }
    }

    fun saveFeaturesConfig() {
        FeatureManager.features.forEach {
            it.saveConfig("${configPath}/Features/${it.name}/")
        }
    }

    fun loadMainConfig() = configs.loadConfig("Superintendent.json")

    fun saveMainConfig() = configs.saveConfig("Superintendent.json")

}