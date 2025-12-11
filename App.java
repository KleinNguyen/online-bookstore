import data_structure.*;
import myClass.*;
import algorithms.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Book> bookList = new ArrayList<>();
    private static ArrayList<Customer> customerList = new ArrayList<>();
    private static ArrayList<Admin> adminList = new ArrayList<>();
    private static MyQueueArrayList<Order> orderQueue = new MyQueueArrayList<>();
    private static MyStack<Order> orderHistory = new MyStack<>();
    private static int bookIdCounter = 1;
    private static int orderIdCounter = 1;
    private static int customerIdCounter = 1;
    public static void main(String[] args) {
        // mock data book list
        bookList.add(new Book(bookIdCounter++, "Brave New World", "Aldous Huxley"));   
        bookList.add(new Book(bookIdCounter++, "Catch-22", "Joseph Heller"));          
        bookList.add(new Book(bookIdCounter++, "Don Quixote", "Miguel de Cervantes")); 
        bookList.add(new Book(bookIdCounter++, "Animal Farm", "George Orwell"));      
        bookList.add(new Book(bookIdCounter++, "Frankenstein", "Mary Shelley"));     


        // mock data for customer
        customerList.add(new Customer(customerIdCounter++, "tuan","tuan@gmail.com", "tuan's street"));
        customerList.add(new Customer(customerIdCounter++, "huy", "huy@gmail.com", "huy's street"));

        // mock data for admin 
        adminList.add(new Admin(1, "admin", "admin@gmail.com"));
        mainMenu();
    }

    // this is use to see only 1 menu
    private static void clearScreen(){
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
    }

    // this is ask user enter to continue
    private static void pause() {
        System.out.println();
        System.out.print(">> Press Enter to continue...");
        sc.nextLine();
    }
    // main menu: inluce login customer and admin
    private static void mainMenu(){
        while(true){
            clearScreen();
            System.out.println("---------Online Bookstore---------");
            System.out.println("1. Customer Login");
            System.out.println("2. Admin Login");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    Customer loginCus = customerLogin();
                    if(loginCus != null){
                        customerMenu(loginCus);
                    }
                    break;
                case "2":
                    Admin loginAdmin = adminLogin();
                    if(loginAdmin != null){
                        adminMenu(loginAdmin);
                    }
                    break;
                case "0":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid Choice! Please enter again!");
                    pause();
            }
        }
    }
    // customer login
    private static Customer customerLogin(){
        clearScreen();
        System.out.println("---------Customer Login---------");
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        for(Customer cus : customerList){
            if(cus.getEmail().equalsIgnoreCase(email)){
                System.out.println("\nLogin successful!");
                pause();
                return cus;
            }
        }

        System.out.println("\nLogin failed! User not found!");
        pause();
        return null;
    }

    // admin login
    private static Admin adminLogin(){
        clearScreen();
        System.out.println("---------Admin Login---------");
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        for(Admin ad : adminList){
            if( ad.getEmail().equalsIgnoreCase(email)){
                System.out.println("\nAdmin login successful!");
                pause();
                return ad;
            }
        }

        System.out.println("\nLogin failed! Admin not found!");
        pause();
        return null;
    }
    // customer menu
    private static void customerMenu(Customer cus){
        while(true){
            clearScreen();
            System.out.println("---------Customer Menu---------");
            System.out.println("Customer: " + cus.getUsername());
            System.out.println("1. View all products");
            System.out.println("2. Search book");
            System.out.println("3. Sort book");
            System.out.println("4. Place order");
            System.out.println("5. View order history");
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
                    placeOrder(cus);
                    break;
                case "5":
                    viewOrderHistory(cus);
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

    // view all product feature
    private static void viewAllProduct() {
        clearScreen();
        System.out.println("---------All Books---------");
        if(bookList.isEmpty()){
            System.out.println("No book available!");
        } else{
            for(int i = 0; i < bookList.size(); i++){
                Book book = bookList.get(i);
                System.out.println((i+1) + ". ID: " + book.getId() + " | Title: " +  book.getBookTitle() + " | Author: " + book.getAuthor());
            }
        }
        pause();
    }
    // place order feature
    private static void placeOrder(Customer cus) {
        clearScreen();
        System.out.println("---------Place Order---------");
        if(bookList.isEmpty()){
            System.out.println("No books available!");
            pause();
            return;
        }
        ArrayList<OrderItem> items = new ArrayList<>();
        while(true){
            System.out.println("0: <- Back");
            System.out.println("Available Books: ");
            for(int i = 0; i < bookList.size(); i++){
                Book book = bookList.get(i);
                System.out.println((i+1) + ". " + book.getBookTitle() + " - " + book.getAuthor());
            }
            System.out.print("Enter book number to place order: ");
            try{
                int bookNum = Integer.parseInt(sc.nextLine());
                if(bookNum == 0){
                    break;
                }
                if(bookNum < 1 || bookNum > bookList.size()){
                    System.out.println();
                    System.out.println("Invalid book number!");
                    pause();
                    return;
                }
                Book selectedBook = bookList.get(bookNum - 1);
                System.out.print("Enter quantity: ");
                int quantity = Integer.parseInt(sc.nextLine());
                if(quantity <= 0){
                    System.out.println("Quantity cannot be positive!");
                    pause();
                }
                items.add(new OrderItem(selectedBook, quantity));
                System.out.println("Added " + quantity + "x "+ selectedBook.getBookTitle());
                pause();
            } catch(NumberFormatException e){
                System.out.println();
                System.out.println("Invalid input!");
            }
        }
        Order newOrder = new Order(orderIdCounter++, cus, items, "Pending");
        orderQueue.add(newOrder);
        orderHistory.push(newOrder);
        System.out.println("\nOrder placed successfully!");
        System.out.println(newOrder);
        pause();
    }
    // view order history feature
    private static void viewOrderHistory(Customer cus) {
        clearScreen();
        System.out.println("---------Order History (Stack)---------");
        MyStack<Order> tempStack = new MyStack<>();
        boolean hasOrders = false;
        while(!orderHistory.isEmpty()) {
            Order order = orderHistory.pop();
            if (order.getCustomer().getId() == cus.getId()) {
            System.out.println(order);
            hasOrders = true;
            }
            tempStack.push(order);
        }
        while(!tempStack.isEmpty()) {
            orderHistory.push(tempStack.pop());
        }
        if(!hasOrders) {
            System.out.println("No order history available!");
        }
        pause();
    }
    // sort book menu
    private static void sortBookMenu() {
        clearScreen();
        System.out.println("---------Sort Books---------");
        System.out.println("1. Sort by ID (Selection Sort)");
        System.out.println("2. Sort by Title (Merge Sort)");
        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();
        
        Book[] bookArray = bookList.toArray(new Book[0]);
        
        switch(choice) {
            case "1":
                // Sort by ID using Selection Sort
                MySort.selectionSort(bookArray);
                System.out.println("Books sorted by ID (Selection Sort):");
                break;
            case "2":
                // Sort by Title using Merge Sort
                MySort.mergeSort(bookArray);
                System.out.println("Books sorted by Title (Merge Sort):");
                break;
            default:
                System.out.println("Invalid choice!");
                pause();
                return;
        }
        
        for(int i = 0; i < bookArray.length; i++) {
            Book book = bookArray[i];
            System.out.println((i+1) + ". ID: " + book.getId() + " | Title: " + 
                book.getBookTitle() + " | Author: " + book.getAuthor());
        }
        pause();
    }
    // search book menu
    private static void searchBookMenu() {
        clearScreen();
        System.out.println("---------Search Book---------");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();
        
        switch(choice) {
            case "1":
                searchBookByTitle();
                break;
            case "2":
                searchBookByAuthor();
                break;
            default:
                System.out.println("Invalid choice!");
        }
        pause();
    }
    private static void searchBookByTitle() {
        System.out.print("Enter book title: ");
        String title = sc.nextLine();
        boolean found = false;
        for(Book book : bookList) {
            if(book.getBookTitle().toLowerCase().contains(title.toLowerCase())) {
                System.out.println("Found: ID: " + book.getId() + " | Title: " + 
                    book.getBookTitle() + " | Author: " + book.getAuthor());
                found = true;
            }
        }
        if(!found) {
            System.out.println("No book found with title containing: " + title);
        }
    }
    private static void searchBookByAuthor() {
        System.out.print("Enter author name: ");
        String author = sc.nextLine();
        boolean found = false;
        for(Book book : bookList) {
            if(book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                System.out.println("Found: ID: " + book.getId() + " | Title: " + 
                    book.getBookTitle() + " | Author: " + book.getAuthor());
                found = true;
            }
        }
        if(!found) {
            System.out.println("No book found with author: " + author);
        }
    }

    private static void adminMenu(Admin admin){
        while(true){
            clearScreen();
            System.out.println("---------Admin Menu---------");
            System.out.println("Admin: " + admin.getUsername());
            System.out.println("1. Book Management");
            System.out.println("2. Order Management");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    bookManagementMenu();
                    break;
                case "2":
                    orderManagementMenu();
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

    private static void bookManagementMenu() {
        while(true) {
            clearScreen();
            System.out.println("---------Book Management---------");
            System.out.println("1. View all books");
            System.out.println("2. Add new book");
            System.out.println("3. Delete book");
            System.out.println("4. Update book");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            
            switch(choice) {
                case "1":
                    viewAllProduct();
                    break;
                case "2":
                    addBook();
                    break;
                case "3":
                    deleteBook();
                    break;
                case "4":
                    searchBookMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice!");
                    pause();
            }
        }
    }
    
    private static void addBook() {
        clearScreen();
        System.out.println("---------Add New Book---------");
        System.out.print("Enter book title: ");
        String title = sc.nextLine();
        System.out.print("Enter author: ");
        String author = sc.nextLine();
        
        Book newBook = new Book(bookIdCounter++, title, author);
        bookList.add(newBook);
        
        System.out.println("Book added successfully!");
        pause();
    }
    
    private static void deleteBook() {
        clearScreen();
        System.out.println("---------Delete Book---------");
        
        if(bookList.isEmpty()) {
            System.out.println("No books to delete!");
            pause();
            return;
        }
        
        viewAllProduct();
        System.out.print("\nEnter book ID to delete: ");
        
        try {
            int id = Integer.parseInt(sc.nextLine());
            boolean removed = false;
            
            for(int i = 0; i < bookList.size(); i++) {
                if(bookList.get(i).getId() == id) {
                    bookList.remove(i);
                    System.out.println("Book deleted successfully!");
                    removed = true;
                    break;
                }
            }
            
            if(!removed) {
                System.out.println("Book not found!");
            }
        } catch(NumberFormatException e) {
            System.out.println("Invalid ID!");
        }
        
        pause();
    }

    private static void orderManagementMenu() {
        while(true) {
            clearScreen();
            System.out.println("---------Order Management (Queue)---------");
            System.out.println("1. View pending orders");
            System.out.println("2. Process next order");
            System.out.println("3. View all order history");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            
            switch(choice) {
                case "1":
                    viewPendingOrders();
                    break;
                case "2":
                    processNextOrder();
                    break;
                case "3":
                    orderHistory();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice!");
                    pause();
            }
        }
    }
    
    private static void viewPendingOrders() {
        clearScreen();
        System.out.println("---------Pending Orders (Queue)---------");
        
        if(orderQueue.isEmpty()) {
            System.out.println("No pending orders!");
        } else {
            MyQueueArrayList<Order> tempQueue = new MyQueueArrayList<>();
            int count = 1;
            
            while(!orderQueue.isEmpty()) {
                Order order = orderQueue.poll();
                System.out.println("Order #" + count++);
                System.out.println(order);
                tempQueue.add(order);
            }
            
            // Restore queue
            while(!tempQueue.isEmpty()) {
                orderQueue.add(tempQueue.poll());
            }
        }
        
        pause();
    }
    
    private static void processNextOrder() {
        clearScreen();
        System.out.println("---------Process Next Order---------");
        
        if(orderQueue.isEmpty()) {
            System.out.println("No orders to process!");
            pause();
            return;
        }
        
        Order order = orderQueue.poll();
        System.out.println("Processing order:");
        System.out.println(order);
        
        order.setStatus("Completed");
        orderHistory.push(order);
        
        System.out.println("Order processed and moved to history!");
        pause();
    }
    private static void orderHistory() {
        clearScreen();
        System.out.println("---------Order History (Stack)---------");
        MyStack<Order> tempStack = new MyStack<>();
        boolean hasOrders = false;
        while(!orderHistory.isEmpty()) {
            Order order = orderHistory.pop();
            System.out.println(order);
            hasOrders = true;
            tempStack.push(order);
        }
        while(!tempStack.isEmpty()) {
            orderHistory.push(tempStack.pop());
        }
        if(!hasOrders) {
            System.out.println("No order history available!");
        }
        pause();
    }
}

