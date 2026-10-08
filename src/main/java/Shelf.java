public class Shelf {
  public static final int SHELF_MAX = 125;
  private String[] shelf;

  public Shelf() {
    this.shelf = new String[SHELF_MAX];
  }

  public boolean shelveAt(String id, int slot) {
    if (this.shelf[slot - 1] != null) {
      return false;
    }
    this.shelf[slot - 1] = id;
    return true;
  }

  public boolean release(String id) {
    for (int i = 0; i < SHELF_MAX; i++) {
      if (this.shelf[i] != null && this.shelf[i].equals(id)) {
        this.shelf[i] = null;
        return true;
      }
    }
    return false;
  }

  public Integer findFreeSlot() {
    for (int i = 0; i < SHELF_MAX; i++) {
      if (this.shelf[i] == null) {
        return i + 1;
      }
    }
    return null;
  }
}