import java.math.BigInteger;

public class NodeListSum {
    public static void main(String[] args) {
        ListNode l1 = createListNodeList("1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1");
        printListNode(l1);
        ListNode l2 = createListNodeList("5,6,4");
        printListNode(l2);
        printListNode(addTwoNumbers(l1, l2));
    }

    private static ListNode createListNodeList(String val) {
        ListNode head = new ListNode(Character.getNumericValue(val.charAt(0)));
        ListNode temp = head;
        for (char c : val.replace(",", "").substring(1).toCharArray()) {
            temp.next = new ListNode(Character.getNumericValue(c));
            temp = temp.next;
        }
        return head;
    }

    private static void printListNode(ListNode l) {
        while (l != null) {
            System.out.print(l.val);
            l = l.next;
        }
        System.out.println();
    }

    private static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        BigInteger s = findNum(l1).add(findNum(l2));
        ListNode head = new ListNode(s.mod(BigInteger.TEN).intValue());
        ListNode temp = head;
        s = s.divide(BigInteger.TEN);
        while (!s.equals(BigInteger.ZERO)) {
            temp.next = new ListNode(s.mod(BigInteger.TEN).intValue());
            s = s.divide(BigInteger.TEN);
            temp = temp.next;
        }
        return head;
    }

    private static BigInteger findNum(ListNode l1) {
        BigInteger n1 = BigInteger.ZERO, p = BigInteger.ONE;
        while (l1 != null) {
            n1 = n1.add(BigInteger.valueOf(l1.val).multiply(p));
            p = p.multiply(BigInteger.TEN);
            l1 = l1.next;
        }
        return n1;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
