package questions

import model.ListNode

/**
 * https://leetcode.cn/problems/reverse-linked-list/
 */
class ReverseList {
    fun reverseList(head: ListNode?): ListNode? {
        // 构造新结点不能直接赋值，kotlin里这样两个引用指向一个内存，并没有开辟新空间
        var p = head?.next
        if (head == null) return null
        var res = ListNode(head.`val`, null)
        while (p != null) {
            val temp = ListNode(p.`val`, null)
            temp.next = res
            res = temp
            p = p.next
        }
        return res
    }

    fun reverseList2(head: ListNode?): ListNode? {
        return null
    }

    fun reverseBetween2(head: ListNode?, left: Int, right: Int): ListNode? {
        val list1 = if (left == 1) null else subList(head, 1, left - 1)
        val list2 = reverseList(subList(head, left, right))
        val size = calSize(head)
        val list3 = if (size == right) null else subList(head, right + 1, size)
        return mergeList(mergeList(list1, list2), list3)
    }

    private fun calSize(head: ListNode?) = if (head == null) 0 else {
        var len = 1
        var p = head
        while (p!!.next != null) {
            len++
            p = p.next
        }
        len
    }

    private fun subList(head: ListNode?, left: Int, right: Int): ListNode? {
        if (head == null) return null
        var p = head
        for (i in 1 until left) {
            if (p != null) p = p.next
        }
        if (p == null) return null
        val newHead = ListNode(p.`val`)
        var resTail = newHead
        for (i in left until right) {
            if (p != null) {
                p = p.next
                val newNode = ListNode(p.`val`)
                resTail.next = newNode
                resTail = newNode
            }
        }
        return newHead
    }

    private fun mergeList(head1: ListNode?, head2: ListNode?) = if (head1 == null && head2 == null) {
        null
    } else if (head1 == null) {
        head2
    } else if (head2 == null) {
        head1
    } else {
        var head1Tail = head1
        while (head1Tail!!.next != null) {
            head1Tail = head1Tail.next
        }
        head1Tail.next = head2
        head1
    }


    fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
        if (head == null) return null
        if (head.next == null) return head
        var res1Tail = ListNode(head.`val`)
        val resHead = res1Tail
        var i = 2;
        var p = head.next
        while (i < left) {
            val temp = ListNode(p.`val`)
            res1Tail.next = temp
            res1Tail = res1Tail.next
            p = p.next
            i++
        }
        var res2Head: ListNode? = null
        var res2Tail: ListNode? = null
        if (i != 2) {
            res2Head = ListNode(p.`val`)
            res2Tail = res2Head
            p = p.next
            i++
        }
        while (i <= right) {
            val temp = ListNode(p.`val`)
            temp.next = res2Head
            res2Head = temp
            p = p.next
            i++
        }
        if (p != null) {
            var res3Tail = ListNode(p.`val`)
            val res3Head = res3Tail
            p = p.next
            while (p != null) {
                val temp = ListNode(p.`val`)
                res3Tail.next = temp
                res3Tail = res3Tail.next
                p = p.next
            }
            res2Tail?.next = res3Head
        }
        res1Tail.next = res2Head
        return resHead
    }
}