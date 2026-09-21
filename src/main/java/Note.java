import java.time.LocalDateTime;
import java.util.HashSet;

public class Note {
  private final LocalDateTime date;
  private String title;
  private String body;
  private HashSet<String> tags;

  public Note(String body) {
    if (isEmptyOrNull(body)) {
      throw new IllegalArgumentException("Body must not be empty or null");
    }
    this.date = LocalDateTime.now();
    this.title = null;
    this.body = body;
    this.tags = new HashSet<>();
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    if (title != null && title.isEmpty()) {
        throw new IllegalArgumentException("Title must no be empty");
      }
    this.title = title;
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    if (isEmptyOrNull(body)) {
      throw new IllegalArgumentException("Body must no be empty or null");
    }
    this.body = body;
  }

  public LocalDateTime getDate() {
    return this.date;
  }

  public HashSet<String> getTags() {;
    return new HashSet<>(this.tags);
  }

  public void addTag(String tag) {
    if (isEmptyOrNull(tag)) {
      throw new IllegalArgumentException("Tag must not be empty or null");
    }
    if (!tags.add(tag)) {
      throw new IllegalArgumentException("Tag was already added");
    }
  }

  public void removeTag(String tag) {
    if (!tags.remove(tag)) {
      throw new IllegalArgumentException("Tag does not exist");
    }
  }

  private boolean isEmptyOrNull(String input) {
    return input == null || input.isEmpty();
  }

}

