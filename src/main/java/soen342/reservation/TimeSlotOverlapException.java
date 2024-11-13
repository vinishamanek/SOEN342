package soen342.reservation;

public class TimeSlotOverlapException extends Exception {
    private final TimeSlot overlappingTimeSlot;

    public TimeSlotOverlapException(String message, TimeSlot overlappingTimeSlot) {
        super(message);
        this.overlappingTimeSlot = overlappingTimeSlot;
    }

    public TimeSlot getOverlappingTimeSlot() {
        return overlappingTimeSlot;
    }
}
