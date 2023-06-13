package pl.jarekzegzula;

import java.util.Objects;

public class WorkingHours {
    private final Integer workingHours;

    public WorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    public Integer value(){
        return workingHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkingHours that = (WorkingHours) o;
        return Objects.equals(workingHours, that.workingHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workingHours);
    }

    @Override
    public String toString() {
        return  "" + workingHours;
    }
}
