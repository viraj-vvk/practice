import java.util.Objects;

public class ZigZag {
    public static void main(String[] args) {
        System.out.println(zigZag("PAYPALISHIRING", 3));
        System.out.println(zigZag("PAYPALISHIRING", 5));
    }

    private static String zigZag(String s, int numRows) {
        int l = s.length();
        if (l < 2 || numRows == 1)
            return s;
        StringBuilder ans = new StringBuilder();
        Character[][] chars = new Character[numRows][l / 2];
        int c = 0, col = 0;
        while (c < l) {
            int r = 0;
            while (c < l && r < numRows) {
                chars[r++][col] = s.charAt(c++);
            }
            r--;
            while (c < l && r > 1) {
                chars[--r][++col] = s.charAt(c++);
            }
            col++;
        }
        for (Character[] aChar : chars) {
            for (Character character : aChar) {
                if (Objects.nonNull(character))
                    ans.append(character);
            }
        }
        return ans.toString();
    }
}
