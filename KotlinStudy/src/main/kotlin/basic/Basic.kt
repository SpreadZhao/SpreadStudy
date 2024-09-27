package basic

import java.util.Collections.emptyList

class Basic {
    companion object {
        @JvmStatic
        fun test() {
            val people = Basic().People()
        }
    }

    inner class People
}

fun main() {
    val data = listOf(
        "周次:3-4周",
        "周次:1周",
        "周次:6-16周",
        "周次:1-3单周",
        "周次:5周"
    )

    data.forEach { weekly ->
        println("Parsed weekly from '$weekly': ${parseWeekly(weekly)}")
    }
}

fun parseWeekly(weekly: String): List<Int> {
    val regex = Regex("""(\d+)(?:-(\d+))?周""")
    val match = regex.find(weekly)

    if (match != null) {
        val startWeek = match.groupValues[1].toInt()
        val endWeek = match.groupValues[2].ifEmpty { startWeek.toString() }.toInt()
        val list = mutableListOf<Int>()
        for (i in startWeek..endWeek) {
            list.add(i)
        }
        return list
    } else {
        val regex2 = Regex("""(\d+)(?:-(\d+))?单周""")
        val match2 = regex2.find(weekly)
        val list = mutableListOf<Int>()
        if (match2 != null) {
            val startWeek = match2.groupValues[1].toInt()
            val endWeek = match2.groupValues[2].ifEmpty { startWeek.toString() }.toInt()
            for (i in startWeek..endWeek) {
                if (i % 2 == 1) {
                    list.add(i)
                }
            }
        }
        return list
    }
}
