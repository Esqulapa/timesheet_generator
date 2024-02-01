package pl.jarekzegzula.DateTypes;

import pl.jarekzegzula.WorkingDays;
import pl.jarekzegzula.WorkingHours;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DaysInMonth {
  private final WorkingDays workingDays;

  public DaysInMonth(WorkingDays workingDays) {
    this.workingDays = workingDays;
  }

  public List<Day> getDaysOfMonth(YearMonth yearMonth, WorkingHours workingHours, Locale locale) {
    return IntStream.rangeClosed(1, yearMonth.lengthOfMonth())
        .mapToObj(
            dayOfMonth -> {
              DayOfWeek dayOfWeek = yearMonth.atDay(dayOfMonth).getDayOfWeek();
              LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), dayOfMonth);
              return new Day(dayOfMonth, workingHours, dayOfWeek, date, workingDays, locale);
            })
        .collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return "DaysInMonth{}";
  }
}
