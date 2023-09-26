package net.spartanb312.superintendent.graphics.core

import org.lwjgl.opengl.GL11

object MatrixUtils {

    @JvmStatic
    fun pushMatrix(mode: Int = -1) {
        if (mode == -1) {
            GL11.glMatrixMode(GL11.GL_PROJECTION)
            GL11.glPushMatrix()
            GL11.glMatrixMode(GL11.GL_MODELVIEW)
            GL11.glPushMatrix()
        } else {
            GL11.glMatrixMode(mode)
            GL11.glPushMatrix()
        }
    }

    @JvmStatic
    fun popMatrix(mode: Int = -1) {
        if (mode == -1) {
            GL11.glMatrixMode(GL11.GL_PROJECTION)
            GL11.glPopMatrix()
            GL11.glMatrixMode(GL11.GL_MODELVIEW)
            GL11.glPopMatrix()
        } else {
            GL11.glMatrixMode(mode)
            GL11.glPopMatrix()
        }
    }

}