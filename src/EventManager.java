import java.util.ArrayList;
import java.util.List;

class EventManager {
    private List<Event> events = new ArrayList<>();
    private List<EventObserver> observers = new ArrayList<>();

    public void addEvent(Event event) {
        events.add(event);
        notifyObservers();
    }

    public void removeEvent(Event event) {
        events.remove(event);
        notifyObservers();
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events); // Return a copy to avoid modification outside
    }

    public void addObserver(EventObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(EventObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (EventObserver observer : observers) {
            observer.onEventListChanged();
        }
    }
}
