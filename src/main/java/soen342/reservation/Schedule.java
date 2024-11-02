package soen342.reservation;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<TimeSlot> timeSlots;

    public Schedule() {
        this.timeSlots = new ArrayList<>();
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void addTimeSlot(TimeSlot timeSlot) {
        for (TimeSlot slot : timeSlots) {
            if (slot.overlapsWith(timeSlot)) {
                throw new RuntimeException("Time slot overlaps with existing time slot.");
            }
        }
        timeSlots.add(timeSlot);
    }
}
