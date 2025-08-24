import java.util.*;

public class KMostFrequent {
    public static void main(String[] args) {
        String[] strings = {"hello", "world", "i", "love", "coding", "i", "love", "java"};
        System.out.println(findMostFrequent(strings, 4));
        System.out.println(findMostFrequent(strings, 5));
        System.out.println(findMostFrequent(strings, 6));
    }

    private static List<String> findMostFrequent(String[] strings, int k) {
        if (k < 1) {
            throw new IllegalArgumentException("Invalid k: " + k);
        }
        if (strings == null || strings.length == 0) {
            return Collections.emptyList();
        }
        if (strings.length == 1) {
            return Arrays.asList(strings);
        }
        Map<String, Integer> stringFrequencies = new HashMap<>();
        for (String string : strings) {
            stringFrequencies.put(string, stringFrequencies.getOrDefault(string, 1) + 1);
        }
        return stringFrequencies.entrySet().stream()
                .sorted((n1, n2) -> Objects.equals(n1.getValue(), n2.getValue())
                        ? n1.getKey().compareTo(n2.getKey())
                        : Integer.compare(n2.getValue(), n1.getValue()))
                .limit(k).map(Map.Entry::getKey).toList();
    }
}
