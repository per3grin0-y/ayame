import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Book {
    private String id;
    private String title;
    private String isbn;
    private LinkedHashSet<String> authors;
    private Integer pages;
    private PartialDate pubDate; //Custom type to be created later
    private String description;
    private Integer rating;
    private HashSet<String> tags;
    private ArrayList<Note> notes; //Custom type to be created later

    public Book(String id, String title) {
        if (isEmpty(id)) throw new IllegalArgumentException("ID must not be null");
        if (isEmpty(title)) throw new IllegalArgumentException("Title must not be null");
        this.id = id;
        this.title = title;
        this.isbn = null;
        this.authors = new LinkedHashSet<>();
        this.pages = null;
        this.pubDate = null;
        this.description = null;
        this.rating = null;
        this.tags = new HashSet<>();
        this.notes = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        if (isEmpty(title)) throw new IllegalArgumentException("Title must not be null");
        this.title = title;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {
        if (rating == null) {
            this.rating = null;
        } else if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be null or between 1 and 5");
        }
    }

    public Integer getPages() {
        return this.pages;
    }

    public void setPages(Integer pages) {
        if (pages == null) {
            this.pages = null;
        } else if (pages > 0) {
            this.pages = pages;
        } else {
            throw new IllegalArgumentException("Pages must be null or greater than 0");
        }
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null) {
            this.isbn = null;
        } else {
            String normalIsbn = isbn.replace("-", "");
            if (normalIsbn.length() == 10 || normalIsbn.length() == 13) {
                this.isbn = normalIsbn;
            } else {
                throw new IllegalArgumentException("ISBN must be 10 or 13-characters long");
            }
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        if (description == null) {
            this.description = null;
        } else if (description.isEmpty()) {
            throw new IllegalArgumentException("Description can be null, but not empty");
        } else {
            this.description = description;
        }
    }

    private boolean isEmpty(String input) {
        return input == null || input.isEmpty();
    }
}
