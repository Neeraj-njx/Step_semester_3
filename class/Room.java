import java.time.LocalDate;
import java.util.*;

abstract class Room {
    private final int roomNumber;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public abstract double calculatePrice(long nights);
}

class StandardRoom extends Room {
    public StandardRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 100;
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 180;
    }
}

class Suite extends Room {
    public Suite(int roomNumber) {
        super(roomNumber);
    }

    @Override
    public double calculatePrice(long nights) {
        return nights * 300;
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

enum ReservationStatus {
    ACTIVE,
    CANCELLED
}

class Reservation {
    private final Customer customer;
    private final Room room;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalDate cancellationDeadline;

    private final double price;

    private ReservationStatus status =
            ReservationStatus.ACTIVE;

    public Reservation(
            Customer customer,
            Room room,
            LocalDate startDate,
            LocalDate endDate,
            LocalDate cancellationDeadline) {

        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cancellationDeadline = cancellationDeadline;

        long nights =
                endDate.toEpochDay()
                        - startDate.toEpochDay();

        this.price = room.calculatePrice(nights);
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void cancel(LocalDate currentDate) {
        if (status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Reservation already cancelled.");
        }

        if (currentDate.isAfter(cancellationDeadline)) {
            throw new IllegalStateException(
                    "Cancellation deadline has passed.");
        }

        status = ReservationStatus.CANCELLED;

        System.out.println(
                "Reservation cancelled successfully.");
    }

    public double getPrice() {
        return price;
    }
}

class HotelBookingSystem {
    private final List<Reservation> reservations =
            new ArrayList<>();

    public boolean isAvailable(
            Room room,
            LocalDate start,
            LocalDate end) {

        for (Reservation reservation : reservations) {

            if (reservation.getRoom() != room ||
                reservation.getStatus() == ReservationStatus.CANCELLED) {
                continue;
            }

            boolean overlaps =
                    start.isBefore(reservation.getEndDate())
                    &&
                    end.isAfter(reservation.getStartDate());

            if (overlaps) {
                return false;
            }
        }

        return true;
    }

    public Reservation reserve(
            Customer customer,
            Room room,
            LocalDate start,
            LocalDate end,
            LocalDate cancellationDeadline) {

        if (!isAvailable(room, start, end)) {
            System.out.println(
                    "Room " + room.getRoomNumber()
                            + " is not available.");
            return null;
        }

        Reservation reservation =
                new Reservation(
                        customer,
                        room,
                        start,
                        end,
                        cancellationDeadline);

        reservations.add(reservation);

        System.out.println(
                "Reservation confirmed for "
                        + customer.getName()
                        + ", Room "
                        + room.getRoomNumber());

        System.out.println(
                "Price: $" + reservation.getPrice());

        return reservation;
    }
}

public class HotelDemo {
    public static void main(String[] args) {

        HotelBookingSystem system =
                new HotelBookingSystem();

        Customer customerA =
                new Customer(1, "Customer A");

        Customer customerB =
                new Customer(2, "Customer B");

        Room room101 =
                new StandardRoom(101);

        Room room201 =
                new DeluxeRoom(201);

        LocalDate start =
                LocalDate.of(2026, 1, 1);

        LocalDate end =
                LocalDate.of(2026, 1, 5);

        Reservation reservation =
                system.reserve(
                        customerA,
                        room101,
                        start,
                        end,
                        LocalDate.of(2025, 12, 30));

        system.reserve(
                customerB,
                room101,
                LocalDate.of(2026, 1, 3),
                LocalDate.of(2026, 1, 7),
                LocalDate.of(2026, 1, 1));

        if (reservation != null) {
            reservation.cancel(
                    LocalDate.of(2025, 12, 29));
        }

        system.reserve(
                new Customer(3, "Customer C"),
                room201,
                LocalDate.of(2026, 2, 10),
                LocalDate.of(2026, 2, 12),
                LocalDate.of(2026, 2, 5));
    }
}
