package net.spartanb312.superintendent.features

import net.spartanb312.superintendent.features.rendering.RenderManager
import net.spartanb312.superintendent.features.utility.UtilityManager

object FeatureManager {

    val features = mutableListOf(
        RenderManager,
        UtilityManager
    )

    fun init() {

    }

}