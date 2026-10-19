import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class Library {
    //region collection
    private final List<Book> books = new ArrayList<>();

    public Library() {}
    public Library(List<Book> initialBooks) { if (initialBooks != null) initialBooks.forEach(this::addBook); }

    public void addBook(Book book) { if (book != null) books.add(book); }
    public List<Book> getBooks() { return List.copyOf(books); }
    //endregion

    //region assignment
    public List<Book> getBooksByAuthor(String author) {
        if (author == null || author.isBlank()) return List.of();
        String target = author.strip();
        return books.stream().filter(b -> b.author().equalsIgnoreCase(target)).toList();
    }

    public static double averagePages(List<Book> books) {
        return books == null ? 0.0 : books.stream().mapToInt(Book::pages).average().orElse(0.0);
    }

    public static List<Book> booksUnderPrice(List<Book> books, double maxPrice) {
        return books == null ? List.of() : books.stream().filter(b -> b.price() <= maxPrice).toList();
    }

    public static Book mostExpensive(List<Book> books) {
        return books == null ? null : books.stream().max(Comparator.comparingDouble(Book::price)).orElse(null);
    }

    public static List<Book> booksWithMinPages(List<Book> books, int minPages) {
        return books == null ? List.of() : books.stream().filter(b -> b.pages() >= minPages).toList();
    }

    public static List<Book> bestPairWithinBudget(List<Book> books, double budget) {
        if (books == null || books.size() < 2) return List.of();
        record Pair(Book a, Book b) {
            double price() { return a.price() + b.price(); }
            int pages() { return a.pages() + b.pages(); }
        }

        return IntStream.range(0, books.size()).boxed()
                .flatMap(i -> IntStream.range(i + 1, books.size()).mapToObj(j -> new Pair(books.get(i), books.get(j))))
                .filter(p -> !p.a().equals(p.b()) && p.price() <= budget)
                .max(Comparator.comparingDouble(Pair::price).thenComparingInt(Pair::pages))
                .map(p -> List.of(p.a(), p.b()))
                .orElseGet(List::of);
    }
    //endregion
}