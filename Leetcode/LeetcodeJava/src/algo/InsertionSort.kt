package algo

class InsertionSort {
    fun sort(array: IntArray) {
        for (i in 1 until array.size) {
            val key = array[i];
            var j = i - 1
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j]
                j--
            }
            array[j + 1] = key
        }
    }
}