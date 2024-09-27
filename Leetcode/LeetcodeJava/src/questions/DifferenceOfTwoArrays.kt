package questions

/**
 * Link: [Find the Difference of Two Arrays](https://leetcode.com/problems/find-the-difference-of-two-arrays/)
 */

class DifferenceOfTwoArrays {
    fun findDifference(nums1: IntArray, nums2: IntArray): List<List<Int>> {
        nums1.sort(); nums2.sort()
        val res = ArrayList<ArrayList<Int>>()
        res.add(ArrayList())
        res.add(ArrayList())
        for (num in nums1) {
            if (!exist(num, nums2)) if (!res[0].contains(num)) res[0].add(num)
        }
        for (num in nums2) {
            if (!exist(num, nums1)) if (!res[1].contains(num)) res[1].add(num)
        }
        return res
    }

    private fun exist(num: Int, arr: IntArray): Boolean {
        var i = 0;
        var j = arr.lastIndex
        while (i < j) {
            val mid = (i + j) / 2
            if (arr[mid] == num) return true
            if (arr[mid] > num) j = mid - 1
            else i = mid + 1
        }
        return arr[(i + j) / 2] == num
    }

    fun findDifference2(nums1: IntArray, nums2: IntArray): List<List<Int>> {
        return listOf(existOnlyInFirstArr(nums1, nums2), existOnlyInFirstArr(nums2, nums1))
    }

    private fun existOnlyInFirstArr(nums1: IntArray, nums2: IntArray): List<Int> {
        val onlyInFirst = HashSet<Int>()
        // store nums2's elems in a hashset
        val set2 = HashSet<Int>()
        for (num in nums2) {
            set2.add(num)
        }
        for (num in nums1) {
            if (!set2.contains(num)) onlyInFirst.add(num)
        }
        return onlyInFirst.toList()
    }

    /* Complexity:
     * Time (M+N) and Space O(M+N) where M and N are the size of nums1 and nums2;
     */
    fun findDifference3(nums1: IntArray, nums2: IntArray): List<List<Int>> {
        val set1 = nums1.toSet()
        val set2 = nums2.toSet()
        return listOf(
            set1.filter { it !in set2 },
            set2.filter { it !in set1 }
        )
    }

}