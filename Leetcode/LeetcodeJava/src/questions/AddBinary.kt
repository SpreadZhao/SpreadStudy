package questions

/**
 * Link: [Add Binary](https://leetcode.com/problems/add-binary/)
 */
class AddBinary {
    fun addBinary(a: String, b: String): String {
        var i = a.length - 1
        var j = b.length - 1
        var carry = 0
        val res = StringBuilder()
        while (i >= 0 || j >= 0) {
            val x: Int = if (i < 0) 0 else a[i] - '0'
            val y: Int = if (j < 0) 0 else b[j] - '0'
            val num = (x + y + carry) % 2
            carry = if ((x + y + carry) / 2 >= 1) {
                1
            } else {
                0
            }
            res.insert(0, num)
            i--
            j--
        }
        if (carry == 1) res.insert(0, 1)
        return res.toString()
    }
}