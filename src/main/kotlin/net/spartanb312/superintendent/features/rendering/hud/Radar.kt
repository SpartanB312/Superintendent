package net.spartanb312.superintendent.features.rendering.hud

import net.minecraft.entity.monster.EntityMob
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.passive.EntityVillager
import net.minecraft.entity.player.EntityPlayer
import net.spartanb312.superintendent.event.events.render.Render2DEvent
import net.spartanb312.superintendent.features.rendering.RenderModule
import net.spartanb312.superintendent.graphics.SmallFontRenderer
import net.spartanb312.superintendent.graphics.core.draw.RenderUtils
import net.spartanb312.superintendent.utils.awt.ColorRGB
import net.spartanb312.superintendent.utils.config.setting
import net.spartanb312.superintendent.utils.math.vector.Vec2f
import net.spartanb312.superintendent.utils.math.vector.distanceTo
import net.spartanb312.superintendent.utils.mc
import net.spartanb312.superintendent.utils.timming.LateUpdateValue
import org.lwjgl.opengl.GL11
import kotlin.math.abs

object Radar : RenderModule.HUD("Radar") {

    private var generalScale by setting("Scale", 1f, 0.1f..5.0f, 0.1f, "The scale of shield HUD.")
    private var radarRange by setting("Range", 20, 1..100, 1, "Radar range")
    private var esp by setting("Entity ESP", false, "Highlight entity outline")

    private val arc1Vertices = RenderUtils.getArcVertices(0.0, 0.0, 50f, Pair(0f, 360f)).reversed().toTypedArray()
    private val arc2Vertices = RenderUtils.getArcVertices(0.0, 0.0, 25f, Pair(0f, 360f)).reversed().toTypedArray()
    private val centerVertices = RenderUtils.getArcVertices(0.0, 0.0, 7f, Pair(0f, 360f)).reversed().toTypedArray()
    private val generalColor = ColorRGB(101, 176, 210)
    private val lightColor = ColorRGB(194, 247, 254)

    private val biomeName by LateUpdateValue(1000) {
        mc.world.getBiome(mc.player.position).biomeName
    }
    private val entities by LateUpdateValue(200) {
        mc.world.loadedEntityList.filter { it.getDistance(mc.player) < radarRange }
    }

    override fun render2D(event: Render2DEvent) {
        val scale = generalScale

        val h = event.res.scaledHeight - 70f
        GL11.glTranslatef(70f, h, 0f)
        GL11.glScalef(scale, scale, scale)

        RenderUtils.drawTriangleFan(0.0, 0.0, arc1Vertices, generalColor.alpha(64))
        RenderUtils.drawArcOutline(arc1Vertices, 2f, generalColor)
        RenderUtils.drawArcOutline(arc2Vertices, 2f, generalColor)

        // pulse
        val time = System.currentTimeMillis() % 3000
        val rate = (time / 1000f).coerceIn(0f..1f)
        val pulseRadius = 7 + 43 * rate
        val alpha = when (time) {
            in 0..1000 -> 64
            in 1000..2000 -> ((1 - (time - 1000) / 1000f) * 64).toInt()
            else -> 0
        }
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        RenderUtils.drawArc(
            Vec2f.ZERO,
            pulseRadius,
            Pair(0f, 360f),
            color = generalColor.alpha(alpha / 2)
        )
        RenderUtils.drawArcOutline(
            Vec2f.ZERO,
            pulseRadius,
            Pair(0f, 360f),
            lineWidth = 5f,
            color = generalColor.alpha(alpha)
        )

        if (esp) {
            if (time < 1000) {
                val scanDistance = 100 * rate
                for (entity in mc.world.loadedEntityList) {
                    if (entity == mc.player) continue
                    if (scanDistance <= entity.getDistance(mc.player)) continue
                    if (mc.player.canEntityBeSeen(entity))
                        entity.isGlowing = true
                }
            } else if (time in 2000..3000) {
                val rate2 = (time / 1000f).coerceIn(2f..3f) - 2f
                val fallBackDis = 100 * rate2
                for (entity in mc.world.loadedEntityList) {
                    if (entity == mc.player) continue
                    if (fallBackDis <= entity.getDistance(mc.player)) continue
                    entity.isGlowing = false
                }
            }
        }

        // cross
        val angle = (mc.player.rotationYaw.toInt() + 360 + 180) % 360
        GL11.glRotatef(-angle.toFloat(), 0f, 0f, 1f)
        RenderUtils.drawLine(Vec2f(0f, 50f), Vec2f(0f, -50f), 1.5f, generalColor.alpha(192))
        RenderUtils.drawLine(Vec2f(50f, 0f), Vec2f(-50f, 0f), 1.5f, generalColor.alpha(192))
        GL11.glRotatef(angle.toFloat(), 0f, 0f, 1f)

        // fov
        val fovAngle = mc.gameSettings.fovSetting / 2
        RenderUtils.drawArc(Vec2f.ZERO, 50f, Pair(-fovAngle, fovAngle), color = generalColor.alpha(96))

        // center
        RenderUtils.drawTriangleFan(0.0, 0.0, centerVertices, lightColor, generalColor)
        RenderUtils.drawArcOutline(centerVertices, 2f, generalColor)

        // entities
        val playerVecRaw = Vec2f(mc.player.posX, mc.player.posZ)
        val moveCompensation = Vec2f(
            mc.player.posX - mc.player.lastTickPosX,
            mc.player.posZ - mc.player.lastTickPosZ
        ) * event.partialTicks
        val playerVec = playerVecRaw + moveCompensation
        GL11.glRotatef(-angle.toFloat(), 0f, 0f, 1f)
        for (it in entities) {
            if (it == mc.player) continue
            val color = when (it) {
                is EntityAnimal -> ColorRGB.YELLOW
                is EntityMob -> ColorRGB(255, 30, 0)
                is EntityVillager -> ColorRGB.AQUA
                is EntityPlayer -> lightColor
                else -> continue
            }
            val offsetVec = (Vec2f(it.posX, it.posZ) - playerVec) * (50f / radarRange)
            val distance = offsetVec.distanceTo(Vec2f.ZERO)
            if (distance > 47.5) continue
            val highLight = (rate < 1) && (abs(distance - pulseRadius) < 5f)
            val highLightAlpha = 255 - 95 * abs(distance - pulseRadius) / 5f
            RenderUtils.drawPoint(
                offsetVec.x,
                offsetVec.y,
                5f * event.res.scaleFactor,
                color.alpha(if (highLight) highLightAlpha.toInt() else 160)
            )
        }
        GL11.glRotatef(angle.toFloat(), 0f, 0f, 1f)

        // biome name
        val startX = 42f
        val startY = 50f - SmallFontRenderer.getHeight(0.4f)
        drawShadowedString(biomeName, startX, startY)

        GL11.glScalef(1f / scale, 1f / scale, 1f / scale)
        GL11.glTranslatef(-70f, -h, 0f)
    }

    fun drawShadowedString(
        str: String,
        x: Float,
        y: Float,
        scale: Float = 0.4f,
        centered: Boolean = false
    ) {
        if (centered) {
            SmallFontRenderer.drawCenteredString(str, x, y, lightColor.alpha(192), scale)
            SmallFontRenderer.drawCenteredString(str, x + 1, y + 1f, generalColor.alpha(64), scale)
        } else {
            SmallFontRenderer.drawString(str, x, y, lightColor.alpha(192), scale)
            SmallFontRenderer.drawString(str, x + 1, y + 1f, generalColor.alpha(64), scale)
        }
    }

}