package questions

/**
 * Link: [Kids With the Greatest Number of Candies](https://leetcode.com/problems/kids-with-the-greatest-number-of-candies/)
 */

class KidsWithTheGreatestNumberOfCandies {
    fun kidsWithCandies(candies: IntArray, extraCandies: Int): List<Boolean> {
        var max = candies[0]
        for (i in 1 until candies.size) {
            if (candies[i] > max) max = candies[i]
        }
        val res = ArrayList<Boolean>()
        for (i in candies.indices) {
            res.add(candies[i] + extraCandies >= max)
        }
        return res
    }
}