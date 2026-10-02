import java.util.*;

abstract class Vehicle {
    private final String id;
    private final String name;
    private boolean available = true;

    public Vehicle(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateCharge(int days);
}

class Sedan extends Vehicle {
    public Sedan(String id, String name) {
        super(id, name);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 50;
    }
}

class SUV extends Vehicle {
    public SUV(String id, String name) {
        super(id, name);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 80;
    }
}

class Truck extends Vehicle {
    public Truck(String id, String name) {
        super(id, name);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 100;
    }
}

class Customer {
    private final int id;
    private final String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private final Vehicle vehicle;
    private final Customer customer;
    private final int days;
    private final double charge;

    public Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
        this.charge = vehicle.calculateCharge(days);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getCharge() {
        return charge;
    }
}

class RentalSystem {
    private final List<Rental> rentals = new ArrayList<>();

    public void rentVehicle(Customer customer, Vehicle vehicle, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getName() + " is currently unavailable.");
            return;
        }

        Rental rental = new Rental(vehicle, customer, days);
        rentals.add(rental);
        vehicle.setAvailable(false);

        System.out.println(vehicle.getName() +
                " rented successfully by " + customer.getName());
        System.out.println("Rental charge: $" + rental.getCharge());
    }

    public void returnVehicle(Vehicle vehicle) {
        for (Iterator<Rental> it = rentals.iterator(); it.hasNext();) {
            Rental rental = it.next();

            if (rental.getVehicle() == vehicle) {
                vehicle.setAvailable(true);
                it.remove();

                System.out.println(vehicle.getName() +
                        " returned by " + rental.getCustomer().getName());
                return;
            }
        }

        System.out.println("No active rental found.");
    }
}

public class VehicleRentalDemo {
    public static void main(String[] args) {
        RentalSystem system = new RentalSystem();

        Customer c1 = new Customer(1, "Customer 1");
        Customer c2 = new Customer(2, "Customer 2");
        Customer c3 = new Customer(3, "Customer 3");

        Vehicle sedanA = new Sedan("S1", "Sedan A");
        Vehicle suvB = new SUV("S2", "SUV B");

        system.rentVehicle(c1, sedanA, 3);
        system.rentVehicle(c2, sedanA, 2);

        system.returnVehicle(sedanA);

        system.rentVehicle(c3, suvB, 5);
    }
}
