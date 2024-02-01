package pl.jarekzegzula;

import pl.jarekzegzula.DateTypes.DaysByYearMonth;
import pl.jarekzegzula.DateTypes.DaysOfMonth;
import pl.jarekzegzula.DateTypes.RangeOfDates;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Locale;
import java.util.Map;

public class App {

  public static void main(String[] args) throws IOException {

    ConsolePrints.showProgramTitle();

    Locale localeFromUser = DataFromUser.getLocaleFromUser();

    LocalDate startDateFromUser = DataFromUser.getFirstDateFromUser();

    LocalDate endDateFromUser = DataFromUser.getSecondDateFromUser(startDateFromUser);

    WorkingHours numberOfWorkingHoursFromUser = DataFromUser.getNumberOfWorkingHoursFromUser();

    WorkingDays workingDaysFromUser = DataFromUser.getWorkingDaysFromUser();

    RangeOfDates rangeOfDates = new RangeOfDates(startDateFromUser, endDateFromUser);

    DaysByYearMonth daysByYearMonth = new DaysByYearMonth(workingDaysFromUser);

    Map<YearMonth, DaysOfMonth> daysByYearMonthForRangeOfDates =
        daysByYearMonth.getDaysByYearMonthForRangeOfDates(
            rangeOfDates, numberOfWorkingHoursFromUser, localeFromUser);
    TimeSheetCreator timeSheetCreator = new TimeSheetCreator();

    timeSheetCreator.createTimeSheet(daysByYearMonthForRangeOfDates, localeFromUser);
  }
}
