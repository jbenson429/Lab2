import javax.swing.*;
import java.awt.*;

// EventPanel class
class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton;
    private EventListPanel eventListPanel;  // Reference to EventListPanel

    public EventPanel(Event event, EventListPanel eventListPanel) {
        this.event = event;
        this.eventListPanel = eventListPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(event.getName() + " at " + event.getDateTime());
        add(label);

        if (event instanceof Meeting) {
            add(new JLabel("Location: " + ((Meeting) event).getLocation()));
        }

        if (event instanceof Completable) {
            completeButton = new JButton("Complete");
            completeButton.addActionListener(e -> {
                ((Completable) event).complete();
                completeButton.setEnabled(false); // Disable the button after completion
                eventListPanel.removeEvent(event); // Remove from the list
            });
            add(completeButton);
        }
    }
}

//git test