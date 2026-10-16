import java.util.Scanner;

public class Main {

    // Node class
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // Remove all nodes containing the given code
    static Node removeAll(Node head, int code) {

        // Remove matching nodes from the beginning
        while (head != null && head.data == code) {
            head = head.next;
        }

        // Remove matching nodes from the rest of the list
        Node current = head;

        while (current != null && current.next != null) {
            if (current.next.data == code) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }

        return head;
    }

    // Print the linked list
    static void printList(Node head) {
        Node current = head;

        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }

        System.out.println("null");
    }

    // Add a node at the end
    static Node addLast(Node head, int value) {
        Node newNode = new Node(value);

        if (head == null) {
            return newNode;
        }

        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
        return head;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read the number of orders
        int n = sc.nextInt();

        Node head = null;

        // Build the linked list
        for (int i = 0; i < n; i++) {
            int value = sc.nextInt();
            head = addLast(head, value);
        }

        // Read the cancelled order code
        int code = sc.nextInt();

        // Remove all occurrences of the code
        head = removeAll(head, code);

        // Print the updated list
        printList(head);

        sc.close();
    }
}

