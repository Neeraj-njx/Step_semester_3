class PayrollAccount {
    // Private fields - cannot be accessed directly from outside
    private double basicSalary;
    private double bonus;

    // Constructor
    public PayrollAccount(double openingBasicSalary) {
        if (openingBasicSalary < 0) {
            System.out.println("Warning: Negative salary not allowed. Starting with Rs 0.0");
            basicSalary = 0;
        } else {
            basicSalary = openingBasicSalary;
        }

        bonus = 0;
    }

    // Add bonus
    public void creditBonus(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid bonus: Amount must be greater than 0.");
        } else {
            bonus += amount;
            System.out.println("Bonus credited: Rs " + amount);
        }
    }

    // Deduct tax
    public void deductTax(double percent) {
        if (percent < 0 || percent > 100) {
            System.out.println("Invalid tax: Percent must be between 0 and 100.");
        } else {
            basicSalary -= basicSalary * percent / 100;
            System.out.println("Tax deducted: " + percent + "%");
        }
    }

    // Read-only access to net salary
    public double getNetSalary() {
        return basicSalary + bonus;
    }
}

public class Main {
    public static void main(String[] args) {

        PayrollAccount account = new PayrollAccount(50000);

        account.creditBonus(5000);
        account.deductTax(10);

        System.out.println("Net salary: Rs " + account.getNetSalary());
    }
}
