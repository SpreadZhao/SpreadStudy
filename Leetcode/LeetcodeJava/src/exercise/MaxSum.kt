package exercise

class MaxSum {
    fun maxSum(arr: IntArray): Int {
        var b = arr[0]
        var sum = b
        for (i in 1 until arr.size) {
//            if(b > 0) b += arr[i]
//            else b = arr[i]
            b = if (b + arr[i] > arr[i]) b + arr[i] else arr[i]
            if (b > sum) sum = b
        }
        return sum
    }
}