import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class AddEventModal extends JDialog {
    private EventListPanel eventListPanel;
    private JTextField nameField;
    private JTextField dateField;
    private JTextField locationField;
    private JButton addButton;

    public AddEventModal(EventListPanel eventListPanel) {
        this.eventListPanel = eventListPanel;

        setTitle("Add Event");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Event Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Event Date (yyyy-MM-dd HH:mm):"));
        dateField = new JTextField();
        panel.add(dateField);

        panel.add(new JLabel("Location (for meetings):"));
        locationField = new JTextField();
        panel.add(locationField);

        addButton = new JButton("Add Event");
        addButton.addActionListener(e -> addEvent());
        panel.add(addButton);

        add(panel);
        setModal(true);
        setVisible(true);
    }

    private void addEvent() {
        try {
            String name = nameField.getText();
            LocalDateTime dateTime = LocalDateTime.parse(dateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String location = locationField.getText();

            Event event;

            if (location.isEmpty()) {
                event = EventFactory.createEvent("deadline", name, dateTime);
            } else {
                LocalDateTime endDateTime = dateTime.plusHours(1);
                event = EventFactory.createEvent("meeting", name, dateTime, endDateTime, location);
            }

            //  Notify via EventManager instead of modifying the list directly
            eventListPanel.getEventManager().addEvent(event);

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please try again.");
        }
    }
}
