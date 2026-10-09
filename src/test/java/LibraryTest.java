import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
  private Library lib;

  @BeforeEach
  void setUp() {
    lib = new Library();
  }

  @Test
  void autoShelveFirstIdIsCorrect() {
    assertEquals("AYA.S1.S1.S001", lib.autoShelve());
  }

  @Test
  void autoShelveReusesReleasedSlot() {
    String s = lib.autoShelve();
    lib.release(s);
    assertEquals(s, lib.autoShelve());
  }

  @Test
  void autoShelveBuildsSectionsWhenFull() {
    for (int i = 0; i < 1000; i++) {
      lib.autoShelve();
    }
    assertEquals("AYA.S2.S1.S001", lib.autoShelve());
  }

  @Test
  void everySlotInSectionCanBeReleased() {
    String[] ids = new String[1000];
    for (int i = 0; i < 1000; i++) {
      ids[i] = lib.autoShelve();
    }
    for (int i = 0; i < 1000; i++){
      lib.release(ids[i]);
    }
  }

  @Test
  void releaseUnshelvedIdThrows() {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> lib.release("AYA.S1.S1.S001"));
    assertTrue(e.getMessage().contains("ID is not shelved"));
  }

  @Test
  void releaseNullThrows(){
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> lib.release(null));
    assertTrue(e.getMessage().contains("ID must not be null"));
  }

  @Test
  void releaseMalformedIdThrows() {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> lib.release("banana"));
    assertTrue(e.getMessage().contains("ID is malformed"));
  }

  @Test
  void releaseSectionOutOfRangeThrows() {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> lib.release("AYA.S0.S0.S001"));
    assertTrue(e.getMessage().contains("ID is malformed"));
    IllegalArgumentException f = assertThrows(IllegalArgumentException.class, () -> lib.release("AYA.S2.S1.S001"));
    assertTrue(f.getMessage().contains("Section must be at most"));
  }

  @Test
  void releaseShelfOutOfRangeThrows() {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> lib.release("AYA.S1.S0.S001"));
    assertTrue(e.getMessage().contains("ID is malformed"));
    IllegalArgumentException f = assertThrows(IllegalArgumentException.class, () -> lib.release("AYA.S1.S9.S001"));
    assertTrue(f.getMessage().contains("Shelf must be at most"));
  }

  @Test
  void releaseSlotOutOfRangeThrows() {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> lib.release("AYA.S1.S1.S000"));
    assertTrue(e.getMessage().contains("ID is malformed"));
    IllegalArgumentException f = assertThrows(IllegalArgumentException.class, () -> lib.release("AYA.S1.S1.S126"));
    assertTrue(f.getMessage().contains("Slot must be at most"));
  }

  @Test
  void releaseTwiceThrows() {
    String s = lib.autoShelve();
    lib.release(s);
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> lib.release(s));
    assertTrue(e.getMessage().contains("ID is not shelved"));
  }
}
