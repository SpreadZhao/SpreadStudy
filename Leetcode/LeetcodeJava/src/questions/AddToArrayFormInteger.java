package questions;

import java.util.LinkedList;
import java.util.List;

/**
 * Link: <a href="https://leetcode.com/problems/add-to-array-form-of-integer/">Add to Array-Form of Integer</a>
 */
public class AddToArrayFormInteger {

    // Constraint: INT_MAX
    public List<Integer> addToArrayForm(int[] num, int k) {
        int val = 0;
        for (int i = num.length - 1; i >= 0; i--) {
            val += num[i] * Math.pow(10, num.length - 1 - i);
        }
        val += k;
        List<Integer> res = new LinkedList<>();
        while (val > 0) {
            res.add(0, val % 10);
            val /= 10;
        }
        return res;
    }

    public List<Integer> addToArrayForm2(int[] num, int k) {
        List<Integer> res = new LinkedList<>();
        int i = num.length - 1, carry = 0;
        while (k > 0 || i >= 0) {
            int n = i < 0 ? (k % 10) + carry : (k % 10) + num[i] + carry;
//            if(n >= 10){
//                res.add(0, n % 10);
//                carry = 1;
//            }else{
//                res.add(0, n);
//                carry = 0;
//            }
            res.add(0, n % 10);
            carry = n / 10;
            k /= 10;
            i--;
        }
        if (carry == 1) res.add(0, 1);
        return res;
    }
}
