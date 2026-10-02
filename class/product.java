import java.util.*;

class Product {
    private final int id;
    private final String name;
    private final double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}

class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be positive.");
        }

        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }
}

enum OrderStatus {
    PENDING,
    PAID
}

interface PaymentMethod {
    boolean processPayment(double amount);
}

class CreditCardPayment implements PaymentMethod {

    @Override
    public boolean processPayment(double amount) {
        System.out.println(
                "Processing Credit Card payment...");
        return true;
    }
}

class PayPalPayment implements PaymentMethod {

    @Override
    public boolean processPayment(double amount) {
        System.out.println(
                "Processing PayPal payment...");
        return false;
    }
}

class BankTransferPayment implements PaymentMethod {

    @Override
    public boolean processPayment(double amount) {
        System.out.println(
                "Processing Bank Transfer...");
        return true;
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

class Order {
    private final String id;
    private final Customer customer;

    private final List<OrderItem> items =
            new ArrayList<>();

    private OrderStatus status =
            OrderStatus.PENDING;

    public Order(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public void addProduct(
            Product product,
            int quantity) {

        items.add(new OrderItem(product, quantity));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double calculateTotal() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getTotal();
        }

        return total;
    }

    public void pay(PaymentMethod paymentMethod) {

        if (items.isEmpty()) {
            System.out.println(
                    "Cannot process payment for an empty order.");
            return;
        }

        if (status == OrderStatus.PAID) {
            System.out.println(
                    "Order is already paid.");
            return;
        }

        System.out.println(
                "Payment initiated for Order " + id);

        boolean success =
                paymentMethod.processPayment(
                        calculateTotal());

        if (success) {
            status = OrderStatus.PAID;

            System.out.println(
                    "Payment for Order "
                            + id + " successful.");

            System.out.println(
                    "Order status: " + status);
        } else {
            System.out.println(
                    "Payment for Order "
                            + id + " failed.");

            System.out.println(
                    "Order status: " + status);
        }
    }
}

public class PaymentDemo {
    public static void main(String[] args) {

        Customer customerX =
                new Customer(1, "Customer X");

        Product productA =
                new Product(1, "Product A", 100);

        Product productB =
                new Product(2, "Product B", 200);

        Order orderX =
                new Order("X", customerX);

        orderX.addProduct(productA, 2);
        orderX.addProduct(productB, 1);

        System.out.println(
                "Order created for Customer X.");

        orderX.pay(new CreditCardPayment());

        Customer customerY =
                new Customer(2, "Customer Y");

        Order emptyOrder =
                new Order("Y", customerY);

        emptyOrder.pay(new CreditCardPayment());

        Customer customerZ =
                new Customer(3, "Customer Z");

        Order orderZ =
                new Order("Z", customerZ);

        orderZ.addProduct(
                new Product(3, "Product C", 150), 1);

        orderZ.pay(new PayPalPayment());
    }
}
