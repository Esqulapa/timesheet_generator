package pl.jarekzegzula.DateTypes;

import pl.jarekzegzula.WorkingDays;
import pl.jarekzegzula.WorkingHours;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class Day {

  private final Integer dayOfTheMonth;

  private final WorkingHours numberOfWorkingHours;

  private final DayOfWeek dayOfWeek;

  private final LocalDate date;

  private final WorkingDays workingDays;

  private final Locale locale;

  public Day(
      Integer dayOfTheMonth,
      WorkingHours numberOfWorkingHours,
      DayOfWeek dayOfWeek,
      LocalDate date,
      WorkingDays workingDays,
      Locale locale) {
    this.dayOfTheMonth = dayOfTheMonth;
    this.dayOfWeek = date.getDayOfWeek();
    this.locale = locale;
    this.numberOfWorkingHours =
        this.isWorkingDay(workingDays) ? numberOfWorkingHours : new WorkingHours(0);
    this.date = date;
    this.workingDays = workingDays;
  }

  public Boolean isWorkingDay(WorkingDays workingDays) {

    return workingDays.workingDays().contains(this.dayOfWeek);
  }

  public Locale getLocale() {
    return locale;
  }

  public String isWorkingDayToString(WorkingDays workingDays) {

    return String.valueOf(isWorkingDay(workingDays));
  }

  public WorkingDays getWorkingDays() {
    return workingDays;
  }

  public Integer getNumberOfWorkingHours() {

    return numberOfWorkingHours.value();
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public String getDayOfWeekInSpecificLanguage() {
    return dayOfWeek.getDisplayName(TextStyle.FULL, locale).toUpperCase();
  }

  public LocalDate getDate() {
    return date;
  }

  @Override
  public String toString() {

    return String.format(
        "%n|%-15s|%-15s|%10s|%-15s| ",
        this.date,
        this.dayOfWeek,
        this.getNumberOfWorkingHours(),
        isWorkingDayToString(workingDays).toUpperCase());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Day day = (Day) o;
    return dayOfTheMonth.equals(day.dayOfTheMonth)
        && numberOfWorkingHours.equals(day.numberOfWorkingHours)
        && dayOfWeek == day.dayOfWeek
        && date.equals(day.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dayOfTheMonth, numberOfWorkingHours, dayOfWeek, date);
  }
}
