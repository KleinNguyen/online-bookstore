import data_structure.MyQueueArrayList;

import java.util.Arrays;
import java.util.Scanner;

import algorithms.MySort;
public class App {
    public static void main(String[] args) {
        customerMenu();
    }

    private static void customerMenu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("1. View all product");
        System.out.println("2. Orders product");
        System.out.println("3. View order history");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                
                break;
            case 2:
                
                break;
            case 3:
                
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid Choice! Please enter again!");
                break;
        }
    }

}
