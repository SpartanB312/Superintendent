package net.spartanb312.superintendent.event

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.spartanb312.superintendent.utils.threading.MainScope
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Original Author: Luna
 * Optimized by B312 on 2023/8/18
 */
open class EventBus : EventPosting {
    override val eventBus: EventBus get() = this
    protected val listeners = CopyOnWriteArrayList<Listener>()
    private val parallelListeners = CopyOnWriteArrayList<ParallelListener>()

    override fun post(event: Any) {
        for (listener in listeners) {
            listener.function.invoke(event)
        }

        invokeParallel(event)
    }

    protected fun invokeParallel(event: Any) {
        if (!parallelListeners.isEmpty()) {
            runBlocking {
                for (listener in parallelListeners) {
                    launch(MainScope.coroutineContext) {
                        listener.function.invoke(event)
                    }
                }
            }
        }
    }

    fun subscribe(listener: Listener) {
        for (i in listeners.indices) {
            val other = listeners[i]
            if (listener == other) {
                return
            } else if (listener.priority > other.priority) {
                listeners.add(i, listener)
                return
            }
        }

        listeners.add(listener)
    }

    fun subscribe(listener: ParallelListener) {
        for (i in parallelListeners.indices) {
            val other = parallelListeners[i]
            if (listener == other) {
                return
            } else if (listener.priority > other.priority) {
                parallelListeners.add(i, listener)
                return
            }
        }

        parallelListeners.add(listener)
    }

    fun unsubscribe(listener: Listener) {
        listeners.removeIf {
            it == listener
        }
    }

    fun unsubscribe(listener: ParallelListener) {
        parallelListeners.removeIf {
            it == listener
        }
    }
}