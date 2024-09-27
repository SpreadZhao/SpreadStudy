package questions

/**
 * Link: [Boats to Save People](https://leetcode.com/problems/boats-to-save-people/)
 */
class BoatsToSavePeople {
    fun numRescueBoats(people: IntArray, limit: Int): Int {
        people.sortDescending()
        var i = 0
        var res = 0
        while (i < people.size) {
            if (i == people.lastIndex) return res + 1
            if (people[i] + people[i + 1] <= limit) {
                i += 2
            } else {
                i++
            }
            res++
        }
        return res
    }

    fun numRescueBoats2(people: IntArray, limit: Int): Int {
        people.sort()
        var i = 0;
        var j = people.lastIndex;
        var res = 0
        while (i <= j) {
            res++
            if (people[i] + people[j] <= limit) i++
            j--
        }
        return res
    }
}