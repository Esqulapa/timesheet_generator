package pl.jarekzegzula.DateTypes;

import org.junit.jupiter.api.Test;
import pl.jarekzegzula.WorkingDays;
import pl.jarekzegzula.WorkingHours;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaysByYearMonthTest {


    @Test
    void TestShouldGetDaysByMonthForRangeOfDates() {
        System.out.println("TEST");
        //given
        Locale polish = new Locale("pl", "PL");
        WorkingHours workingHours = new WorkingHours(8);
        WorkingDays workingDays = new WorkingDays(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));


        LocalDate startDate = LocalDate.of(2022, 12, 5);
        LocalDate endDate = LocalDate.of(2023, 2, 3);
        RangeOfDates rangeOfDates = new RangeOfDates(startDate, endDate);

        YearMonth december2022 = YearMonth.of(2022, 12);
        YearMonth january2023 = YearMonth.of(2023, 1);
        YearMonth february2023 = YearMonth.of(2023, 2);

        List<Day> daysOfDecember = new DaysInMonth(workingDays).getDaysOfMonth(december2022, workingHours,polish);
        List<Day> daysOfJanuary = new DaysInMonth(workingDays).getDaysOfMonth(january2023, workingHours,polish);
        List<Day> daysOfFebruary = new DaysInMonth(workingDays).getDaysOfMonth(february2023, workingHours,polish);

        DaysOfMonth daysOfMonth = new DaysOfMonth(daysOfDecember);
        DaysOfMonth daysOfMonth1 = new DaysOfMonth(daysOfJanuary);
        DaysOfMonth daysOfMonth2 = new DaysOfMonth(daysOfFebruary);


        Map<YearMonth, DaysOfMonth> daysOfMonthMap = Map.of(
                december2022, daysOfMonth,
                january2023,daysOfMonth1,
                february2023,daysOfMonth2
        );


        //when
        Map<YearMonth, DaysOfMonth> daysByYearMonth = new DaysByYearMonth(workingDays).getDaysByYearMonthForRangeOfDates(rangeOfDates, workingHours,polish);


        //then

        assertEquals(daysOfMonthMap, daysByYearMonth);
        System.out.println(daysByYearMonth);

    }
}