package questions

/**
 * Link: [Pairs of Spells and Potions](https://leetcode.com/problems/successful-pairs-of-spells-and-potions/)
 */

class SpellsAndPotions {

    // Time limit exceeded
    fun successfulPairs(spells: IntArray, potions: IntArray, success: Long): IntArray {
        val pairs = IntArray(spells.size)
        for (i in pairs.indices) {
            for (j in potions.indices) {
                val temp: Long = spells[i].toLong() * potions[j]
                if (temp >= success) pairs[i]++
            }
        }
        return pairs
    }

    fun successfulPairs2(spells: IntArray, potions: IntArray, success: Long): IntArray {
        val pairs = IntArray(spells.size)
        potions.sort()
//        spells.sort()
        for (i in pairs.indices) {
            var low = 0;
            var high = potions.lastIndex;
            var mid = 0;
            val spell = spells[i]
            while (low < high) {
                mid = (low + high) / 2
                val product = potions[mid].toLong() * spell
                if (product >= success) high = mid - 1
                else low = mid + 1
            }
//            if(low == potions.lastIndex){
//                if(potions[low].toLong() * spell >= success) pairs[i]++
//            }else if(low == 0){
//                if(potions[low].toLong() * spell >= success) pairs[i] += potions.size
//                else if(potions[low + 1].toLong() * spell >= success) pairs[i] += potions.size - 1
//            }else{
//                val product = potions[low].toLong() * spell
//                if(product < success){
//                    val product_n = potions[low + 1].toLong() * spell
//                    if(product_n >= success) pairs[i] += potions.size - low - 1
//                }else{
//                    val product_p = potions[low - 1].toLong() * spell
//                    if(product_p < success) pairs[i] += potions.size - low
//                }
//            }
            if (potions[low].toLong() * spell >= success) pairs[i] += potions.size - low
            else pairs[i] += potions.size - low - 1
        }
        return pairs
    }

}