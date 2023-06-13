package pl.jarekzegzula;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;

public record WorkingDays(List<DayOfWeek> workingDays) {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingDays that = (WorkingDays) o;
        return Objects.equals(workingDays, that.workingDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workingDays);
    }
}
