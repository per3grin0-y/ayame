public record Location(int section, int shelf, int slot) {
  public Location{
    if (section < 1 || shelf < 1 || slot < 1){
      throw new IllegalArgumentException(
          "Location indices must be positive"
      );
    } else if (shelf > Section.SHELVES_PER_SECTION){
      throw new IllegalArgumentException(
          "Shelf index must not be greater than " + Section.SHELVES_PER_SECTION
      );
    } else if (slot > Shelf.SLOTS_PER_SHELF){
      throw new IllegalArgumentException(
          "Slot index must not be greater than " + Shelf.SLOTS_PER_SHELF
      );
    }
  }
}