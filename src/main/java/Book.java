import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Book {
  private final String id;
  private String isbn;
  private String title;
  private LinkedHashSet<String> authors;
  private String description;
  private Integer rating;
  private Integer pages;
  private PartialDate pubDate;
  private HashSet<String> tags;
  private ArrayList<Note> notes;

  public Book(String id, String title) {
    if (isEmptyOrNull(id)) {
      throw new IllegalArgumentException("ID must not be null");
    }
    if (isEmptyOrNull(title)) {
      throw new IllegalArgumentException("Title must not be null");
    }
    this.id = id;
    this.isbn = null;
    this.title = title;
    this.authors = new LinkedHashSet<>();
    this.description = null;
    this.rating = null;
    this.pages = null;
    this.pubDate = null;
    this.tags = new HashSet<>();
    this.notes = new ArrayList<>();
  }

  public String getId() {
    return this.id;
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

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    if (isEmptyOrNull(title)) {
      throw new IllegalArgumentException("Title must not be null");
    }
    this.title = title;
  }

  public LinkedHashSet<String> getAuthors() {;
    return new LinkedHashSet<>(this.authors);
  }

  public void addAuthor(String author) {
    if (isEmptyOrNull(author)) {
      throw new IllegalArgumentException("Author must not be empty or null");
    }
    if (!authors.add(author)) {
      throw new IllegalArgumentException("Author was already added");
    }
  }

  public void removeAuthor(String author) {
    if (!authors.remove(author)) {
      throw new IllegalArgumentException("Author does not exist");
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

  public PartialDate getPubDate() {
    return this.pubDate;
  }

  public void setPubDate(PartialDate date) {
    this.pubDate = date;
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

  public HashSet<String> getTags() {;
    return new HashSet<>(this.tags);
  }

  public void addTag(String tag) {
    if (isEmptyOrNull(tag)) {
      throw new IllegalArgumentException("Tag must not be empty or null");
    }
    if (!tags.add(tag)) {
      throw new IllegalArgumentException("Tag was already added");
    }
  }

  public void removeTag(String tag) {
    if (!tags.remove(tag)) {
      throw new IllegalArgumentException("Tag does not exist");
    }
  }

  public ArrayList<Note> getNotes() {;
    return new ArrayList<>(this.notes);
  }

  public void addNote(Note note) {
    if (note == null) {
      throw new IllegalArgumentException("Note must not be null");
    }
    notes.add(note);
  }

  public void removeNote(int index) {
    notes.remove(index);
  }

  private boolean isEmptyOrNull(String input) {
    return input == null || input.isEmpty();
  }
}