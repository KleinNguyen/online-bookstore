package myClass;

import java.util.ArrayList;

public class Order {
    private int id;
    private Customer customer;              
    private ArrayList<OrderItem> items;     
    private String status;

    public Order(int id, Customer customer, ArrayList<OrderItem> items, String status){
        this.id = id;
        this.customer = customer;
        this.items = items;
        this.status = status;
    }
    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== ORDER #").append(id).append(" =====\n");
        sb.append("Customer: ").append(customer.getUsername()).append("\n");
        sb.append("Address: ").append(customer.getAddress()).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Items:\n");

        for (OrderItem item : items) {
            sb.append("  - ").append(item.toString()).append("\n");
        }

        sb.append("============================\n");
        return sb.toString();
    }
}
