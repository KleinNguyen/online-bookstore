import data_structure.MyQueueArrayList;
import myClass.Admin;
import myClass.Customer;

import java.util.Arrays;
import java.util.Scanner;

import algorithms.MySort;
public class App {
    private static Scanner sc = new Scanner(System.in);
    private static Customer cus = new Customer(1,"tuan","tuan@gmail.com", "tuan's street");
    private static Admin admin = new Admin(1, "admin", "admin@gmail.com");
    public static void main(String[] args) {
        mainMenu();
    }

    private static void clearScreen(){
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
    }
    private static void mainMenu(){
        while(true){
            clearScreen();
            System.out.println("---------Online Bookstore---------");
            System.out.println("1. Customer Menu");
            System.out.println("2. Admin Menu");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    customerMenu();
                    break;
                case "2":
                    adminMenu();
                    break;
                case "0":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid Choice! Please enter again!");
                    
                    break;
            }
        }
    }

    private static void customerMenu(){
        while(true){
            clearScreen();
            System.out.println("---------Customer Menu---------");
            System.out.println("Customer: " + cus.getUsername());
            System.out.println("1. View all products");
            System.out.println("2. Search book");
            System.out.println("3. Sort book");
            System.out.println("4. Place order");
            System.out.println("5. View order history");
            System.out.println("6. Search order");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    viewAllProduct();
                    break;
                case "2":
                    searchBookMenu();
                    break;
                case "3":
                    sortBookMenu();
                    break;
                case "4":
                    placeOrder();
                    break;
                case "5":
                    viewOrderHistory()
                    break;
                case "6":
                    searchOrder();
                    break;
                case "0":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid Choice! Please enter again!");
                    break;
            }
        }
    }

    private static void adminMenu(){
        while(true){
            clearScreen();
            System.out.println("---------Customer Menu---------");
            System.out.println("Admin: " + admin.getUsername());
            System.out.println("1. Book Management");
            System.out.println("2. Order Management");
            System.out.println("3. Customer Management");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    bookManagementMenu();
                    break;
                case "2":
                    orderManagementMenu()
                    break;
                case "3":
                    customerManagementMenu();
                    break;
                case "0":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid Choice! Please enter again!");
                    break;
            }
        }
    }
}
