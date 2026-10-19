import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class Library {
  private final List<Section> library;
  private final BiMap<Location, Book> index;

  public Library() {
    this.library = new ArrayList<>();
    this.library.add(new Section());
    this.index = HashBiMap.create();
  }

  /**
   * Adds a book at the specified location.
   *
   * @param book the book to shelve
   * @param location where the book will be shelved
   * @return the location where the book was shelved
   * @throws NullPointerException if the book or location is null
   * @throws IndexOutOfBoundsException if the location contains invalid indices
   * @throws IllegalArgumentException if the book has already been added
   * @throws IllegalStateException if the location's slot is already occupied
   */
  public Location shelve(Book book, Location location) {
    Objects.requireNonNull(book, "Book must not be null");
    Objects.requireNonNull(location, "Location must not be null.");
    int sectionIndex = toSectionIndex(location.section());
    if (retrieve(location) != null) {
      throw new IllegalStateException("Location is already occupied.");
    }
    if (this.index.containsValue(book)) {
      throw new IllegalArgumentException("Book has already been added.");
    }

    //Shelve
    this.library.get(sectionIndex)
        .shelve(book, location.shelf(), location.slot());

    //Update index
    this.index.put(location, book);

    return location;
  }

  /**
   * Adds a book in the first free slot in the library.
   * If all existing sections are full, a new section is created.
   *
   * @param book the book to shelve
   * @return the location where the book was shelved
   * @throws NullPointerException if the book is null
   * @throws IllegalArgumentException if the book has already been added
   * @throws IllegalStateException if library failed to resize
   */
  public Location shelve(Book book) {
    Objects.requireNonNull(book, "Book must not be null.");
    if (this.index.containsValue(book)) {
      throw new IllegalArgumentException("Book has already been added.");
    }
    Location location = findFreeSlotInLibrary();
    return shelve(book, location);
  }

  /**
   * Removes book from the specified location.
   *
   * @param location the location where the book is shelved
   * @throws NullPointerException if the location is null
   * @throws IndexOutOfBoundsException if the location contains invalid indices
   * @throws IllegalStateException if no book exists at the location
   */
  public void unshelve(Location location) {
    Objects.requireNonNull(location, "Location must not be null.");
    int sectionIndex = toSectionIndex(location.section());
    if (retrieve(location) == null) {
      throw new IllegalStateException("No book exists at the specified location.");
    }

    //Unshelve
    this.library.get(sectionIndex)
        .unshelve(location.shelf(), location.slot());

    //Update index
    this.index.remove(location);
  }

  /**
   * Moves a book from its origin location to its destination location.
   *
   * @param origin the book's current location
   * @param destination the location to which the book is to be moved
   * @throws NullPointerException if origin or destination is {@code null}
   * @throws IndexOutOfBoundsException if origin or destination contains invalid indices
   * @throws IllegalArgumentException if origin equals destination
   * @throws IllegalStateException if the origin slot is empty or destination slot is occupied
   */
  public void move(Location origin, Location destination) {
    Objects.requireNonNull(origin, "Origin must not be null.");
    Objects.requireNonNull(destination, "Destination must not be null.");
    if (origin.equals(destination)) {
      throw new IllegalArgumentException("Origin and destination must not be equal.");
    }

    //Retrieve will throw with invalid indices
    Book book = retrieve(origin);
    if (book == null) {
      throw new IllegalStateException("Origin is empty.");
    }
    if (retrieve(destination) != null) {
      throw new IllegalStateException("Destination is occupied.");
    }

    int originSectionIndex = toSectionIndex(origin.section());
    int destinationSectionIndex = toSectionIndex(destination.section());

    //Relocate
    this.library.get(originSectionIndex)
        .unshelve(origin.shelf(), origin.slot());

    this.library.get(destinationSectionIndex)
        .shelve(book, destination.shelf(), destination.slot());

    //Update index
    this.index.remove(origin);
    this.index.put(destination, book);
  }

  /**
   * Retrieves the book stored at the specified location, if any.
   *
   * @param location the location from which the book is to be retrieved
   * @return the book stored at the specified location, or {@code null} if none is present
   * @throws IndexOutOfBoundsException if the location contains invalid indices
   */
  public Book retrieve(Location location) {
    Objects.requireNonNull(location, "Location must not be null.");
    int sectionIndex = toSectionIndex(location.section());
    return this.library.get(sectionIndex)
        .getShelf(location.shelf()).getBook(location.slot());
  }

  public List<Book> getAllBooks() {
    return new ArrayList<>(this.index.values());
  }

  public List<Book> searchByTitle(String query) {
    validateQuery(query);
    List<Book> books = new ArrayList<>();
    for (Book book : this.index.values()) {
      if (book.getTitle().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
        books.add(book);
      }
    }
    return books;
  }

  public List<Book> searchByAuthor(String query) {
    validateQuery(query);
    String normalQuery = query.toLowerCase(Locale.ROOT);
    List<Book> books = new ArrayList<>();
    for (Book book : this.index.values()) {
      for (String author : book.getAuthors()) {
        if (author.toLowerCase(Locale.ROOT).contains(normalQuery)) {
          books.add(book);
          break;
        }
      }
    }
    return books;
  }

  public List<Book> searchByIsbn(String query) {
    validateQuery(query);
    String normalQuery = query.replaceAll("[\\s-]", "");
    List<Book> books = new ArrayList<>();
    for (Book book : this.index.values()) {
      String bookIsbn = book.getIsbn();
      if (bookIsbn != null) {
        String normalBookIsbn = bookIsbn.replaceAll("[\\s-]", "");
        if (normalBookIsbn.equals(normalQuery)) {
          books.add(book);
        }
      }
    }
    return books;
  }

  private int toSectionIndex(int section) {
    if (section < 1 || section > this.library.size()) {
      throw new IndexOutOfBoundsException("Section must be between 1 and " + this.library.size() + ".");
    }
    return section - 1;
  }

  /**
   * Searches through the entire library to find the first free slot.
   * If it reaches the library's size without finding one, it creates another section and returns
   * its first free slot
   * @return location of the first free slot
   * @throws IllegalStateException if the library failed to resize
   */
  private Location findFreeSlotInLibrary() {
    for (int i = 1; i <= this.library.size(); i++) {
      Optional<Section.ShelfLocation> shelfLocation = this.library.get(i - 1).findFreeSlotInSection();
      if (shelfLocation.isPresent()) {
        int shelf = shelfLocation.get().shelf();
        int slot = shelfLocation.get().slot();
        return new Location(i, shelf, slot);
      }
    }
    this.library.add(new Section());
    Optional<Section.ShelfLocation> freshLocation = this.library.getLast().findFreeSlotInSection();
    if (freshLocation.isPresent()) {
      int shelf = freshLocation.get().shelf();
      int slot = freshLocation.get().slot();
      return new Location(this.library.size(), shelf, slot);
    }
    throw new IllegalStateException("library resizing failed.");
  }

  private void validateQuery(String query) {
    if (query == null || query.isBlank()) {
      throw new IllegalArgumentException("Query item must not be empty or null");
    }
  }
}
