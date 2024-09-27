package questions

import kotlin.math.min

/**
 * Link:[Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays)
 */
class MedianOfTwoSortedArrays {

    /**
     * TODO: Make it non recursive and O(log(m + n))
     *
     * The Solution of O(m + n) is here: [Two pointers](https://leetcode.com/problems/median-of-two-sorted-arrays/solutions/3283266/best-java-solution-beats-100/)
     */
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val m = nums1.size;
        val n = nums2.size;
        val k = (m + n) / 2
        return if ((m + n) % 2 == 1) medianSelect(nums1, nums2, k + 1).toDouble()
        else {
            val x = medianSelect(nums1, nums2, k).toDouble()
            val y = medianSelect(nums1, nums2, k + 1)
            (x + y) / 2
        }
    }

    /**
     * 1 2 3
     * 4 5 6 7 8
     */

    private fun medianSelect(nums1: IntArray, nums2: IntArray, k: Int): Int {
        val m = nums1.size
        val n = nums2.size
        if (m > n) return medianSelect(nums2, nums1, k)
        if (m == 0) return nums2[k - 1]
        if (k == 1) return min(nums1[0], nums2[0])
        val i = min(m, k / 2)
        val j = k - i
        return if (nums1[i - 1] < nums2[j - 1]) medianSelect(nums1.copyOfRange(i, nums1.size), nums2, k - i)
        else medianSelect(nums1, nums2.copyOfRange(j, nums2.size), k - j)
    }
}