import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CataloguerTest {
  private Library lib;
  private Cataloguer cat;

  @BeforeEach
  void setUp() {
    lib = new Library();
    cat = new Cataloguer(lib);
  }

  @Test
  void addBookWithEmptyOrNullTitleThrows() {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cat.addBook(""));
    assertTrue(e.getMessage().contains("Title must not be"));
    IllegalArgumentException f = assertThrows(IllegalArgumentException.class, () -> cat.addBook(null));
    assertTrue(f.getMessage().contains("Title must not be"));
  }

  @Test
  void addBookTitleThrowFreesItsSlot() {
    assertThrows(IllegalArgumentException.class, () -> cat.addBook(""));
    assertEquals("AYA.S1.S1.S001", cat.addBook("LOTR"));
  }

  @Test
  void getBookAfterRemoveThrows() {
    String id = cat.addBook("LOTR");
    cat.removeBook(id);
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cat.getBook(id));
    assertTrue(e.getMessage().contains("is unknown"));
  }

  @Test
  void removeBookFreesItsSlot() {
    String id = cat.addBook("LOTR");
    cat.removeBook(id);
    assertEquals(id, cat.addBook("Dune"));
  }

  @Test
  void removeBookWithUnknownIdThrows() {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cat.removeBook("AYA.S1.S1.S001"));
    assertTrue(e.getMessage().contains("is unknown"));
  }

  @Test
  void getBookReturnsWhatWasAdded() {
    String id = cat.addBook("LOTR");
    Book bk = cat.getBook(id);
    assertEquals(id, bk.getId());
  }

}
