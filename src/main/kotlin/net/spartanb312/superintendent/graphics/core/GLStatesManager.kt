package net.spartanb312.superintendent.graphics.core

import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.opengl.GL11

object GLStatesManager {

    var alpha by GLState(false) { if (it) GlStateManager.enableAlpha() else GlStateManager.disableAlpha() }
    var blend by GLState(false) { if (it) GlStateManager.enableBlend() else GlStateManager.disableBlend() }
    var smooth by GLState(false) { if (it) GL11.glEnable(GL11.GL_SMOOTH) else GL11.glDisable(GL11.GL_FLAT) }
    var depth by GLState(false) { if (it) GlStateManager.enableDepth() else GlStateManager.disableDepth() }
    var texture2d by GLState(false) { if (it) GlStateManager.enableTexture2D() else GlStateManager.disableTexture2D() }
    var cull by GLState(false) { if (it) GlStateManager.enableCull() else GlStateManager.disableCull() }
    var pointSmooth by GLState(false) { if (it) GL11.glEnable(GL11.GL_POINT_SMOOTH) else GL11.glDisable(GL11.GL_POINT_SMOOTH) }
    var lineSmooth by GLState(false) { if (it) GL11.glEnable(GL11.GL_LINE_SMOOTH) else GL11.glDisable(GL11.GL_LINE_SMOOTH) }
    var polygonSmooth by GLState(false) { if (it) GL11.glEnable(GL11.GL_POLYGON_SMOOTH) else GL11.glDisable(GL11.GL_POLYGON_SMOOTH) }
    var shadeModel by GLState(GL11.GL_FLAT) { GlStateManager.shadeModel(it) }

}