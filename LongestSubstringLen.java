public class LongestSubstringLen {
    public static void main(String[] args) {
        System.out.println(longestSubstringLen("abcabcbb"));
        System.out.println(longestSubstringLen("bbbb"));
        System.out.println(longestSubstringLen("pwwkew"));
    }

    private static int longestSubstringLen(String s) {
        int m = 1;
        for (int i = 0; i < s.length() - m; i++) {
            int c = 1;
            for (int j = i + 1; j < s.length(); j++) {
                if (s.substring(i, j).indexOf(s.charAt(j)) > -1) {
                    break;
                }
                c++;
            }
            m = Math.max(c, m);
        }
        return m;
    }
}
