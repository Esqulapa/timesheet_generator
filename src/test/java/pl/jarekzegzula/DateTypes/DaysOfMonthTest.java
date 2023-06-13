package pl.jarekzegzula.DateTypes;

import org.junit.jupiter.api.Test;
import pl.jarekzegzula.WorkingDays;
import pl.jarekzegzula.WorkingHours;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DaysOfMonthTest {

    @Test
    void testShouldCountNumberOfWorkingHoursFromDaysInMonth(){

        //given
        WorkingHours workingHours = new WorkingHours(8);
        YearMonth december2022 = YearMonth.of(2023, 3);


        WorkingDays workingDays = new WorkingDays(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        List<Day> daysOfDecember = new DaysInMonth(workingDays).getDaysOfMonth(december2022, workingHours);
        DaysOfMonth daysOfMonth = new DaysOfMonth(daysOfDecember);

        //when
        Double countedWorkingDays = daysOfMonth.countWorkingDays2(daysOfMonth, workingDays);
        Double countedWorkingDays2 = daysOfMonth.countWorkingDays(daysOfMonth, workingDays);
        //
        System.out.println(countedWorkingDays);
        System.out.println(countedWorkingDays2);
        System.out.println();
        assertNotNull(countedWorkingDays);
    }

}