package questions

/**
 * Link: [Letter Combinations of a Phone Number](https://leetcode.com/problems/letter-combinations-of-a-phone-number/)
 */
class LetterCombinationsOfAPhoneNumber {
    fun letterCombinations(digits: String): List<String> {
        val res = ArrayList<String>()
        if (digits == "") return res
        val builder = StringBuilder()
        print(digits, 0, builder, res)
        return res
    }

    private fun print(digits: String, index: Int, builder: StringBuilder, res: ArrayList<String>) {
        if (index >= digits.length) {
            res.add(builder.toString())
            return
        }
        val digit = digits[index]
        when (digit) {
            '2' -> {
                builder.append('a')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('b')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('c')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
            }

            '3' -> {
                builder.append('d')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('e')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('f')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
            }

            '4' -> {
                builder.append('g')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('h')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('i')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
            }

            '5' -> {
                builder.append('j')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('k')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('l')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
            }

            '6' -> {
                builder.append('m')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('n')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('o')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
            }

            '7' -> {
                builder.append('p')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('q')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('r')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('s')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
            }

            '8' -> {
                builder.append('t')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('u')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('v')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
            }

            '9' -> {
                builder.append('w')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('x')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('y')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
                builder.append('z')
                print(digits, index + 1, builder, res)
                builder.delete(index, builder.length)
            }
        }
    }

    private val mapping = arrayListOf<String>(
        "",
        "",
        "abc",
        "def",
        "ghi",
        "jkl",
        "mno",
        "pqrs",
        "tuv",
        "wxyz"
    )

    private val ans = ArrayList<String>()

    fun letterCombinations2(digits: String): List<String> {
        if (digits.isEmpty()) return ans
        generateCombination(digits, "", 0)
        return ans
    }

    private fun generateCombination(digits: String, letters: String, index: Int) {
        if (digits.length == letters.length) {
            ans.add(letters)
            return
        }
        val currDigit = mapping[digits[index] - '0']
        for (i in currDigit.indices)
            generateCombination(digits, letters + currDigit[i], index + 1)
    }

}