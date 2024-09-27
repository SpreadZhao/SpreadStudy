package questions

/**
 * Link: [Single Element In A Sorted Array](https://leetcode.com/problems/single-element-in-a-sorted-array/)
 *
 *
 * Question: Why sorted?
 *
 *
 * Better Solution: [Day 52 || Binary Search || Easiest Beginner Friendly Sol](https://leetcode.com/problems/single-element-in-a-sorted-array/solutions/3212178/day-52-binary-search-easiest-beginner-friendly-sol/)
 */
class SingleElementInASortedArray {
    fun singleNonDuplicate(nums: IntArray): Int {
        if (nums.size == 1) return nums[0]
        var i = 0
        while (i < nums.size - 1) {
            i += if (nums[i] == nums[i + 1]) 2 else return nums[i]
        }
        return nums[i]
    }

    fun singleNonDuplicate2(nums: IntArray): Int {
        return core(nums, 0, nums.lastIndex)
    }

    private fun core(nums: IntArray, low: Int, high: Int): Int {
        val mid = (low + high) / 2
        if (low < high) {
            if (mid % 2 == 1) {
                if (nums[mid] == nums[mid + 1]) return core(nums, low, mid - 1)
                else if (nums[mid] == nums[mid - 1]) return core(nums, mid + 1, high)
            } else {
                if (nums[mid] == nums[mid - 1]) return core(nums, low, mid - 2)
                else if (nums[mid] == nums[mid + 1]) return core(nums, mid + 2, high)
            }
        }
        return nums[mid]
    }

    fun singleNonDuplicate3(nums: IntArray): Int {
        var low = 0;
        var high = nums.lastIndex
        while (low < high) {
            val mid = (low + high) / 2
            if (mid % 2 == 1) {
                if (nums[mid] == nums[mid + 1]) high = mid - 1
                else if (nums[mid] == nums[mid - 1]) low = mid + 1
                else return nums[mid]
            } else {
                if (nums[mid] == nums[mid - 1]) high = mid - 2
                else if (nums[mid] == nums[mid + 1]) low = mid + 2
                else return nums[mid]
            }
        }
        return nums[low]
    }
}