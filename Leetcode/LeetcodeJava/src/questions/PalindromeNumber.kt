package questions

import java.util.*

/**
 * Link: [Palindrome Number](https://leetcode.com/problems/palindrome-number/)
 */
class PalindromeNumber {
    fun isPalindrome(x: Int): Boolean {
        val s = x.toString()
        var i = 0
        var j = s.length - 1
        while (i < j) {
            if (s[i] != s[j]) return false
            i++
            j--
        }
        return true
    }

    // Follow up: Could you solve it without converting the integer to a string?
    fun isPalindrome2(x: Int): Boolean {
        var x = x
        if (x < 0) return false
        val nums: MutableList<Int> = LinkedList() // ArrayList is a little bit faster
        while (x > 0) {
            nums.add(x % 10)
            x /= 10
        }
        var i = 0
        var j = nums.size - 1
        while (i < j) {
            if (nums[i] != nums[j]) return false
            i++
            j--
        }
        return true
    }

    fun isPalindrome3(x: Int): Boolean {
        var xx = x
        if (xx < 0 || xx % 10 == 0 && xx != 0) return false
        var r = 0
        while (xx > r) {
            r = r * 10 + xx % 10
            xx /= 10
        }
        return xx == r || xx == r / 10
    }
}