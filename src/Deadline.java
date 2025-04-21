import java.time.LocalDateTime;

public class Deadline extends Event implements Completable {
    private boolean completed;

    public Deadline(String name, LocalDateTime dateTime) {
        super(name, dateTime);
        this.completed = false;
    }

    @Override
    public String getName() {
        return "Deadline: " + name;
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