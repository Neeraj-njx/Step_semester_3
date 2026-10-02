import java.time.LocalDate;

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

abstract class Employee {
    private final int id;
    private final String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isLeaveAllowed(int days);
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isLeaveAllowed(int days) {
        return days <= 30;
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isLeaveAllowed(int days) {
        return days <= 15;
    }
}

class Contractor extends Employee {
    public Contractor(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isLeaveAllowed(int days) {
        return days <= 5;
    }
}

class LeaveRequest {
    private final Employee employee;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private LeaveStatus status = LeaveStatus.PENDING;

    public LeaveRequest(Employee employee,
                        LocalDate startDate,
                        LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Invalid dates");
        }

        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void approve() {
        if (status != LeaveStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending requests can be approved.");
        }

        status = LeaveStatus.APPROVED;
    }

    public void reject() {
        if (status != LeaveStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending requests can be rejected.");
        }

        status = LeaveStatus.REJECTED;
    }

    public void changeStatus(LeaveStatus newStatus) {
        if (status != LeaveStatus.PENDING) {
            throw new IllegalStateException(
                    "Cannot change leave request status from "
                            + status + " to " + newStatus);
        }

        if (newStatus != LeaveStatus.APPROVED &&
            newStatus != LeaveStatus.REJECTED) {
            throw new IllegalArgumentException(
                    "Invalid status transition.");
        }

        status = newStatus;
    }

    public Employee getEmployee() {
        return employee;
    }
}

class LeaveManagementSystem {

    public LeaveRequest submitRequest(
            Employee employee,
            LocalDate start,
            LocalDate end) {

        int days = (int) (end.toEpochDay() - start.toEpochDay()) + 1;

        if (!employee.isLeaveAllowed(days)) {
            throw new IllegalArgumentException(
                    "Leave policy does not allow this duration.");
        }

        LeaveRequest request =
                new LeaveRequest(employee, start, end);

        System.out.println(
                "Leave request submitted for "
                        + employee.getName()
                        + " (" + start + " - " + end + ").");

        System.out.println("Status: " + request.getStatus());

        return request;
    }
}

public class LeaveDemo {
    public static void main(String[] args) {

        LeaveManagementSystem system =
                new LeaveManagementSystem();

        Employee john =
                new FullTimeEmployee(1, "John");

        LeaveRequest request =
                system.submitRequest(
                        john,
                        LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 1, 5));

        request.approve();

        System.out.println(
                "John's leave request approved.");
        System.out.println("Status: " + request.getStatus());

        try {
            request.changeStatus(LeaveStatus.PENDING);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
