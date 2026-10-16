
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

    static Node head = null;

    // Add a token at the beginning
    static void addFirst(int token) {
        Node newNode = new Node(token);
        newNode.next = head;
        head = newNode;
    }

    // Add a token at the end
    static void addLast(int token) {
        Node newNode = new Node(token);

        if (head == null) {
            head = newNode;
            return;
        }

        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    // Insert a token at a given index (0-based)
    static void insertAt(int index, int token) {
