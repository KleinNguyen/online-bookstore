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
        bookList.add(new Book(bookIdCounter++, "Catch-22", "Joseph Heller"));          
        bookList.add(new Book(bookIdCounter++, "Don Quixote", "Miguel de Cervantes")); 
        bookList.add(new Book(bookIdCounter++, "Animal Farm", "George Orwell"));      
        bookList.add(new Book(bookIdCounter++, "Frankenstein", "Mary Shelley"));     
        bookList.add(new Book(bookIdCounter++, "Brave New World", "Aldous Huxley"));   


        // mock data for customer
        customerList.add(new Customer(customerIdCounter++, "tuan","tuan@gmail.com", "tuan's street","123456"));
        customerList.add(new Customer(customerIdCounter++, "huy", "huy@gmail.com", "huy's street", "123456"));

        // mock data for admin 
        adminList.add(new Admin(1, "admin", "admin@gmail.com","123456"));
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
    private static void mainMenu() {
        while(true){
            clearScreen();
            System.out.println("---------Online Bookstore---------");
            System.out.println("1. Login");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    login(); // gọi hàm login duy nhất
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
    // login
    private static void login() {
        clearScreen();
        System.out.println("---------Login---------");
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        // check customer
        for (Customer cus : customerList) {
            if (cus.getEmail().equalsIgnoreCase(email) && cus.getPassword().equals(password)) {
                System.out.println("\nCustomer login successful!");
                pause();
                customerMenu(cus);
                return;
            }
        }
        // check admin
        for (Admin admin : adminList) {
            if (admin.getEmail().equalsIgnoreCase(email) && admin.getPassword().equals(password)) {
                System.out.println("\nAdmin login successful!");
                pause();
                adminMenu(admin);
                return;
            }
        }
        System.out.println("\nLogin failed! Email or password incorrect!");
        pause();
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
                    searchBook();
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
        System.out.println("1. Sort by Title (Merge Sort)");
        System.out.println("2. Sort by Author (Selection Sort)");
        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();
        Book[] bookArray = bookList.toArray(new Book[0]);
        switch(choice) {
            case "1":
                MySort.mergeSort(bookArray);
                System.out.println("Books sorted by Title (Merge Sort):");
                for(int i = 0; i < bookArray.length; i++) {
                    Book book = bookArray[i];
                    System.out.println((i+1) + ". ID: " + book.getId() + " | Title: " + 
                        book.getBookTitle() + " | Author: " + book.getAuthor());
                }
                break;
            case "2":
                // Sort by Author using Selection Sort
                BookByAuthor[] booksByAuthor = new BookByAuthor[bookArray.length];
                for(int i = 0; i < bookArray.length; i++) {
                    booksByAuthor[i] = new BookByAuthor(bookArray[i]);
                }
                MySort.selectionSort(booksByAuthor);
                System.out.println("Books sorted by Author (Selection Sort):");
                for(int i = 0; i < booksByAuthor.length; i++) {
                    Book book = booksByAuthor[i].getBook();
                    System.out.println((i+1) + ". ID: " + book.getId() + " | Title: " + 
                        book.getBookTitle() + " | Author: " + book.getAuthor());
                }
                break;
            default:
                System.out.println("Invalid choice!");
                pause();
                return;
        }
        pause();
    }
    
    // Helper class for sorting by Author
    private static class BookByAuthor implements Comparable<BookByAuthor> {
        private Book book;
        public BookByAuthor(Book book) {
            this.book = book;
        }
        public Book getBook() {
            return book;
        }
        @Override
        public int compareTo(BookByAuthor other) {
            return this.book.getAuthor().compareToIgnoreCase(other.book.getAuthor());
        }
    }
    // search book method
    private static void searchBook() {
        clearScreen();
        System.out.println("---------Search Book---------");
        System.out.print("Enter keyword to search (title or author): ");
        String keyword = sc.nextLine().toLowerCase();
        Book[] bookArray = bookList.toArray(new Book[0]);
        boolean found = false;
        for (int i = 0; i < bookArray.length; i++) {
            Book titleCheck = new Book(0, bookArray[i].getBookTitle(), "");
            Book authorCheck = new Book(0, "", bookArray[i].getAuthor());
            //  check book title
            if (bookArray[i].getBookTitle().toLowerCase().contains(keyword)) {
                int index = MySearch.linearSearch(bookArray, bookArray[i]);
                if (index != -1) {
                    System.out.println("Found (Title match) at index " + index + ": ID: " + bookArray[i].getId() +
                            " | Title: " + bookArray[i].getBookTitle() + " | Author: " + bookArray[i].getAuthor());
                    found = true;
                }
            }
            // check book author
            else if (bookArray[i].getAuthor().toLowerCase().contains(keyword)) {
                int index = MySearch.linearSearch(bookArray, bookArray[i]);
                if (index != -1) {
                    System.out.println("Found (Author match) at index " + index + ": ID: " + bookArray[i].getId() +
                            " | Title: " + bookArray[i].getBookTitle() + " | Author: " + bookArray[i].getAuthor());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No book found with keyword: " + keyword);
        }
        pause();
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
            System.out.println("3. Update book");
            System.out.println("4. Delete book");
            System.out.println("5. Search book");
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
                    updateBook();
                    break;
                case "4":
                    deleteBook();
                    break;
                case "5":
                    searchBook();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice!");
                    pause();
            }
        }
    }
    // add book method
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
    // update book method
    private static void updateBook() {
        clearScreen();
        System.out.println("---------Update Book---------");
        if(bookList.isEmpty()) {
            System.out.println("No books to update!");
            pause();
            return;
        }
        viewAllProduct(); 
        System.out.print("\nEnter book ID to update: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Book bookToUpdate = null;
            for(Book book : bookList) {
                if(book.getId() == id) {
                    bookToUpdate = book;
                    break;
                }
            }
            if(bookToUpdate == null) {
                System.out.println("Book not found!");
                pause();
                return;
            }
            System.out.print("Enter new title (leave empty to keep current): ");
            String newTitle = sc.nextLine();
            if(!newTitle.isEmpty()) {
                bookToUpdate.setBookTitle(newTitle);
            }
            System.out.print("Enter new author (leave empty to keep current): ");
            String newAuthor = sc.nextLine();
            if(!newAuthor.isEmpty()) {
                bookToUpdate.setAuthor(newAuthor);
            }
            System.out.println("Book updated successfully!");
        } catch(NumberFormatException e) {
            System.out.println("Invalid ID!");
        }
        pause();
    }
    // delete book method
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
    // order management menu
    private static void orderManagementMenu() {
        while(true) {
            clearScreen();
            System.out.println("---------Order Management (Queue)---------");
            System.out.println("1. Process next order");
            System.out.println("2. View all order history");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();
            switch(choice) {
                case "1":
                    processOrder();
                    break;
                case "2":
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
    // process order from pending to completed
    private static void processOrder() {
        clearScreen();
        System.out.println("---------Process Order---------");
        if(orderQueue.isEmpty()) {
            System.out.println("No orders to process!");
            pause();
            return;
        }
        System.out.println("Pending Orders:");
        MyQueueArrayList<Order> tempQueue = new MyQueueArrayList<>();
        while(!orderQueue.isEmpty()) {
            Order o = orderQueue.poll();
            System.out.println("Order ID: " + o.getId() + " | Customer: " + o.getCustomer().getUsername() + " | Status: " + o.getStatus());
            tempQueue.add(o);
        }
        while(!tempQueue.isEmpty()) orderQueue.add(tempQueue.poll());
        System.out.print("\nEnter Order ID to process: ");
        int orderId;
        try {
            orderId = Integer.parseInt(sc.nextLine());
        } catch(NumberFormatException e) {
            System.out.println("Invalid ID!");
            pause();
            return;
        }
        tempQueue = new MyQueueArrayList<>();
        boolean found = false;
        while(!orderQueue.isEmpty()) {
            Order o = orderQueue.poll();
            if(o.getId() == orderId && !found) {
                if(!o.getStatus().equals("Completed")) {
                    o.setStatus("Completed");
                    orderHistory.push(o);  
                    System.out.println("\nOrder processed successfully!");
                } else {
                    System.out.println("\nThis order has already been processed!");
                }
                found = true;
            } else {
                tempQueue.add(o);  
            }
        }
        while(!tempQueue.isEmpty()) orderQueue.add(tempQueue.poll());
        if(!found) {
            System.out.println("\nOrder ID not found!");
        }
        pause();
    }

    // order history for admin
    private static void orderHistory() {
        clearScreen();
        System.out.println("---------Order History (Stack)---------");
        if(orderHistory.isEmpty()) {
            System.out.println("No order history available!");
            pause();
            return;
        }
        for(int i = orderHistory.size() - 1; i >= 0; i--) {
            Order order = orderHistory.get(i);
            System.out.println(order);
        }
        pause();
    }


}