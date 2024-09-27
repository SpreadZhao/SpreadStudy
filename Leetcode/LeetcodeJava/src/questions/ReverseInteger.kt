package questions

/**
 * Link: [Reverse Integer](https://leetcode.com/problems/reverse-integer/)
 */
class ReverseInteger {
    fun reverse(x: Int): Int {
        val xstr = Math.abs(x).toString()
        //System.out.println(xstr);
        var i = 0
        var j = xstr.length - 1
        val xchs = CharArray(xstr.length)
        while (i <= j) {
            xchs[i] = xstr[j]
            xchs[j] = xstr[i]
            i++
            j--
        }
        var res: Int
        res = try {
            String(xchs).toInt()
        } catch (e: NumberFormatException) {
            0
        }
        if (x < 0) res = -res
        return res
    }

    fun reverse2(x: Int): Int {
        var xx = x
        var res = 0
        val BOUNDARY_P = Int.MAX_VALUE / 10
        val BOUNDARY_N = Int.MIN_VALUE / 10
        while (xx != 0) {
            val a = xx % 10
            if (res > BOUNDARY_P || res == BOUNDARY_P && a > 7) return 0
            if (res < BOUNDARY_N || res == BOUNDARY_N && a < -8) return 0
            res = res * 10 + a
            xx /= 10
        }
        return res
    }
}