package pl.jarekzegzula.DateTypes;

import pl.jarekzegzula.WorkingDays;

import java.util.List;

public record DaysOfMonth(List<Day> days) {
 public List<Day> days() {
        return days;
    }
    public Double countWorkingDays(DaysOfMonth days, WorkingDays workingDays) {

        return days.days().stream()
                .filter(day -> day.isWorkingDay(workingDays))
                .mapToDouble(Day::getNumberOfWorkingHours)
                .sum();
    }
    @Override
    public String toString() {

        return String.format("%n|%-15s|%-15s|%10s|%15s| %n", "DATA", "DZIEN TYGODNIA", "CZAS PRACY","DZIEN PRACUJACY") + days;
    }

}

