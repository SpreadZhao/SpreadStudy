import test.Test.listIntersection
import test.Test.listIntersection2
import test.Test.permutation
import test.Test.reverseListBetween
import test.TestAlgo.maxHeap
import test.TestExercise.printThread
import test.TestExercise.shortest2


fun main(args: Array<String>) {

    val stime = System.currentTimeMillis()


//        Test.romanInteger("MCMXCIV");
//        longestCommonPrefix();

    //        Test.sum();
//        Test.addTwoNumbers();
//        longestSubstring();
//        Test.longestPalindromicSubstring();
//        Test.zigzagConversion();
//        Test.shuffleTheArray();
//        reverseInteger()
//        Test.stringToInteger();
//        Test.palindromeNumber();
//        Test.integerToRoman();
//        Test.addBinary();
//        Test.addToArrayFormOfInteger();
//        Test.maxDepthOfBianryTree();
//        Test.regularExpressionMatching();
//        Test.searchInsertPosition();
//        singleElementInASortedArray()
//        Test.containerWithMostWater();
//        sumClosest()
//        Test.sltoBst();
//        Test.symmetricTree();
//        Test.sumRootToLeafNumbers();
//        Test.trie();
//        Test.wordDict();
//        Test.canPlaceFlowers();
//        TestExercise.testGraph();
//        Test.mimimunScore();
//        connectNetwork()
//        unreachableNodes()
//        spellsAndPotions()
//        boatsToSavePeople()
//        medianOfTwoSortedArrays()
//        minimizeMaxmimumOfArray()
//        validParentheses()
//        simplyfyPath()
//        matrixChainProduct()
//        longestCommonSubsequence()
//        longestCommonSubstring()
//        maxSum()
//        longestPalindromicSubsequence()
//        formString()
//        lcoaPN()
//        fractionalKS()
//        zeroOneKS()
//        schedule()
//        shortest()
//        differenceOfTwoArrays()
//        shortest2()
//        queen8()
//        insertionSort()
//        mergeSort()
//        maximumSubarray()
//    maxHeap()
//        quickSort()
//        countingSort()
//        rodCutting()

//        spiralMatrix()


//        while (true);
//        reverseList()
//        reverseListBetween()
//        printThread()
//    permutation()
//    listIntersection()
    listIntersection2()

    val etime = System.currentTimeMillis()
    println("time: " + (etime - stime))
}


//fun main(args: Array<String>) {
//    val num1 = "34567"
//    val num2 = "234"
//    println(plusLargeNumber(num1, num2))
//}
//
//fun plusLargeNumber(num1: String, num2: String): String {
//    // 3 4 5 6 7
//    //     2 3 4
//    var i = num1.lastIndex
//    var j = num2.lastIndex
//    var carry = 0
//    val res = StringBuilder()
//    while (i >= 0 || j >= 0) {
//        val a = if (i >= 0) num1[i].toInt() else 0
//        val b = if (j >= 0) num2[j].toInt() else 0
//        val tempRes = (a + b + carry) % 10
//        carry = (a + b) / 10
//        res.insert(0, tempRes.toString())
//        if (i >= 0) i--
//        if (j >= 0) j--
//    }
//    return res.toString()
//}