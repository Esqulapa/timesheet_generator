package pl.jarekzegzula;

import pl.jarekzegzula.DateTypes.Day;
import pl.jarekzegzula.DateTypes.DaysOfMonth;

import java.time.YearMonth;
import java.util.Map;

public class FormattedDayPrinter {

  public static String formatDays(
      Map<YearMonth, DaysOfMonth> daysByYearMonth, WorkingDays listOfWorkingDays) {
    StringBuilder sb = new StringBuilder();

    sb.append("| Date        | Day           | Working Hours | Working Day |").append("\n");
    sb.append("|-------------|---------------|---------------|-------------|").append("\n");

    for (Map.Entry<YearMonth, DaysOfMonth> entry : daysByYearMonth.entrySet()) {
      DaysOfMonth daysOfMonth = entry.getValue();

      for (Day day : daysOfMonth.days()) {
        sb.append(String.format("|%-13s", day.getDate()))
            .append(String.format("|%-15s", day.getDayOfWeek()))
            .append(String.format("|%-15s", day.getNumberOfWorkingHours()))
            .append(
                String.format("|%-13s|", day.isWorkingDayToString(day.getWorkingDays()))
                    .toUpperCase())
            .append("\n");
      }
      sb.append(String.format("%29s", ""))
          .append(
              String.format(
                  " |SUMA = %-8s| %n",
                  daysOfMonth.countWorkingDays(daysOfMonth, listOfWorkingDays)))
          .append("\n");
    }

    return sb.toString();
  }
}
