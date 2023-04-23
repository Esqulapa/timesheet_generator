import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class YearMonthMap {
    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */

    public Map<Year, List<Month>> getMonthsByYearForRangeOfDates(LocalDate startDate, LocalDate endDate) {


        return startDate
                .with(TemporalAdjusters.firstDayOfMonth())
                .datesUntil(endDate.with(TemporalAdjusters.lastDayOfMonth()),Period.ofMonths(1))
                .collect(Collectors.groupingBy(Year::from,Collectors.mapping(LocalDate::getMonth,Collectors.toList())));

    }
}






