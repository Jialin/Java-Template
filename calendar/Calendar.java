package template.calendar;

public class Calendar {

  /** Compute weekday of the day. NOTE: 0 for Sunday, 1 for Monday, etc. */
  public static int weekday(int year, int month, int day) {
    year = month >= 3 ? year : (year - 1);
    month = month >= 3 ? (month - 2) : (month + 10);
    return (year + (year >> 2) - year / 100 + year / 400 + (13 * month - 1) / 5 + day) % 7;
  }
}
