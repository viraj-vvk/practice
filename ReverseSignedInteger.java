public class ReverseSignedInteger {
    public static void main(String[] args) {
        System.out.println(reverse(123));
        System.out.println(reverse(-123));
        System.out.println(reverse(120));
        System.out.println(reverse(Integer.MAX_VALUE));
        System.out.println(reverse(Integer.MIN_VALUE));
        System.out.println(reverse(1534236469));
    }

    private static int reverse(int x) {
        if (x > -10 && x < 10)
            return x;
        int prev, rev = 0;
        while (x != 0) {
            prev = rev;
            rev = rev * 10 + x % 10;
            if (rev / 10 != prev)
                return 0;
            x /= 10;
        }
        return rev;
    }
}
