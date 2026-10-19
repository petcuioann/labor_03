import java.util.List;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();

        Book b1 = new Book("Clean Code", "Robert Martin", 45, 464);
        Book b2 = new Book("1984", "George Orwell", 18, 328);
        Book b3 = new Book("Animal Farm", "George Orwell", 12, 112);
        Book b4 = new Book("Java Basics", "Max Müller", 35, 520);
        Book b5 = new Book("The Hobbit", "J. R. R. Tolkien", 28, 310);

        lib.addBook(b1);
        lib.addBook(b2);
        lib.addBook(b3);
        lib.addBook(b4);
        lib.addBook(b5);

        List<Book> all = lib.getBooks();

        System.out.println("1. George Orwell: " + lib.getBooksByAuthor("George Orwell"));
        System.out.printf("2. avg pages (b1,b2,b3): %.2f\n", Library.averagePages(List.of(b1, b2, b3)));
        System.out.println("3. max 30 €: " + Library.booksUnderPrice(all, 30));
        System.out.println("4. most expensive (b2,b3,b5): " + Library.mostExpensive(List.of(b2, b3, b5)));
        System.out.println("5. min 300 pages: " + Library.booksWithMinPages(all, 300));
        System.out.println("6. budget 55 €: " + Library.bestPairWithinBudget(all, 55));
    }
}
