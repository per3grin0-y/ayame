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

    public Book(String id, String title){
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
}
