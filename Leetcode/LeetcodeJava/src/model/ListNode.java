package model;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static void printList(ListNode head) {
        ListNode p = head;
        while (p != null) {
            System.out.printf("%d ", p.val);
            p = p.next;
        }
    }
}
