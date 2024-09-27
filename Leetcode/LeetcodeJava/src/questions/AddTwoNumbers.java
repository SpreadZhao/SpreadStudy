package questions;

import model.ListNode;

/**
 * Link: <a href="https://leetcode.com/problems/add-two-numbers/">Add Two Numbers</a>
 */
public class AddTwoNumbers {
    public static void print(ListNode l) {
        while (l.next != null) {
            System.out.print(l.val + " ");
            l = l.next;
        }
        System.out.println(l.val);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode();
        ListNode p = res, p1 = l1, p2 = l2;
        boolean carry = false;
        int temp;
        while (p1 != null || p2 != null) {
            if (p1 == null) {
                temp = calTemp(0, p2.val, carry);
                if (temp >= 10) {
                    carry = true;
                    p.val = temp % 10;
                } else {
                    p.val = temp;
                    carry = false;
                }
                p2 = p2.next;
            } else if (p2 == null) {
                temp = calTemp(p1.val, 0, carry);
                if (temp >= 10) {
                    carry = true;
                    p.val = temp % 10;
                } else {
                    p.val = temp;
                    carry = false;
                }
                p1 = p1.next;
            } else {
                temp = calTemp(p1.val, p2.val, carry);
                if (temp >= 10) {
                    carry = true;
                    p.val = temp % 10;
                } else {
                    p.val = temp;
                    carry = false;
                }
                p1 = p1.next;
                p2 = p2.next;
            }
            if (p1 != null || p2 != null) {
                p.next = new ListNode();
                p = p.next;
            }
        }
        if (carry) {
            p.next = new ListNode(1);
        }
        return res;
    }

    public int calTemp(int num1, int num2, boolean carry) {
        int temp = num1 + num2;
        if (carry) temp += 1;
        return temp;
    }
}
