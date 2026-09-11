import java.util.Arrays;

// --- Immutable Loan Receipt ---
class LoanReceipt {
    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        this.memberId = memberId;
        // Defensive copy on the way in
        if (bookIds != null) {
            this.bookIds = Arrays.copyOf(bookIds, bookIds.length);
        } else {
            this.bookIds = new String[0];
        }
    }

    public String getMemberId() {
        return memberId;
    }

    // Defensive copy on the way out
    public String[] getBookIds() {
        return Arrays.copyOf(this.bookIds, this.bookIds.length);
    }

    // Wither pattern: returns a new instance, leaving the original unchanged
    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= this.bookIds.length) {
            throw new IndexOutOfBoundsException("Invalid book index: " + index);
        }

        String[] updatedIds = Arrays.copyOf(this.bookIds, this.bookIds.length);
        updatedIds[index] = newId;
        return new LoanReceipt(this.memberId, updatedIds);
    }
}

// --- Reference-Only Variant ---
class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= getBookIds().length) {
            throw new IndexOutOfBoundsException("Invalid book index: " + index);
        }

        String[] updatedIds = getBookIds();
        updatedIds[index] = newId;
        return new ReferenceOnlyLoanReceipt(getMemberId(), updatedIds, this.roomNumber);
    }
}

// --- Nightly Circulation Ledger ---
class CirculationLedger {
    private static final String BRANCH_CODE;

    // Static initialization block for one-time class-level state
    static {
        BRANCH_CODE = "BRANCH-CENTRAL-01";
    }

    public static String getBranchCode() {
        return BRANCH_CODE;
    }

    // Single-pass nightly reconciliation with O(1) auxiliary space
    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        int processedCount = 0;
        int nullSkippedCount = 0;
        int referenceOnlyCount = 0;
        int regularCount = 0;

        for (LoanReceipt receipt : receipts) {
            // Null safety: skip flaky scanner entries without throwing exceptions
            if (receipt == null) {
                nullSkippedCount++;
                continue;
            }

            processedCount++;

            // instanceof polymorphic dispatch
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnlyCount++;
            } else {
                regularCount++;
            }
        }

        return String.format(
            "%d processed | %d null skipped | %d reference-only | %d regular",
            processedCount,
            nullSkippedCount,
            referenceOnlyCount,
            regularCount
        );
    }
}

// --- Execution Entry Point ---
public class Main {
    public static void main(String[] args) {
        // 1. Array-mutation trap check (both directions)
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println("Mutated output check: " + r.getBookIds()[0]); // Outputs: BK-100

        // 2. Wither check
        LoanReceipt corrected = r.withCorrectedBookId(1, "BK-102");
        System.out.println("Original:  " + Arrays.toString(r.getBookIds()));         // ["BK-100", "BK-101"]
        System.out.println("Corrected: " + Arrays.toString(corrected.getBookIds())); // ["BK-100", "BK-102"]

        // 3. Nightly batch processing with null tolerance
        LoanReceipt[] batch = new LoanReceipt[]{
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };

        String result = CirculationLedger.processNightlyCirculation(batch);
        System.out.println(result);
        // Outputs: "2 processed | 1 null skipped | 1 reference-only | 1 regular"
    }
}
