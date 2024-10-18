public class Offering {
    private Lesson lesson;
    private int capacity;
    private Location location;
    private TimeSlot timeslot;

    public Offering(Lesson lesson, int capacity, Location location, TimeSlot timeslot) {
        this.lesson = lesson;
        this.capacity = capacity;
        this.location = location;
        this.timeslot = timeslot;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public int getCapacity() {
        return capacity;
    }

    public Location getLocation() {
        return location;
    }

    public TimeSlot getTimeSlot() {
        return timeslot;
    }

    @Override
    public String toString() {
        return "Lesson: " + lesson.getName() + "\n" +
                "Capacity: " + capacity + "\n" +
                "Location: " + location.getName() + ", " + location.getCity().getName() + "\n" +
                "Day: " + timeslot.getDayOfWeek() + "\n" +
                "Start Time: " + timeslot.getStartTime() + "\n" +
                "End Time: " + timeslot.getEndTime();
    }

}