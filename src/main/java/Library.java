import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Library {
  private static final String ID_PREFIX = "AYA";
  private static final String DATA_MARKER = "S";
  private static final Pattern FORMAT = Pattern.compile(
      ID_PREFIX
          + "\\." + DATA_MARKER + "([1-9]\\d{0,8})"
          + "\\." + DATA_MARKER + "([1-9])"
          + "\\." + DATA_MARKER + "((?!000)\\d{3})");
  private ArrayList<Section> library;

  public Library() {
    this.library = new ArrayList<>();
    library.add(new Section());
  }

  private static String buildId(int[] slot) {
    return ID_PREFIX + "." + DATA_MARKER + slot[0] + "." + DATA_MARKER + slot[1] + "." + DATA_MARKER + pad(slot[2]);
  }

  private static String pad(int n) {
    if (n < 10) {
      return "00" + n;
    } else if (n < 100) {
      return "0" + n;
    } else {
      return String.valueOf(n);
    }
  }

  public String autoShelve() {
    int[] data = findFreeSlot();
    int section = data[0];
    int shelf = data[1];
    int slot = data[2];
    String id = buildId(data);
    if (!this.library.get(section - 1).shelve(id, shelf, slot)) {
      throw new IllegalStateException("Slot was reported free but could not be claimed");
    }
    return id;
  }

  public boolean release(String id) {
    int[] parsed = checkId(id);
    return this.library.get(parsed[0] - 1).release(id, parsed[1]);
  }

  /**
   * Returns {section, shelf, slot} for the first free slot, all are 1-based.
   * If no Section is free, add a new Section and return its first slot.
   */
  private int[] findFreeSlot() {
    for (int i = 1; i <= this.library.size(); i++) {
      int[] slot = this.library.get(i - 1).findFreeSlot();
      if (slot != null) {
        return new int[]{i, slot[0], slot[1]};
      }
    }
    //If every section is full
    this.library.add(new Section());
    int[] slot = this.library.getLast().findFreeSlot();
    return new int[]{this.library.size(), slot[0], slot[1]};
  }

  private int[] checkId(String id) {
    if (id == null) {
      throw new IllegalArgumentException("ID must not be null");
    }
    Matcher m = FORMAT.matcher(id);
    if (!m.matches()) {
      throw new IllegalArgumentException("ID is malformed: " + id + ". "
          + "Expected a format like " + ID_PREFIX + "." + DATA_MARKER + "5" + "."
          + DATA_MARKER + "1" + "." + DATA_MARKER + "067");
    }
    int section = Integer.parseInt(m.group(1));
    if (section > this.library.size()) {
      throw new IllegalArgumentException("Section must be at most" + this.library.size()
          + ". Input: " + section);
    }
    int shelf = Integer.parseInt(m.group(2));
    if (shelf > Section.SECTION_MAX) {
      throw new IllegalArgumentException("Shelf must be at most " + Section.SECTION_MAX
          + ". Input: " + shelf);
    }
    int slot = Integer.parseInt(m.group(3));
    if (slot > Shelf.SHELF_MAX) {
      throw new IllegalArgumentException("Slot must be at most " + Shelf.SHELF_MAX
          + ". Input: " + slot);
    }
    return new int[]{section, shelf, slot};
  }
}
