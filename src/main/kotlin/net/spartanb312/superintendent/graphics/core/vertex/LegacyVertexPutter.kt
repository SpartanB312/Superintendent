package net.spartanb312.superintendent.graphics.core.vertex

import net.spartanb312.superintendent.utils.awt.ColorRGB
import net.spartanb312.superintendent.utils.awt.ColorUtils.aFloat
import net.spartanb312.superintendent.utils.awt.ColorUtils.bFloat
import net.spartanb312.superintendent.utils.awt.ColorUtils.gFloat
import net.spartanb312.superintendent.utils.awt.ColorUtils.rFloat
import net.spartanb312.superintendent.utils.math.vector.Vec2d
import net.spartanb312.superintendent.utils.math.vector.Vec2f
import net.spartanb312.superintendent.utils.math.vector.Vec3d
import net.spartanb312.superintendent.utils.math.vector.Vec3f
import org.lwjgl.opengl.GL11.*

object LegacyVertexPutter {

    fun begin(mode: Int) = glBegin(mode)

    inline fun Int.begin(block: LegacyVertexPutter.() -> Unit) {
        begin(this)
        this@LegacyVertexPutter.block()
        end()
    }

    /**
     * 2D
     */
    fun put(x: Double, y: Double, color: ColorRGB) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex2d(x, y)
    }

    fun put(x: Double, y: Double, color: Int) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex2d(x, y)
    }

    fun put(pos: Vec2d, color: ColorRGB) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex2d(pos.x, pos.y)
    }

    fun put(pos: Vec2d, color: Int) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex2d(pos.x, pos.y)
    }

    fun put(x: Float, y: Float, color: ColorRGB) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex2d(x.toDouble(), y.toDouble())
    }

    fun put(x: Float, y: Float, color: Int) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex2d(x.toDouble(), y.toDouble())
    }

    fun put(pos: Vec2f, color: ColorRGB) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex2d(pos.x.toDouble(), pos.y.toDouble())
    }

    fun put(pos: Vec2f, color: Int) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex2d(pos.x.toDouble(), pos.y.toDouble())
    }

    fun put(x: Int, y: Int, color: ColorRGB) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex2d(x.toDouble(), y.toDouble())
    }

    fun put(x: Int, y: Int, color: Int) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex2d(x.toDouble(), y.toDouble())
    }

    /**
     * 3D
     */
    fun put(x: Double, y: Double, z: Double, color: ColorRGB) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex3d(x, y, z)
    }

    fun put(x: Double, y: Double, z: Double, color: Int) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex3d(x, y, z)
    }

    fun put(pos: Vec3d, color: ColorRGB) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex3d(pos.x, pos.y, pos.z)
    }

    fun put(pos: Vec3d, color: Int) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex3d(pos.x, pos.y, pos.z)
    }

    fun put(x: Float, y: Float, z: Float, color: ColorRGB) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex3f(x, y, z)
    }

    fun put(x: Float, y: Float, z: Float, color: Int) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex3f(x, y, z)
    }

    fun put(pos: Vec3f, color: ColorRGB) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex3f(pos.x, pos.y, pos.z)
    }

    fun put(pos: Vec3f, color: Int) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex3f(pos.x, pos.y, pos.z)
    }

    fun put(x: Int, y: Int, z: Int, color: ColorRGB) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex3i(x, y, z)
    }

    fun put(x: Int, y: Int, z: Int, color: Int) {
        glColor4f(color.rFloat, color.gFloat, color.bFloat, color.aFloat)
        glVertex3i(x, y, z)
    }

    fun end() = glEnd()

}