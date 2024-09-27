package questions;

/**
 * Link: <a href="https://leetcode.com/problems/string-to-integer-atoi/">String to Integer</a>
 */
public class StringToInteger {
    public int myAtoi(String s) {
        int i = 0;
        long res = 0;
        boolean isNegative = false;
        boolean enterDigit = false;
        boolean leaveDigit = false;
        while (i < s.length()) {
            if (leaveDigit) break;
            char ch = s.charAt(i);
            switch (ch) {
                case '+', '-' -> {
                    if (enterDigit) leaveDigit = true; // s2 is 0, s3 is -13
                    enterDigit = true;      // s5 is 0
                    if (ch == '-' && !leaveDigit) isNegative = true; // s4 is 123
                }
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    enterDigit = true;      // s2 is 0
                    res = res * 10 + ch - '0';
                    if (res > Integer.MAX_VALUE + 1L && isNegative) {
                        res = Integer.MIN_VALUE;
                        leaveDigit = true;
                    } else if (res > Integer.MAX_VALUE && !isNegative) {
                        res = Integer.MAX_VALUE;
                        leaveDigit = true;
                    }
                }
                case ' ' -> {
                    if (enterDigit) leaveDigit = true;
                }
                default -> leaveDigit = true;
            }
            i++;
        }
        if (isNegative) res = -res;
        return (int) res;
    }
}
