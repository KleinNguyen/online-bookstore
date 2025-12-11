package myClass;

import java.util.ArrayList;

public class Order {
    private int id;
    private ArrayList<Customer> info;
    private ArrayList<OrderItem> items;
    private String status;

    public Order(int id,ArrayList<Customer> info , ArrayList<OrderItem> items, String status){
        this.id = id;
        this.info = info;
        this.items = items;
        this.status = status;
    }

}
