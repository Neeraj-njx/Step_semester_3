
import java.util.*;

public class Main {

    static String reverseCustomerName(String customerName) {
        char[] characters = customerName.toCharArray();

        int left = 0;
        int right = characters.length - 1;

        while (left < right) {
            char temp = characters[left];
            characters[left] = characters[right];
            characters[right] = temp;

            left++;
            right--;
        }

        return new String(characters);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();

        String reversedName = reverseCustomerName(customerName);

        System.out.println("Original Name: " + customerName);
        System.out.println("Reversed Name: " + reversedName);

        sc.close();
    }
}
