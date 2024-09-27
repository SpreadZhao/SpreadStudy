package exercise

class Schedule {
    fun minAverageCompletion(time: IntArray): Double {
        time.sort()
        var ac = 0.0;
        var sum = 0.0
        for (t in time) {
            sum += t
            ac += sum / time.size
        }
        return ac
    }
}