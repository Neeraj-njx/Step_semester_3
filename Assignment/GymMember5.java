class GymMember5 {
    private static int membersEnrolled = 0;
    private static int counter = 2000;

    public final String membershipNumber;

    protected int monthlyFee;
    private int feesPaid;

    public GymMember(int monthlyFee) {
        membersEnrolled++;
        counter++;
        membershipNumber = "GYM-" + counter;
        this.monthlyFee = monthlyFee;
    }

    public void payFee(int amount) {
        feesPaid += amount;
    }

    public void payFee(int amount, String mode) {
        payFee(amount);
    }

    public int getFeesPaid() {
        return feesPaid;
    }

    public static boolean isValidReferralCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }

        return code.charAt(0) == 'G'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isUpperCase(code.charAt(3));
    }

    public static int getMembersEnrolled() {
        return membersEnrolled;
    }

    public static String processWeeklyCheckIn(GymMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (GymMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof GroupClassMember) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + group + " group | "
                + individual + " individual";
    }
}

class GroupClassMember extends GymMember {
    private String className;

    public GroupClassMember(int monthlyFee, String className) {
        super(monthlyFee);
        this.className = className;
    }
}
