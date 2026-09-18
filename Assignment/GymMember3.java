import java.util.Arrays;

class GymMember3 {
    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    private int totalLateFees;
    private int[] lateFeeHistory = new int[10];
    private int lateFeeCount;

    public GymMember(String memberId, int monthlyFee) {
        if (memberId == null || memberId.trim().length() < 4) {
            throw new IllegalArgumentException();
        }

        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
    }

    protected void chargeLateFee(int amount) {
        totalLateFees += amount;
        lateFeeHistory[lateFeeCount++] = amount;
    }

    public int[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, lateFeeCount);
    }

    public int getTotalLateFees() {
        return totalLateFees;
    }
}

class PremiumMember extends GymMember {
    private String trainerName;

    public PremiumMember(String memberId, int monthlyFee, String trainerName) {
        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    @Override
    protected void chargeLateFee(int amount) {
        super.chargeLateFee(amount / 2);
    }
}
