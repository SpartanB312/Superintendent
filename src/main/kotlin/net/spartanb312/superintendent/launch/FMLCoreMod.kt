package net.spartanb312.superintendent.launch

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
import org.apache.logging.log4j.LogManager
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.MixinEnvironment
import org.spongepowered.asm.mixin.Mixins

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("Superintendent")
@IFMLLoadingPlugin.SortingIndex(value = 114514)
class FMLCoreMod : IFMLLoadingPlugin {

    init {
        LogManager.getLogger("Superintendent-Core").apply {
            MixinBootstrap.init()
            Mixins.addConfigurations("mixins.superintendent.json")
            MixinEnvironment.getDefaultEnvironment().obfuscationContext = "searge"
            info("Superintendent mixins initialized.")
        }
    }

    override fun injectData(data: Map<String, Any>) {

    }

    override fun getASMTransformerClass(): Array<String> {
        return emptyArray()
    }

    override fun getModContainerClass(): String? {
        return null
    }

    override fun getSetupClass(): String? {
        return null
    }

    override fun getAccessTransformerClass(): String? {
        return null
    }

}