package questions

/**
 * Link: [Search Insert Position](https://leetcode.com/problems/search-insert-position/)
 */
class SearchInsertPosition {
    fun searchInsert(nums: IntArray, target: Int): Int {
        var mid = nums.size / 2
        mid = if (nums.isEmpty()) return 0 else if (nums[mid] == target) return mid else if (nums[mid] > target) {
            val left = nums.copyOfRange(0, mid)
            searchInsert(left, target)
        } else {
            val right = nums.copyOfRange(mid + 1, nums.size)
            mid + searchInsert(right, target) + 1
        }
        return mid
    }
}