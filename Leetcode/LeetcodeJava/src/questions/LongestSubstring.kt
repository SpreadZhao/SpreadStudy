package questions

import java.util.*
import kotlin.math.max

/**
 * Link: [Longest Substring Without Repeated Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/description/)
 */
class LongestSubstring {
    fun lengthOfLongestSubstring(s: String): Int {
        if (s == "") return 0
        val chs: MutableSet<Char> = HashSet()
        val lens: MutableList<Int> = ArrayList()
        var i = 0
        while (i < s.length) {
            val ch = s[i]
            if (!chs.contains(ch)) {
                chs.add(ch)
            } else {
                lens.add(chs.size)
                i -= chs.size
                chs.clear()
            }
            i++
        }
        if (lens.isEmpty() || chs.isNotEmpty()) { // Any else branch in for loop is not executed
            lens.add(chs.size)
        }
        lens.sort()
        return lens[lens.size - 1]
    }

    fun lengthOfLongestSubstring2(s: String): Int {
        if (s.length <= 1) return s.length
        var i = 0
        var j: Int
        val sub = HashMap<Char, Int>()
        var maxLen = 0
        while (i < s.length) {    // abcabcbb
            sub[s[i]] = 1
            j = i + 1
            while (j < s.length) {
                if (!sub.contains(s[j])) {
                    sub[s[j]] = 1
                    j++
                } else break
            }
            maxLen = max(maxLen, j - i)
            i++
            sub.clear()
        }
        return maxLen
    }

    fun lengthOfLongestSubstring3(s: String): Int {
        val sub = HashMap<Char, Int>()
        var maxLen = 0
        var i = 0
        var j = 0
        while (j < s.length) {
            val ch = s[j]
            sub[ch] = sub.getOrDefault(ch, 0) + 1
            while (sub[ch]!! > 1) {
//                sub[ch] = sub[ch]?.minus(1) ?: 0
                val l = s[i]
                sub[l] = sub[l]?.minus(1) ?: 0
                i++
            }
            maxLen = max(maxLen, j - i + 1)
            j++
        }
        return maxLen
    }


    fun lengthOfLongestSubstring4(s: String): Int {
        val sub = HashMap<Char, Int>()
        var i = 0;
        var j = 0;
        var maxLen = 0        // abcabcbb
        while (j < s.length) {
            val ch = s[j]
            if (!sub.contains(ch)) {
                sub[ch] = j
            } else {
                maxLen = max(maxLen, j - i)
                i = sub[ch]!!.plus(1)
            }
            j++
        }
        return maxLen
    }

    fun lengthOfLongestSubstring5(s: String): Int {
        val sub = HashMap<Char, Int>()
        var i = 0;
        var j = 0;
        var maxLen = 0        // abcabcbb
        while (j < s.length) {
            val ch = s[j]
            if (sub.contains(ch)) {
//                i = max(i, sub[ch]!!.plus(1))
                i = sub[ch]!!.plus(1)
                sub[ch] = j
                maxLen = max(maxLen, j - i + 1)
                j++
            } else {
                sub[ch] = j
                maxLen = max(maxLen, j - i + 1)
                j++
            }
        }
        return maxLen
    }

    fun lengthOfLongestSubstring6(s: String): Int {
        val sub = HashMap<Char, Int>()
        var i = 0;
        var j = 0;
        var maxLen = 0        // abcabcbb
        while (j < s.length) {
            val ch = s[j]
            if (sub.contains(ch)) {
                i = max(i, sub[ch]!!)
            }
            sub[ch] = j + 1
            maxLen = max(maxLen, j - i + 1)
            j++
        }
        return maxLen
    }

}