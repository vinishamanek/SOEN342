import java.time.DayOfWeek;
import java.time.LocalTime;

public class TimeSlot {
    private DayOfWeek day;
    private LocalTime startTime; 
    private LocalTime endTime;

    public TimeSlot(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return day;
    }
    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    // will need to have a check to see if timeslot overlaps with another existing timeslot, implementation will be fixed later on
    public boolean overlapsWith(TimeSlot other) {
        return this.startTime.isBefore(other.getEndTime()) && this.endTime.isAfter(other.getStartTime());
    }
}
