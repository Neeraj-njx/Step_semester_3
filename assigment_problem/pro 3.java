
import java.util.Scanner;

public class Main {

    static void findLongestStreak(String signalLog) {
        if (signalLog.isEmpty()) {
            System.out.println("Signal log is empty.");
            return;
        }

        char currentColor = signalLog.charAt(0);
        int currentCount = 1;

        char longestColor = currentColor;
        int longestCount = 1;

        for (int i = 1; i < signalLog.length(); i++) {
            char ch = signalLog.charAt(i);

            if (ch == currentColor) {
                currentCount++;
            } else {
                currentColor = ch;
                currentCount = 1;
            }

            if (currentCount > longestCount) {
                longestCount = currentCount;
                longestColor = currentColor;
            }
        }

        System.out.println(
            "Longest Streak: '" + longestColor
            + "' repeated " + longestCount + " times"
        );
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter signal log: ");
        String signalLog = sc.nextLine();

        findLongestStreak(signalLog);

        sc.close();
    }
}
