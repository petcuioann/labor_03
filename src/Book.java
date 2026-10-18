import java.util.Locale;

public record Book(String title, String author, double price, int pages) {
    public Book {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("title must not be blank");
        if (author == null || author.isBlank())
            throw new IllegalArgumentException("author must not be blank");
        if (Double.isNaN(price) || Double.isInfinite(price) || price < 0.0)
            throw new IllegalArgumentException("price must be finite and non-negative");
        if (pages <= 0)
            throw new IllegalArgumentException("pages must be strictly positive");

        title = title.strip();
        author = author.strip();
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public int getPages() { return pages; }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s (%s, %.2f €, %d p.)", title, author, price, pages);
    }
}
