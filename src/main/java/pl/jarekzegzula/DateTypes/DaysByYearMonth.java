package pl.jarekzegzula.DateTypes;

import pl.jarekzegzula.WorkingDays;
import pl.jarekzegzula.WorkingHours;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DaysByYearMonth {

    private final WorkingDays workingDays;

    public DaysByYearMonth(WorkingDays workingDays) {
        this.workingDays = workingDays;
    }

    public Map<YearMonth, DaysOfMonth> getDaysByYearMonthForRangeOfDates(RangeOfDates rangeOfDates, WorkingHours workingHours) {
        LocalDate startDate = rangeOfDates.startDate();
        LocalDate endDate = rangeOfDates.endDate();

        return startDate
                .with(TemporalAdjusters.firstDayOfMonth())
                .datesUntil(endDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1))
                .collect(Collectors.groupingBy(
                        YearMonth::from,
                        LinkedHashMap::new,
                        Collectors.mapping(
                                LocalDate -> new Day(LocalDate.getDayOfMonth(),
                                        workingHours, LocalDate.getDayOfWeek(), LocalDate, workingDays), Collectors.collectingAndThen(Collectors.toList(), DaysOfMonth::new))
                ));
    }

    @Override
    public String toString() {
        return "DaysByYearMonth{}";
    }
}

