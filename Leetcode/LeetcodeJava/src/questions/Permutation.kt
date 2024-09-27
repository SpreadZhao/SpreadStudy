package questions

/**
 * https://leetcode.cn/problems/permutations/description/ance
 */
class Permutation {
    fun permute(nums: IntArray): List<List<Int>> {
        val res = ArrayList<List<Int>>()
        val list = ArrayList<Int>()
        backtrack(nums, list, res)
        return res
    }



    fun backtrack(nums: IntArray, list: MutableList<Int>, res: MutableList<List<Int>>) {
        if (list.size == nums.size) {
            res.add(list.toList())
            return
        }
        for (num in nums) {
            if (!list.contains(num)) {
                list.add(num)
                backtrack(nums, list, res)
                list.removeLast()
            }
        }
    }
}