package questions;

/**
 * Link: <a href="https://leetcode.com/problems/integer-to-roman/">Integer to Roman</a>
 */
public class IntegertoRoman {
    public String intToRoman(int num) {
        int every_digit;
        int level = 1;
        StringBuilder builder = new StringBuilder();
        while (num > 0) {
            every_digit = num % 10;
            builder.insert(0, getRomanByLevel(every_digit, level++));
            num /= 10;
        }
        return builder.toString();
    }

    public String getRomanByLevel(int num, int level) {
        switch (level) {
            case 1 -> {
                return switch (num) {
                    case 1 -> "I";
                    case 2 -> "II";
                    case 3 -> "III";
                    case 4 -> "IV";
                    case 5 -> "V";
                    case 6 -> "VI";
                    case 7 -> "VII";
                    case 8 -> "VIII";
                    case 9 -> "IX";
                    case 0 -> "";
                    default -> null;
                };
            }
            case 2 -> {
                return switch (num) {
                    case 1 -> "X";
                    case 2 -> "XX";
                    case 3 -> "XXX";
                    case 4 -> "XL";
                    case 5 -> "L";
                    case 6 -> "LX";
                    case 7 -> "LXX";
                    case 8 -> "LXXX";
                    case 9 -> "XC";
                    case 0 -> "";
                    default -> null;
                };
            }
            case 3 -> {
                return switch (num) {
                    case 1 -> "C";
                    case 2 -> "CC";
                    case 3 -> "CCC";
                    case 4 -> "CD";
                    case 5 -> "D";
                    case 6 -> "DC";
                    case 7 -> "DCC";
                    case 8 -> "DCCC";
                    case 9 -> "CM";
                    case 0 -> "";
                    default -> null;
                };
            }
            case 4 -> {
                return switch (num) {
                    case 1 -> "M";
                    case 2 -> "MM";
                    case 3 -> "MMM";
                    default -> null;
                };
            }
            default -> {
                return null;
            }
        }

    }
}
