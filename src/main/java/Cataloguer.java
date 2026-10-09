import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cataloguer {
  private Map<String, Book> catalogue;
  private Library library;

  public Cataloguer(Library library) {
    this.catalogue = new HashMap<>();
    this.library = library;
  }

  public String addBook(String title) {
    String id = this.library.autoShelve();
    try {
      this.catalogue.put(id, new Book(id, title));
    } catch (IllegalArgumentException e) {
      this.library.release(id);
      throw e;
    }
    return id;
  }

  public void removeBook(String id) {
    this.library.validateId(id);
    if (!catalogue.containsKey(id)) {
      throw new IllegalArgumentException("Book with ID '" + id + "' is unknown");
    }
    library.release(id);
    catalogue.remove(id);
  }

  public Book getBook(String id) {
    this.library.validateId(id);
    Book bk = this.catalogue.get(id);
    if (bk == null) {
      throw new IllegalArgumentException("Book with id '" + id + "' is unknown");
    }
    return bk;
  }

  public List<Book> listBooks() {
    return List.copyOf(catalogue.values());
  }
}
