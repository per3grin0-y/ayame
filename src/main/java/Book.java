import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Book {
    private String id;
    private String title;
    private String isbn;
    private LinkedHashSet<String> authors;
    private int pages;
    private PartialDate pubDate; //Custom type to be created later
    private String description;
    private int rating;
    private HashSet<String> tags;
    private ArrayList<Note> notes; //Custom type to be created later
}
