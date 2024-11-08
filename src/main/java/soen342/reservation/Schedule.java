package soen342.reservation;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
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
