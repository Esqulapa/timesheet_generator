import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class YearMonthMapTest {
    @Test
    public void testShouldGetMonthByYearForRangeOfDates() {
        System.out.println("TEST");
        //given
        LocalDate startDate = LocalDate.of(2022,10,5);
        LocalDate endDate = LocalDate.of(2023,4,3);
        YearMonthMap yearMonthMap = new YearMonthMap();
        Map<Year, List<Month>> expected = Map.of(
                Year.of(2022), List.of(Month.OCTOBER,Month.NOVEMBER,Month.DECEMBER),
                Year.of(2023), List.of(Month.JANUARY,Month.FEBRUARY,Month.MARCH,Month.APRIL)
        );


        //when

        Map<Year, List<Month>> monthsByYear = yearMonthMap.getMonthsByYearForRangeOfDates(startDate,endDate);
        //then
        assertEquals(expected, monthsByYear);

    }

}