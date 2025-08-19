import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
class Book {
    int id;
    String title;
    String author;
    boolean isIssued;

    public Book(int id, String title, String author, boolean isIssued) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = isIssued;
    }

    public void displayBook() {
        System.out.println("ID: " + id + " | Title: " + title + " | Author: " + author + " | Issued: " + (isIssued ? "Yes" : "No"));
    }

    // Convert to text format
    public String toFileString() {
        return id + "," + title + "," + author + "," + isIssued;
    }

    // Convert from text format
    public static Book fromFileString(String line) {
        String[] parts = line.split(",");
        return new Book(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2],
            Boolean.parseBoolean(parts[3])
        );
    }
}

class Library {
    ArrayList<Book> books = new ArrayList<>();
    String fileName = "books.txt";

    public Library() {
        loadBooksFromFile();
    }

    public void addBook(Book b) {
        books.add(b);
        saveBooksToFile();
        System.out.println("Book added successfully!");
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available!");
            return;
        }
        for (Book b : books) {
            b.displayBook();
        }
    }

    public void issueBook(int id) {
        for (Book b : books) {
            if (b.id == id && !b.isIssued) {
                b.isIssued = true;
                saveBooksToFile();
                System.out.println("Book issued successfully!");
                return;
            }
        }
        System.out.println("Book not available or already issued.");
    }

    public void returnBook(int id) {
        for (Book b : books) {
            if (b.id == id && b.isIssued) {
                b.isIssued = false;
                saveBooksToFile();
                System.out.println("Book returned successfully!");
                return;
            }
        }
        System.out.println("Invalid book ID or book was not issued.");
    }

    // Save books to a file
    private void saveBooksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Book b : books) {
                writer.write(b.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    // Load books from a file
    private void loadBooksFromFile() {
        File file = new File(fileName);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                books.add(Book.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }
}

public class Basicproject {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author name: ");
                    String author = sc.nextLine();
                    library.addBook(new Book(id, title, author, false));
                    break;

                case 2:
                    library.displayBooks();
                    break;

                case 3:
                    System.out.print("Enter book ID to issue: ");
                    int issueId = sc.nextInt();
                    library.issueBook(issueId);
                    break;

                case 4:
                    System.out.print("Enter book ID to return: ");
                    int returnId = sc.nextInt();
                    library.returnBook(returnId);
                    break;

                case 5:
                    System.out.println("Exiting... Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);

        sc.close();
    }
}
