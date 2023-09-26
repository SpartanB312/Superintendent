package net.spartanb312.superintendent.graphics

import net.spartanb312.superintendent.graphics.font.IFontRenderer
import net.spartanb312.superintendent.graphics.font.UnicodeFontRenderer

object MainFontRenderer : IFontRenderer by UnicodeFontRenderer(
    "/assets/superintendent/font/Microsoft YaHei UI.ttc",
    100f, linearMag = true,
    imgSize = 560,
    chunkSize = 16,
    scaleFactor = 0.25f,
    useMipmap = true
)

object KnightFont : IFontRenderer by UnicodeFontRenderer(
    "/assets/superintendent/font/FoughtKnight.ttf",
    100f, linearMag = true,
    imgSize = 560,
    chunkSize = 16,
    scaleFactor = 0.25f,
    useMipmap = true
)

object SmallFontRenderer : IFontRenderer by UnicodeFontRenderer(
    "/assets/superintendent/font/Microsoft YaHei UI.ttc",
    50f, linearMag = true,
    imgSize = 280,
    chunkSize = 16,
    scaleFactor = 0.5f,
    useMipmap = true
)

object TinyFontRenderer : IFontRenderer by UnicodeFontRenderer(
    "/assets/superintendent/font/Microsoft YaHei UI.ttc",
    25f, linearMag = true,
    imgSize = 140,
    chunkSize = 16,
    scaleFactor = 1f,
    useMipmap = true
)
