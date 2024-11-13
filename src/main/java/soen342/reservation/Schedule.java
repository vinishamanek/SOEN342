package soen342.reservation;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "schedule_id", nullable = false)
    private List<TimeSlot> timeSlots;

    public Schedule() {
        this.timeSlots = new ArrayList<>();
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void addTimeSlot(TimeSlot timeSlot) throws TimeSlotOverlapException {
        for (TimeSlot slot : timeSlots) {
            if (slot.overlapsWith(timeSlot)) {
                throw new TimeSlotOverlapException("Attempt to add an overlaping timeslot", slot);
            }
        }
        timeSlots.add(timeSlot);
    }
}
