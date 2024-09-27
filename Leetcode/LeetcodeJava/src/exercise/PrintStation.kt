package exercise

class PrintStation {
    private fun printStation(l: Array<IntArray>, i: Int, j: Int) {
        if (j == 0) return
        printStation(l, l[i][j], j - 1)
        println("line: $i, station: $j")
    }
}

