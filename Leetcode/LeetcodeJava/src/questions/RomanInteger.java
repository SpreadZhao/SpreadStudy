package questions;

/**
 * Link: <a href="https://leetcode.com/problems/roman-to-integer/description/">Roman Integer</a>
 * Particular instance:
 * IV: 4, IX: 9
 * XL: 40, XC: 90
 * CD: 400, CM: 900
 */
public class RomanInteger {
    public int romanToInt(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i == s.length() - 1) {
                switch (s.charAt(i)) {
                    case 'I' -> res += 1;
                    case 'V' -> res += 5;
                    case 'X' -> res += 10;
                    case 'L' -> res += 50;
                    case 'C' -> res += 100;
                    case 'D' -> res += 500;
                    case 'M' -> res += 1000;
                }
                break;
            }
            switch (s.charAt(i)) {
                case 'I' -> {
                    if (s.charAt(i + 1) == 'V') {
                        res += 4;
                        ++i;
                    } else if (s.charAt(i + 1) == 'X') {
                        res += 9;
                        ++i;
                    } else {
                        res += 1;
                    }
                }
                case 'V' -> res += 5;
                case 'X' -> {
                    if (s.charAt(i + 1) == 'L') {
                        res += 40;
                        ++i;
                    } else if (s.charAt(i + 1) == 'C') {
                        res += 90;
                        ++i;
                    } else {
                        res += 10;
                    }
                }
                case 'L' -> res += 50;
                case 'C' -> {
                    if (s.charAt(i + 1) == 'D') {
                        res += 400;
                        ++i;
                    } else if (s.charAt(i + 1) == 'M') {
                        res += 900;
                        ++i;
                    } else {
                        res += 100;
                    }
                }
                case 'D' -> res += 500;
                case 'M' -> res += 1000;
            }
        }
        return res;
    }
}
