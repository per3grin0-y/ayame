import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

public class Section {
  public static final int SHELVES_PER_SECTION = 8;
  private Shelf[] shelves;

  public Section() {
    this.shelves = new Shelf[SHELVES_PER_SECTION];
    for (int i = 0; i < SHELVES_PER_SECTION; i++) {
      this.shelves[i] = new Shelf();
    }
  }


  public void shelve(Book book, int shelf, int slot) {
    Objects.requireNonNull(book, "Book must not be null");
    int shelfIndex = toShelfIndex(shelf);
    this.shelves[shelfIndex].shelve(book, slot);
  }

  public void unshelve(int shelf, int slot) {
    int shelfIndex = toShelfIndex(shelf);
    this.shelves[shelfIndex].unshelve(slot);
  }

  public Shelf getShelf(int shelf) {
    int shelfIndex = toShelfIndex(shelf);
    return this.shelves[shelfIndex];
  }

  private int toShelfIndex(int shelf) {
    if (shelf < 1 || shelf > SHELVES_PER_SECTION) {
      throw new IndexOutOfBoundsException("Shelf index must be between 1 and " + SHELVES_PER_SECTION);
    }
    return shelf - 1;
  }

  public Optional<ShelfLocation> findFreeSlotInSection() {
    for (int i = 1; i <= SHELVES_PER_SECTION; i++) {
      OptionalInt shelfSlot = this.shelves[i - 1].findFreeSlotInShelf();
      if (shelfSlot.isPresent()) {
        return Optional.of(new ShelfLocation(i, shelfSlot.getAsInt()));
      }
    }
    return Optional.empty();
  }

  public record ShelfLocation(int shelf, int slot) {
  }
}
