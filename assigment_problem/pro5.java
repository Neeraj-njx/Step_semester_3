import java.util.Scanner;

public class Main {

    static void classifyWordLengths(String review) {
        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;

        // Handle an empty or whitespace-only review
        if (review.trim().isEmpty()) {
            System.out.println("Short: 0 | Medium: 0 | Long: 0");
            return;
        }

        String[] words = review.trim().split("\\s+");

        for (String word : words) {
            // Count letters, ignoring punctuation at word boundaries
            String cleanedWord = word.replaceAll(
                "^[^\\p{L}\\p{N}]+|[^\\p{L}\\p{N}]+$", ""
            );

            int length = cleanedWord.length();

            if (length == 0) {
                continue;
            }

            if (length <= 4) {
                shortCount++;
            } else if (length <= 8) {
                mediumCount++;
            } else {
                longCount++;
            }
        }

        System.out.println(
            "Short: " + shortCount
            + " | Medium: " + mediumCount
            + " | Long: " + longCount
        );
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter movie review:");
        String review = sc.nextLine();

        classifyWordLengths(review);

        sc.close();
    }
}

