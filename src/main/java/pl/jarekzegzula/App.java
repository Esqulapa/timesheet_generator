package pl.jarekzegzula;

import pl.jarekzegzula.DateTypes.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        List<DayOfWeek> workingWeek = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        WorkingDays workingDays = new WorkingDays(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        WorkingHours workingHours = new WorkingHours(8);

        LocalDate startDate = LocalDate.of(2022, 12, 5);
        LocalDate endDate = LocalDate.of(2023, 2, 3);
        RangeOfDates rangeOfDates = new RangeOfDates(startDate, endDate);

        Map<YearMonth, DaysOfMonth> daysByYearMonth = new DaysByYearMonth(workingDays).getDaysByYearMonthForRangeOfDates(rangeOfDates, workingHours);

        //System.out.println(daysByYearMonth);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<YearMonth, DaysOfMonth> entry : daysByYearMonth.entrySet()) {
            sb.append(entry.getKey())
                    .append(" => ").append(" ")
                    .append(entry.getValue()).append(entry.getValue().days()).append("sdfsdfsdf\n");
        }

        String s = FormattedDayPrinter.formatDays(daysByYearMonth, workingDays);

        System.out.println(s);
    }
}
