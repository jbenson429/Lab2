import java.time.Duration;
import java.time.LocalDateTime;

// Meeting class
class Meeting extends Event implements Completable {
    private LocalDateTime endDateTime;
    private String location;
    private boolean completed;

    public Meeting(String name, LocalDateTime dateTime) {
        super(name, dateTime);
        this.completed = false;
    }

    public LocalDateTime getEndTime() {
        return endDateTime;
    }

    public Duration getDuration() {
        return Duration.between(dateTime, endDateTime);
    }

    public String getLocation() {
        return location;
    }

    public void setEndTime(LocalDateTime end) {
        this.endDateTime = end;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getName() {
        return "Meeting: " + name;
    }

    @Override
    public void complete() {
        completed = true;
    }

    @Override
    public boolean isComplete() {
        return completed;
    }

}