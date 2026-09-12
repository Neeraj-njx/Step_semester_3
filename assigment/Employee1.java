class Employee1 {
    // Instance fields
    String empName;
    double salary;

    // Static fields - shared by all employees
    static String companyName = "Bright Horizon Technologies";
    static int employeeCount = 0;

    // Constructor
    Employee(String empName, double salary) {
        this.empName = empName;
        this.salary = salary;

        // Increment whenever an employee object is created
        employeeCount++;
    }

    // Static method
    static void printCompanyInfo() {
        System.out.println("Company: " + companyName);
        System.out.println("Employee Count: " + employeeCount);
    }
}

public class Main {
    public static void main(String[] args) {

        // Create three Employee objects
        Employee emp1 = new Employee("Divya", 65000);
        Employee emp2 = new Employee("Arjun", 55000);
        Employee emp3 = new Employee("Priya", 60000);

        // Call static method using the class name
        Employee.printCompanyInfo();
    }
}
