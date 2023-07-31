package pl.jarekzegzula.DateTypes;

import org.junit.jupiter.api.Test;
import pl.jarekzegzula.WorkingDays;
import pl.jarekzegzula.WorkingHours;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DayTest {
    @Test
    void shouldCreateDay() {
        //given
        LocalDate date = LocalDate.of(2023,06,05);
        Locale polish = new Locale("pl", "PL");

        int dayOfTheMonth = 1;
        WorkingHours numberOfWorkingHours = new WorkingHours(8);
        DayOfWeek monday = DayOfWeek.MONDAY;
        WorkingDays workingDays = new WorkingDays(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        //when
        Day day = new Day(dayOfTheMonth, numberOfWorkingHours, monday, date, workingDays, polish);

        //then
        assertNotNull(day);

    }

    @Test
    void shouldCheckWorkingDay() {

        //given
        Locale polish = new Locale("pl", "PL");
        LocalDate date = LocalDate.of(2023,06,05);
        int dayOfTheMonth = 1;
        WorkingHours numberOfWorkingHours = new WorkingHours(8);
        WorkingDays workingDays = new WorkingDays(List.of(DayOfWeek.MONDAY));

        Day day = new Day(dayOfTheMonth, numberOfWorkingHours, date.getDayOfWeek(), date, workingDays, polish);
        //when
        Boolean isWorkingDay = day.isWorkingDay(workingDays);

        //then
        assertTrue(isWorkingDay);


    }

}