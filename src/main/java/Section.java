public class Section {
  public static final int SECTION_MAX = 8;
  private Shelf[] section;

  public Section() {
    this.section = new Shelf[SECTION_MAX];
    for (int i = 0; i < this.section.length; i++) {
      this.section[i] = new Shelf();
    }
  }

  public boolean shelve(String id, int shelf, int slot) {
    return this.section[shelf - 1].shelveAt(id, slot);
  }

  public boolean release(String id, int shelf) {
    return this.section[shelf - 1].release(id);
  }

  /**
   * Returns {shelf, slot} for the first free slot, both are 1-based.
   */
  public int[] findFreeSlot() {
    for (int i = 1; i <= SECTION_MAX; i++) {
      Integer slot = this.section[i - 1].findFreeSlot();
      if (slot != null) {
        return new int[]{i, slot};
      }
    }
    return null;
  }
}

