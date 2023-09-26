package net.spartanb312.superintendent

import net.spartanb312.superintendent.event.ListenerOwner
import net.spartanb312.superintendent.features.ConfigManager
import net.spartanb312.superintendent.features.FeatureManager
import net.spartanb312.superintendent.features.rendering.RenderManager
import net.spartanb312.superintendent.utils.Logger
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GLContext

/**
 * Initialize via Mixin
 */
object Superintendent : ListenerOwner() {

    const val MOD_NAME = "Superintendent"
    const val MOD_VERSION = "1.0-dev"

    var compatibilityMode = false; private set

    fun initialize() {
        Logger.info("Initializing Superintendent...")
        Superintendent.subscribe()
        FeatureManager.init()
        RenderManager.init()

        Logger.info("Loading Config")
        ConfigManager.loadAll()

        Runtime.getRuntime().addShutdownHook(Thread {
            ConfigManager.saveAll()
        })
    }


    object Compatibility {
        val gpuManufacturer = GL11.glGetString(GL11.GL_VENDOR) ?: ""
        val gpuName = GL11.glGetString(GL11.GL_RENDERER) ?: ""
        val glContextVersion = GL11.glGetString(GL11.GL_VERSION) ?: ""

        private val contextCapabilities = GLContext.getCapabilities()
        val openGL11 get() = contextCapabilities.OpenGL11
        val openGL12 get() = contextCapabilities.OpenGL12 && !compatibilityMode
        val openGL13 get() = contextCapabilities.OpenGL13 && !compatibilityMode
        val openGL14 get() = contextCapabilities.OpenGL14 && !compatibilityMode
        val openGL15 get() = contextCapabilities.OpenGL15 && !compatibilityMode
        val openGL20 get() = contextCapabilities.OpenGL20 && !compatibilityMode
        val openGL21 get() = contextCapabilities.OpenGL21 && !compatibilityMode
        val openGL30 get() = contextCapabilities.OpenGL30 && !compatibilityMode
        val openGL31 get() = contextCapabilities.OpenGL31 && !compatibilityMode
        val openGL32 get() = contextCapabilities.OpenGL32 && !compatibilityMode
        val openGL33 get() = contextCapabilities.OpenGL33 && !compatibilityMode
        val openGL40 get() = contextCapabilities.OpenGL40 && !compatibilityMode
        val openGL41 get() = contextCapabilities.OpenGL41 && !compatibilityMode
        val openGL42 get() = contextCapabilities.OpenGL42 && !compatibilityMode
        val openGL43 get() = contextCapabilities.OpenGL43 && !compatibilityMode
        val openGL44 get() = contextCapabilities.OpenGL44 && !compatibilityMode
        val openGL45 get() = contextCapabilities.OpenGL45 && !compatibilityMode

        val extFramebufferObject get() = contextCapabilities.GL_EXT_framebuffer_object && !contextCapabilities.OpenGL30

        val intelGraphics = glContextVersion.lowercase().contains("intel")
                || gpuManufacturer.lowercase().contains("intel")
    }

}