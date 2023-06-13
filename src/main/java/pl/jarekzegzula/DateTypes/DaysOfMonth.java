package pl.jarekzegzula.DateTypes;

import pl.jarekzegzula.WorkingDays;
import pl.jarekzegzula.WorkingHours;

import java.util.List;

public record DaysOfMonth(List<Day> days) {


    public Double countWorkingDays(DaysOfMonth days, WorkingDays workingDays) {

        double numberOfWorkingHours = 0;
        for (Day day : days.days) {
            if (day.isWorkingDay(workingDays)) {
                numberOfWorkingHours = numberOfWorkingHours + day.getNumberOfWorkingHours();
            }
        }
        return numberOfWorkingHours;


    }

    public Double countWorkingDays2(DaysOfMonth days, WorkingDays workingDays) {

        return days.days().stream()
                .filter(day -> day.isWorkingDay(workingDays))
                .mapToDouble(Day::getNumberOfWorkingHours)
                .sum();
    }



    @Override
    public String toString() {
//        "DaysOfMonth{" +
//                "days=" + days +
//                '}';
        //"|%-15s|%-16s|%-10s|%-15b|"

        return String.format("%n|%-15s|%-15s|%10s|%15s| %n", "DATA", "DZIEN TYGODNIA", "CZAS PRACY","DZIEN PRACUJACY") + days;
    }
}

