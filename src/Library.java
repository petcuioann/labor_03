import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Library {
    private final List<Book> books = new ArrayList<>();

    public Library() {}
    public Library(List<Book> initialBooks) { if (initialBooks != null) initialBooks.forEach(this::addBook); }
    public void addBook(Book book) { if (book != null) books.add(book); }
    public List<Book> getBooks() { return List.copyOf(books); }

    public List<Book> getBooksByAuthor(String author) { return getBooksByAuthor(this.books, author); }
    public double averagePages() { return averagePages(this.books); }
    public List<Book> booksUnderPrice(double maxPrice) { return booksUnderPrice(this.books, maxPrice); }
    public Book mostExpensive() { return mostExpensive(this.books); }
    public List<Book> booksWithMinPages(int minPages) { return booksWithMinPages(this.books, minPages); }
    public List<Book> bestPairWithinBudget(double budget) { return bestPairWithinBudget(this.books, budget); }

    public static List<Book> getBooksByAuthor(List<Book> books, String author) {
        if (author == null || author.isBlank()) return List.of();
        String target = author.strip();
        return valid(books).filter(b -> b.getAuthor().equalsIgnoreCase(target)).toList();
    }

    public static double averagePages(List<Book> books) {
        return valid(books).mapToInt(Book::getPages).average().orElse(0.0);
    }

    public static List<Book> booksUnderPrice(List<Book> books, double maxPrice) {
        return valid(books).filter(b -> b.getPrice() <= maxPrice).toList();
    }

    public static Book mostExpensive(List<Book> books) {
        return valid(books).max(Comparator.comparingDouble(Book::getPrice)).orElse(null);
    }

    public static List<Book> booksWithMinPages(List<Book> books, int minPages) {
        return valid(books).filter(b -> b.getPages() >= minPages).toList();
    }

    public static List<Book> bestPairWithinBudget(List<Book> books, double budget) {
        List<Book> clean = valid(books).distinct().toList();
        record Pair(Book a, Book b) {
            double getPrice() { return a.getPrice() + b.getPrice(); }
            int getPages() { return a.getPages() + b.getPages(); }
        }
        return IntStream.range(0, clean.size()).boxed()
                .flatMap(i -> IntStream.range(i + 1, clean.size())
                        .mapToObj(j -> new Pair(clean.get(i), clean.get(j))))
                .filter(p -> p.getPrice() <= budget)
                .max(Comparator.comparingDouble(Pair::getPrice).thenComparingInt(Pair::getPages))
                .map(p -> List.of(p.a(), p.b()))
                .orElseGet(List::of);
    }

    private static Stream<Book> valid(List<Book> books) {
        return books == null ? Stream.empty() : books.stream().filter(Objects::nonNull);
    }
}