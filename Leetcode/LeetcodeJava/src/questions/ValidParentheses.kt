package questions

import java.util.*

/**
 * Link: [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/)
 */
class ValidParentheses {
    fun isValid(s: String): Boolean {
        val stack = Stack<Char>()
        for (ch in s) {
            when (ch) {
                '(', '[', '{' -> stack.push(ch)
                ')', ']', '}' -> if (!match(stack, ch)) return false
            }
        }
        return stack.empty()
    }

    private fun match(stack: Stack<Char>, close: Char): Boolean {
        if (stack.empty()) return false
        return when (close) {
            ')' -> stack.pop() == '('
            ']' -> stack.pop() == '['
            '}' -> stack.pop() == '{'
            else -> false
        }
    }

    fun isValid2(s: String): Boolean {
        var stack = 0L
//        if(s.length > 100) return true
        for (ch in s) {
            when (ch) {
                '(' -> stack = (stack shl 2) + 1
                '[' -> stack = (stack shl 2) + 2
                '{' -> stack = (stack shl 2) + 3
                '}' -> if (stack and 3 != 3L) return false else stack = stack shr 2
                ']' -> if (stack and 3 != 2L) return false else stack = stack shr 2
                ')' -> if (stack and 3 != 1L) return false else stack = stack shr 2
            }
        }
        return stack == 0L
    }
}