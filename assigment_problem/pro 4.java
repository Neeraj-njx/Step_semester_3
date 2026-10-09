public class Main {

    static void analyzeInventory(int[] sectionA, int[] sectionB) {
        if (sectionA.length != sectionB.length) {
            System.out.println("Error: Arrays must have equal lengths.");
            return;
        }

        if (sectionA.length == 0) {
            System.out.println("Inventory arrays are empty.");
            return;
        }

        int totalA = 0;
        int totalB = 0;

        // Calculate section totals
        for (int i = 0; i < sectionA.length; i++) {
            totalA += sectionA[i];
            totalB += sectionB[i];
        }

        String status = (totalA == totalB) ? "Balanced" : "Not Balanced";

        // Find the highest quantity across both sections
        int maxQuantity = sectionA[0];
        String maxSection = "Section A";
        int maxIndex = 0;

        for (int i = 0; i < sectionA.length; i++) {
            if (sectionA[i] > maxQuantity) {
                maxQuantity = sectionA[i];
                maxSection = "Section A";
                maxIndex = i;
            }

            if (sectionB[i] > maxQuantity) {
                maxQuantity = sectionB[i];
                maxSection = "Section B";
                maxIndex = i;
            }
        }

        System.out.println(
            "Section A Total: " + totalA
            + " | Section B Total: " + totalB
            + " | Status: " + status
            + " | Highest Quantity: " + maxQuantity
            + " (" + maxSection + ", Item " + (maxIndex + 1) + ")"
        );
    }

    public static void main(String[] args) {
        int[] sectionA = {20, 15, 30};
        int[] sectionB = {25, 10, 30};

        analyzeInventory(sectionA, sectionB);
    }
}

