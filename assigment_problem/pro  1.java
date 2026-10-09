public class Main {

```
public static void checkDuplicateSeats(int[] seatNumbers) {
    boolean foundDuplicate = false;

    for (int i = 0; i < seatNumbers.length; i++) {
        boolean alreadyPrinted = false;

        for (int k = 0; k < i; k++) {
            if (seatNumbers[i] == seatNumbers[k]) {
                alreadyPrinted = true;
                break;
            }
        }

        if (alreadyPrinted) {
            continue;
        }

        for (int j = i + 1; j < seatNumbers.length; j++) {
            if (seatNumbers[i] == seatNumbers[j]) {
                System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                foundDuplicate = true;
                break;
            }
        }
    }

    if (!foundDuplicate) {
        System.out.println("No Duplicate Seats Found");
    }
}

public static
```
