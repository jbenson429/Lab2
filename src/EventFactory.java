import java.time.LocalDateTime;

public class EventFactory {
    public static Event createEvent(String type, String name, LocalDateTime dateTime) {
        return createEvent(type, name, dateTime, null, null);
    }

    public static Event createEvent(String type, String name, LocalDateTime start, LocalDateTime end, String location) {
        switch (type.toLowerCase()) {
            case "deadline":
                return new Deadline(name, start);
            case "meeting":
                Meeting meeting = new Meeting(name, start);
                if (end != null) meeting.setEndTime(end);
                if (location != null) meeting.setLocation(location);
                return meeting;
            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }
}