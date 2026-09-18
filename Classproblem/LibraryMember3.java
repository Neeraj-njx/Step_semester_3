import java.util.Arrays;

class LibraryMember {
    protected int totalFine;
    private int[] fineHistory = new int[10];
    private int fineCount;

    protected void chargeFine(int amount) {
        totalFine += amount;
        fineHistory[fineCount++] = amount;
    }

    public int[] getFineHistory() {
        return Arrays.copyOf(fineHistory, fineCount);
    }

    public int getTotalFine() {
        return totalFine;
    }
}

class StudentMember extends LibraryMember {
    public StudentMember(String id, int borrowLimit, String course) {
    }

    @Override
    protected void chargeFine(int amount) {
        super.chargeFine(amount / 2);
    }
}
