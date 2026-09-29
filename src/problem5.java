import java.math.BigInteger;

public class Problem5 {

    public String multBrute(String A, String B) {
        if (A == null || B == null || A.equals("0") || B.equals("0")) {
            return "0";
        }

        boolean isNegative = (A.charAt(0) == '-') ^ (B.charAt(0) == '-');
        if (A.charAt(0) == '-') A = A.substring(1);
        if (B.charAt(0) == '-') B = B.substring(1);

        int n = A.length();
        int m = B.length();
        int[] result = new int[n + m];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                int mul = (A.charAt(i) - '0') * (B.charAt(j) - '0');
                int sum = mul + result[i + j + 1];

                result[i + j + 1] = sum % 10;
                result[i + j] += sum / 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int p : result) {
            if (!(sb.length() == 0 && p == 0)) {
                sb.append(p);
            }
        }

        if (sb.length() == 0) return "0";
        if (isNegative) sb.insert(0, "-");

        return sb.toString();
    }

    public String multSmart(String A, String B) {
        if (A == null || B == null || A.equals("0") || B.equals("0")) {
            return "0";
        }

        boolean isNegative = (A.charAt(0) == '-') ^ (B.charAt(0) == '-');
        if (A.charAt(0) == '-') A = A.substring(1);
        if (B.charAt(0) == '-') B = B.substring(1);

        String result = karatsuba(A, B);

        if (isNegative && !result.equals("0")) {
            result = "-" + result;
        }

        return result;
    }

    private String karatsuba(String num1, String num2) {
        int n1 = num1.length();
        int n2 = num2.length();

        if (n1 < 10 || n2 < 10) {
            return multBrute(num1, num2);
        }

        int n = Math.max(n1, n2);
        int half = n / 2;

        String a = n1 > half ? num1.substring(0, n1 - half) : "0";
        String b = n1 > half ? num1.substring(n1 - half) : num1;
        String c = n2 > half ? num2.substring(0, n2 - half) : "0";
        String d = n2 > half ? num2.substring(n2 - half) : num2;

        String ac = karatsuba(a, c);
        String bd = karatsuba(b, d);

        String aPlusB = addStrings(a, b);
        String cPlusD = addStrings(c, d);

        String abcd = karatsuba(aPlusB, cPlusD);

        String middle = subtractStrings(subtractStrings(abcd, ac), bd);

        String step1 = appendZeros(ac, 2 * half);
        String step2 = appendZeros(middle, half);

        return addStrings(addStrings(step1, step2), bd);
    }

    private String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;

        while (i >= 0 || j >= 0 || carry != 0) {
            int sum = carry;
            if (i >= 0) sum += num1.charAt(i--) - '0';
            if (j >= 0) sum += num2.charAt(j--) - '0';
            sb.append(sum % 10);
            carry = sum / 10;
        }

        return sb.reverse().toString();
    }

    private String subtractStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, borrow = 0;

        while (i >= 0) {
            int sub = (num1.charAt(i--) - '0') - borrow;
            if (j >= 0) {
                sub -= (num2.charAt(j--) - '0');
            }
            if (sub < 0) {
                sub += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            sb.append(sub);
        }

        while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.reverse().toString();
    }

    private String appendZeros(String str, int count) {
        if (str.equals("0")) return "0";
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < count; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Problem5 solver = new Problem5();
        String A = "12345678987654321";
        String B = "98765432123456789";

        System.out.println("Brute Result: " + solver.multBrute(A, B));
        System.out.println("Smart Result: " + solver.multSmart(A, B));

        BigInteger a = new BigInteger(A);
        BigInteger b = new BigInteger(B);
        System.out.println("BigInteger:   " + a.multiply(b));
    }
}