class GymMember4 {
    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    public GymMember(String memberId, int monthlyFee) {
        if (memberId == null || memberId.trim().length() < 4) {
            throw new IllegalArgumentException();
        }

        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
    }

    public void displayInfo() {
        System.out.print("Standard | Sessions: " + sessionsAttended + " | ");
    }

    public static String batchPrint(GymMember[] members) {
        StringBuilder result = new StringBuilder();

        for (GymMember member : members) {
            member.displayInfo();

            if (member instanceof PremiumMember) {
                PremiumMember premium = (PremiumMember) member;

                result.append("Premium | Trainer: ")
                      .append(premium.getTrainerName())
                      .append(" | Sessions: ")
                      .append(premium.getSessionsAttended())
                      .append(" [Trainer via downcast: ")
                      .append(premium.getTrainerName())
                      .append("] | ");
            } else {
                result.append("Standard | Sessions: ")
                      .append(member.getSessionsAttended())
                      .append(" | ");
            }
        }

        return result.toString();
    }

    public int getSessionsAttended() {
        return sessionsAttended;
    }
}

class PremiumMember extends GymMember {
    private String trainerName;

    public PremiumMember(String memberId, int monthlyFee, String trainerName) {
        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    @Override
    public void displayInfo() {
        System.out.print("Premium | Trainer: " + trainerName
                + " | Sessions: " + sessionsAttended + " | ");
    }

    public String getTrainerName() {
        return trainerName;
    }
}
