package concurrency.atomic

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

private val ai = AtomicInteger(1)
val barrier = CyclicBarrier(2)

fun main() {
    repeat(10000) {
        print(ai.getAndIncrement())
        thread {
            ai.decrementAndGet()
            barrier.await()
        }
        barrier.await()
        println(ai.get())
        while (true) {
            if (ai.compareAndSet(1, 1)) {
                break
            }
        }
        barrier.reset()
        println("--------")
    }

}