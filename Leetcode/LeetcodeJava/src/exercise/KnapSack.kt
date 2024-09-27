package exercise

import kotlin.math.max

class KnapSack {
    //    inner class ItemValue(val profit: Int, val weight: Int)
    class ItemValue(val profit: Int, val weight: Int)

    fun getMaxValue(arr: Array<ItemValue>, capacity: Int): Double {
        /** Kotlin anonymous class: object
         * arr.sortWith(object : Comparator<ItemValue> {
         *             override fun compare(item1: ItemValue?, item2: ItemValue?): Int {
         *                 val p1 = item1?.profit ?: -1
         *                 val w1 = item1?.weight ?: -1
         *                 val p2 = item2?.profit ?: -1
         *                 val w2 = item2?.weight ?: -1
         *                 val cp1 = p1.toDouble() / w1
         *                 val cp2 = p2.toDouble() / w2
         *                 return if (cp1 < cp2) 1 else -1
         *             }
         *
         *         })
         */
        arr.sortWith { item1, item2 ->
            val p1 = item1?.profit ?: -1
            val w1 = item1?.weight ?: -1
            val p2 = item2?.profit ?: -1
            val w2 = item2?.weight ?: -1
            val cp1 = p1.toDouble() / w1
            val cp2 = p2.toDouble() / w2
            if (cp1 < cp2) 1 else -1
        }

        var totalPft = 0.0
        var cap = capacity
        for (i in arr) {
            val curWt = i.weight
            val curPft = i.profit
            if (cap >= curWt) {
                cap -= curWt
                totalPft += curPft
            } else {
                val fraction = cap.toDouble() / curWt
                totalPft += curPft * fraction
//                cap = (cap - (curWt * fraction)).toInt()
                cap -= (curWt * fraction).toInt()
                break
            }
        }
        return totalPft
    }


    fun zeroOneKnap(capacity: Int, wt: IntArray, pft: IntArray, n: Int): Int {
        if (n == 0 || capacity == 0) return 0
        return if (wt[n - 1] > capacity) zeroOneKnap(capacity, wt, pft, n - 1)
        else max(
            pft[n - 1] + zeroOneKnap(capacity - wt[n - 1], wt, pft, n - 1),
            zeroOneKnap(capacity, wt, pft, n - 1)
        )
    }

    fun zeroOneKnap2(capacity: Int, wt: IntArray, pft: IntArray, n: Int): Int {
        val dp = Array(n + 1) { IntArray(capacity + 1) { -1 } }
        return zok(capacity, wt, pft, n, dp)
    }

    private fun zok(capacity: Int, wt: IntArray, pft: IntArray, n: Int, dp: Array<IntArray>): Int {
        if (n == 0 || capacity == 0) return 0
        if (dp[n][capacity] != -1) return dp[n][capacity]
        return if (wt[n - 1] > capacity) {
            dp[n][capacity] = zok(capacity, wt, pft, n - 1, dp)
            dp[n][capacity]
        } else {
            dp[n][capacity] = max(
                pft[n - 1] + zok(capacity - wt[n - 1], wt, pft, n - 1, dp),
                zok(capacity, wt, pft, n - 1, dp)
            )
            dp[n][capacity]
        }
    }

    fun zeroOneKnap3(capacity: Int, wt: IntArray, pft: IntArray, n: Int): Int {
        val dp = Array(n + 1) { IntArray(capacity + 1) { 0 } }
        for (i in 0..n) {
            for (w in 0..capacity) {
                dp[i][w] = if (i == 0 || w == 0) 0
                else if (wt[i - 1] <= w)
                    max(pft[i - 1] + dp[i - 1][w - wt[i - 1]], dp[i - 1][w])
                else
                    dp[i - 1][w]
            }
        }
        return dp[n][capacity]
    }

    private var res = Int.MIN_VALUE
    fun zeroOneKnap4(capacity: Int, wt: IntArray, pft: IntArray, n: Int): Int {
        val isChosen = BooleanArray(n)
        choose(capacity, wt, pft, 0, n, isChosen)
        return res
    }

    private fun choose(capacity: Int, wt: IntArray, pft: IntArray, curr: Int, n: Int, isChosen: BooleanArray) {
        if (curr >= n) {
            checkMax(capacity, wt, pft, n, isChosen)
        } else {
            isChosen[curr] = false
            choose(capacity, wt, pft, curr + 1, n, isChosen)
            isChosen[curr] = true
            choose(capacity, wt, pft, curr + 1, n, isChosen)
        }
    }

    private fun checkMax(capacity: Int, wt: IntArray, pft: IntArray, n: Int, isChosen: BooleanArray) {
        var weight = 0;
        var profit = 0
        for (i in isChosen.indices) {
            if (isChosen[i]) {
                weight += wt[i]
                profit += pft[i]
            }
        }
        if (weight <= capacity) {
            if (profit > res) {
                res = profit
            }
        }
    }
}