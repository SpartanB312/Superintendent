package net.spartanb312.superintendent.utils.threading

import kotlinx.coroutines.CoroutineScope

object MainScope : CoroutineScope by newCoroutineScope(Runtime.getRuntime().availableProcessors(), "Superintendent")