import java.util.Objects;
import java.util.OptionalInt;

public class Shelf {
  public static final int SLOTS_PER_SHELF = 125;
  private Book[] slots;

  public Shelf() {
    this.slots = new Book[SLOTS_PER_SHELF];
  }

  public void shelve(Book book, int slot) {
    Objects.requireNonNull(book, "Book must not be null");
    int slotIndex = toSlotIndex(slot);
    if (this.slots[slotIndex] != null) {
      throw new IllegalStateException("Slot is already occupied.");
    }
    this.slots[slotIndex] = book;
  }

  public void unshelve(int slot) {
    int slotIndex = toSlotIndex(slot);
    if (this.slots[slotIndex] == null) {
      throw new IllegalStateException("Slot is already empty.");
    }
    this.slots[slotIndex] = null;
  }

  public Book getBook(int slot){
    int slotIndex = toSlotIndex(slot);
    return this.slots[slotIndex];
  }

  private int toSlotIndex(int slot){
    if (slot < 1 || slot > SLOTS_PER_SHELF) {
      throw new IndexOutOfBoundsException("Slot index must be between 1 and " + SLOTS_PER_SHELF);
    }
    return slot - 1;
  }

  public OptionalInt findFreeSlotInShelf() {
    for (int i = 1; i <= SLOTS_PER_SHELF; i++) {
      if (this.slots[i - 1] == null) {
        return OptionalInt.of(i);
      }
    }
    return OptionalInt.empty();
  }
}
