package questions;


import model.ListNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Link: <a href="https://leetcode.com/problems/linked-list-random-node/">Linked List Random Node</a>
 * Reservoir Sampling
 */
public class LinkedListRandomNode {

    //ListNode head;

    // Wrap it with array list.
    List<Integer> array = new ArrayList<>();

    public LinkedListRandomNode(ListNode head) {
        while (head != null) {
            array.add(head.val);
            head = head.next;
        }
    }

    public int getRandom() {
        int index = new Random().nextInt(array.size());
        return array.get(index);
    }
}
