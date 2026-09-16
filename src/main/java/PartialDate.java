import java.time.Year;
import java.util.Comparator;

public class PartialDate implements Comparable<PartialDate> {
  private static final Comparator<Integer> byMonth = Comparator.nullsFirst(Comparator.naturalOrder());
  private static final Comparator<Integer> byDay = Comparator.nullsFirst(Comparator.naturalOrder());
  private int year;
  private Integer month;
  private Integer day;

  public PartialDate(int year, Integer month, Integer day) {
    checkYearBoundary(year);
    checkMonthBoundary(month);
    checkDayBoundary(day);
    checkDayHasMonth(month, day);
    this.year = year;
    this.month = month;
    this.day = day;
  }

  public PartialDate(int year, Integer month) {
    this(year, month, null);
  }

  public PartialDate(int year) {
    this(year, null, null);
  }

  public Integer getYear() {
    return this.year;
  }

  public void setYear(int year) {
    checkYearBoundary(year);
    this.year = year;
  }

  public Integer getMonth() {
    return this.month;
  }

  public void setMonth(Integer month) {
    checkMonthBoundary(month);
    checkDayHasMonth(month, this.day);
    this.month = month;
  }

  public Integer getDay() {
    return this.day;
  }

  public void setDay(Integer day) {
    checkDayBoundary(day);
    checkDayHasMonth(this.month, day);
    this.day = day;
  }

  private void checkYearBoundary(int year) {
    int currentYear = Year.now().getValue();
    if (year > currentYear || year < 0) {
      throw new IllegalArgumentException("Constraint: 0 < year < currentYear");
    }
  }

  private void checkMonthBoundary(Integer month) {
    if (month != null && (month < 1 || month > 12)) {
      throw new IllegalArgumentException("Constraint: 1 < month < 12");
    }
  }

  private void checkDayBoundary(Integer day) {
    if (day != null && (day < 1 || day > 31)) {
      throw new IllegalArgumentException("Constraint: 1 < day < 31");
    }
  }

  private void checkDayHasMonth(Integer month, Integer day) {
    if (month == null && day != null) {
      throw new IllegalArgumentException("Cannot have a day without a month");
    }
  }

  @Override
  public int compareTo(PartialDate other) {
    int yearDiff = this.year - other.getYear();
    if (yearDiff != 0) {
      return yearDiff;
    }

    int monthDiff = byMonth.compare(this.month, other.getMonth());
    if (monthDiff != 0) {
      return monthDiff;
    }

    return byDay.compare(this.day, other.getDay());
  }
}
