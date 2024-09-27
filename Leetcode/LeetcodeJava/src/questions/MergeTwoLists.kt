package questions

import model.ListNode
import kotlin.math.min

/**
 * https://leetcode.cn/problems/merge-k-sorted-lists/description/
 */
class MergeTwoLists {
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        if (list1 == null) return list2
        if (list2 == null) return list1
        val newHead = ListNode(min(list1.`val`, list2.`val`))
        var newTail = newHead
        var p1 = if (list1.`val` > list2.`val`) {
            list1
        } else {
            list1.next
        }
        var p2 = if (list1.`val` > list2.`val`) {
            list2.next
        } else {
            list2
        }
        while (p1 != null || p2 != null) {
            if (p1 != null && p2 != null) {
                if (p1.`val` > p2.`val`) {
                    val temp = ListNode(p2.`val`)
                    newTail.next = temp
                    newTail = temp
                    p2 = p2.next
                } else {
                    val temp = ListNode(p1.`val`)
                    newTail.next = temp
                    newTail = temp
                    p1 = p1.next
                }
            } else if (p1 == null) {
                val temp = ListNode(p2.`val`)
                newTail.next = temp
                newTail = temp
                p2 = p2.next
            } else {
                val temp = ListNode(p1.`val`)
                newTail.next = temp
                newTail = temp
                p1 = p1.next
            }
        }
        return newHead
    }

    fun mergeTwoLists2(list1: ListNode?, list2: ListNode?): ListNode? = if (list1 == null) list2
    else if (list2 == null) list1
    else if (list1.`val` < list2.`val`) {
        list1.next = mergeTwoLists2(list1.next, list2)
        list1
    } else {
        list2.next = mergeTwoLists2(list1, list2.next)
        list2
    }

    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) return null
        if (lists.size == 1) return lists[0]
        if (lists.size == 2) return mergeTwoLists2(lists[0], lists[1])
        var res = mergeTwoLists2(lists[0], lists[1])
        for (i in 2 until lists.size) {
            res = mergeTwoLists2(res, lists[i])
        }
        return res
    }
}