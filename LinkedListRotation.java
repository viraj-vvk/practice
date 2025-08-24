public class LinkedListRotation {
    private static class Node {
        private final int value;
        private Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public int getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1, null);
        Node temp = head;
        for (int i = 2; i < 11; i++) {
            temp.setNext(new Node(i, null));
            temp = temp.getNext();
        }

        printLinkedList(head);
        head = rotateRight(head, 10, 2);
        printLinkedList(head);
        head = rotateLeft(head, 10, 2);
        printLinkedList(head);

    }

    private static Node rotateRight(Node head, int size, int rotation) {
        if (size < 1) {
            throw new IllegalArgumentException("Invalid Size");
        }
        if (size == 1)
            return head;

        rotation = (rotation % size);
        Node fast = head;
        while (rotation-- > 0) {
            fast = fast.getNext();
        }
        Node slow = head;
        while (fast.getNext() != null) {
            fast = fast.getNext();
            slow = slow.getNext();
        }
        fast.setNext(head);
        head = slow.getNext();
        slow.setNext(null);

        return head;
    }

    private static Node rotateLeft(Node head, int size, int rotation) {
        return rotateRight(head, size, size - (rotation % size));
    }

    private static void printLinkedList(Node head) {
        while (head != null) {
            System.out.printf("%s\t", head.getValue());
            head = head.getNext();
        }
        System.out.println();
    }
}
