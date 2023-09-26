package net.spartanb312.superintendent.utils.threading

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

fun newCoroutineContext(nThreads: Int, name: String): ExecutorCoroutineDispatcher {
    require(nThreads >= 1) { "Expected at least one thread, but $nThreads specified" }
    val threadNo = AtomicInteger()
    val executor = Executors.newScheduledThreadPool(nThreads) { runnable ->
        val t = Thread(runnable, if (nThreads == 1) name else name + "-" + threadNo.incrementAndGet())
        t.isDaemon = true
        t
    }
    return executor.asCoroutineDispatcher()
}

fun newCoroutineScope(nThreads: Int, name: String): CoroutineScope =
    CoroutineScope(newCoroutineContext(nThreads, name))