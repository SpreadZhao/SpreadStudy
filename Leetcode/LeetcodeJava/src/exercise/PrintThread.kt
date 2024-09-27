package exercise

class PrintThread {
    private var isRunning = true
    private var currNum = 1
    private var order = 1
    private val lock = Object()
    private val th1 = Thread {
        while (isRunning && currNum <= 100) {
            synchronized(lock) {
                while (isRunning && order != 1) lock.wait()
                if (currNum < 100) {
                    println("[1]$currNum")
                    currNum++
                    order++
                } else if (currNum == 100) {
                    if (order == 1) println("[1]$currNum")
                    isRunning = false
                }
                lock.notifyAll()
            }
        }
    }
    private val th2 = Thread {
        while (isRunning && currNum <= 100) {
            synchronized(lock) {
                while (isRunning && order != 2) lock.wait()
                if (currNum < 100) {
                    println("[2]$currNum")
                    currNum++
                    order++
                } else if (currNum == 100) {
                    if (order == 2) println("[2]$currNum")
                    isRunning = false
                }
                lock.notifyAll()
            }
        }
    }
    private val th3 = Thread {
        while (isRunning && currNum <= 100) {
            synchronized(lock) {
                while (isRunning && order != 3) lock.wait()
                if (currNum < 100) {
                    println("[3]$currNum")
                    currNum++
                    order = 1
                } else if (currNum == 100) {
                    if (order == 3) println("[3]$currNum")
                    isRunning = false
                }
                lock.notifyAll()
            }
        }
    }

    fun start() {
        th1.start()
        th2.start()
        th3.start()
    }
}
