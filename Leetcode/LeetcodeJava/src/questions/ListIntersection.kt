package questions

import model.ListNode

/**
 * https://leetcode.cn/problems/intersection-of-two-linked-lists/
 */
class ListIntersection {
    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        if (headA == null || headB == null) return null
        var p1 = headA
        var p2 = headB
        while (p1 != p2) {
            // 为什么不能p1.next == null ???
            p1 = if (p1 == null) headB else p1.next
            p2 = if (p2 == null) headA else p2.next
        }
        return p1
    }
}