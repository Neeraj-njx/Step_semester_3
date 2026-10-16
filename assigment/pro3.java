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

    // Reverse the linked list in place
    static Node reverse(Node head) {
        Node previous = null;
        Node current = head;
        Node next = null;

        while (current != null) {
            next = current.next;       // Save the next node
            current.next = previous;    // Reverse the link
            previous = current;        // Move previous forward
            current = next;            // Move current forward
        }

        return previous;
    }

    // Add a node at the end
    static Node addLast(Node head, int value) {
        Node newNode = new Node(value);

        if (head == null) {

