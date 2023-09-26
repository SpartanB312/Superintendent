package net.spartanb312.superintendent.features.rendering.hud

import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.spartanb312.superintendent.event.events.render.Render2DEvent
import net.spartanb312.superintendent.features.rendering.RenderModule
import net.spartanb312.superintendent.graphics.SmallFontRenderer
import net.spartanb312.superintendent.graphics.core.GLStatesManager
import net.spartanb312.superintendent.graphics.core.MatrixUtils
import net.spartanb312.superintendent.graphics.core.draw.RenderUtils
import net.spartanb312.superintendent.utils.awt.ColorRGB
import net.spartanb312.superintendent.utils.math.vector.Vec2f
import net.spartanb312.superintendent.utils.mc
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*

object ItemsHUD : RenderModule.HUD("ItemsHUD") {

    private val generalColor = ColorRGB(101, 176, 210)
    private val lightColor = ColorRGB(194, 247, 254)

    override fun render2D(event: Render2DEvent) {
        val w = event.res.scaledWidth - 145f
        val h = event.res.scaledHeight - 85f

        MatrixUtils.pushMatrix(GL11.GL_MODELVIEW)
        glTranslatef(w, h, 0f)
        GLStatesManager.shadeModel = GL_SMOOTH
        glAlphaFunc(GL_GREATER, 0.004f);
        GlStateManager.tryBlendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
        )

        // background
        RenderUtils.drawGradientRect(
            Vec2f(-10f, 20f),
            Vec2f(65f, 50f),
            generalColor.alpha(128),
            generalColor.alpha(0),
            generalColor.alpha(0),
            generalColor.alpha(128),
        )
        RenderUtils.drawGradientRect(
            Vec2f(65f, 20f),
            Vec2f(140f, 50f),
            generalColor.alpha(0),
            generalColor.alpha(128),
            generalColor.alpha(128),
            generalColor.alpha(0),
        )

        // lines -10,(0,130),140
        val cAlpha = 192
        RenderUtils.drawLine(Vec2f(-10f, 20f), Vec2f(65f, 20f), 4f, generalColor.alpha(0), generalColor.alpha(cAlpha))
        RenderUtils.drawLine(Vec2f(65f, 20f), Vec2f(140f, 20f), 4f, generalColor.alpha(cAlpha), generalColor.alpha(0))
        RenderUtils.drawLine(Vec2f(-10f, 50f), Vec2f(65f, 50f), 4f, generalColor.alpha(0), generalColor.alpha(cAlpha))
        RenderUtils.drawLine(Vec2f(65f, 50f), Vec2f(140f, 50f), 4f, generalColor.alpha(cAlpha), generalColor.alpha(0))
        val heldStack = if (mc.player.heldItemMainhand.item != Items.AIR) mc.player.heldItemMainhand
        else mc.player.heldItemOffhand
        if (heldStack.isEmpty) {
            MatrixUtils.popMatrix(GL11.GL_MODELVIEW)
            return
        }
        val str = mc.player.heldItemMainhand.displayName
        val startX = 125 - SmallFontRenderer.getWidth(str, 0.4f)
        Radar.drawShadowedString(str, startX, 53f)

        // items
        RenderHelper.enableGUIStandardItemLighting()
        glTranslatef(82f, 25f, 0f)
        GL11.glScalef(1.25f, 1.25f, 1f)
        mc.renderItem.renderItemAndEffectIntoGUI(heldStack, 0, 0)
        GL11.glScalef(0.8f, 0.8f, 1f)
        glTranslatef(-82f, -25f, 0f)
        RenderHelper.disableStandardItemLighting()
        val height = SmallFontRenderer.getHeight(0.4f)
        SmallFontRenderer.drawString(
            (getItemCount(heldStack.item) - heldStack.count).toString(),
            105f,
            37f - height,
            generalColor.alpha(160),
            0.35f
        )
        SmallFontRenderer.drawString(
            heldStack.count.toString(),
            105f,
            34f,
            lightColor.alpha(160),
            0.4f
        )

        MatrixUtils.popMatrix(GL11.GL_MODELVIEW)
    }

    private fun getItemCount(item: Item): Int {
        var count = mc.player.inventory.mainInventory
            .filter { itemStack: ItemStack -> itemStack.item === item }
            .sumOf { obj: ItemStack -> obj.count }
        if (mc.player.heldItemOffhand.item === item) {
            count += mc.player.heldItemOffhand.count
        }
        return count
    }

}