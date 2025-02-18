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
        setSize(500, 400); // Larger window size
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
        setModal(true); // Make it a modal dialog
        setVisible(true);
    }

    private void addEvent() {
        try {
            String name = nameField.getText();
            LocalDateTime dateTime = LocalDateTime.parse(dateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String location = locationField.getText();

            // Add the event, either a Deadline or Meeting depending on the input
            if (location.isEmpty()) {
                eventListPanel.addEvent(new Deadline(name, dateTime));
            } else {
                eventListPanel.addEvent(new Meeting(name, dateTime, dateTime.plusHours(1), location)); // Example duration of 1 hour
            }

            dispose(); // Close the modal
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please try again.");
        }
    }
}