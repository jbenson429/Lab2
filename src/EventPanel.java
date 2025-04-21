import javax.swing.*;
import java.awt.*;

// EventPanel class
class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton;
    private EventListPanel eventListPanel;

    public EventPanel(Event event, EventListPanel eventListPanel) {
        this.event = event;
        this.eventListPanel = eventListPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // Change to BoxLayout

        JLabel label = new JLabel(event.getName() + " at " + event.getDateTime());
        add(label);

        // If it's a Meeting, show location
        if (event instanceof Meeting) {
            add(new JLabel("Location: " + ((Meeting) event).getLocation()));
        }

        // If it's a Completable event (Deadline or Meeting), add Complete button
        if (event instanceof Completable) {
            completeButton = new JButton("Complete");
            completeButton.addActionListener(e -> {
                ((Completable) event).complete();
                completeButton.setEnabled(false);
                eventListPanel.removeEvent(event); // Remove event after completing
            });
            add(completeButton);
        }
    }
}