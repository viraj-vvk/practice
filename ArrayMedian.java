import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class ArrayMedian {
    public static void main(String[] args) {
        int[] nums1 = {1, 3}, nums2 = {2};
        System.out.println(findMedianSortedArrays(nums1, nums2));
        nums1 = new int[]{1, 2};
        nums2 = new int[]{3, 4};
        System.out.println(findMedianSortedArrays(nums1, nums2));
        nums1 = new int[]{1, 2, 3, 4, 5};
        nums2 = new int[]{2, 4, 6, 8, 10};
        System.out.println(findMedianSortedArrays(nums1, nums2));
        nums1 = new int[]{1, 2, 3, 4, 5};
        nums2 = new int[]{};
        System.out.println(findMedianSortedArrays(nums1, nums2));
        System.out.println(findMedianSortedArrays(new int[0], new int[0]));
    }

    private static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1 = nums1.length, l2 = nums2.length;
        if (l1 == 0 && l2 == 0)
            return 0;
        int i = 0, j = 0, l = l1 + l2, m = l / 2 + 1;
        Queue<Integer> nums = new PriorityQueue<>(m, Comparator.reverseOrder());
        while (nums.size() < m && i < l1 && j < l2) {
            nums.offer(nums1[i] < nums2[j] ? nums1[i++] : nums2[j++]);
        }
        while (nums.size() < m && i < l1)
            nums.offer(nums1[i++]);
        while (nums.size() < m && j < l2)
            nums.offer(nums2[j++]);
        return l % 2 == 0 ? ((double) nums.poll() + nums.poll()) / 2 : nums.poll();
    }
}
