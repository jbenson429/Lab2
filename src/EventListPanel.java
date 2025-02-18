import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

/// EventListPanel class
class EventListPanel extends JPanel {
    private ArrayList<Event> events;
    private JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private JCheckBox filterCompleted;
    private JCheckBox filterDeadlines;
    private JCheckBox filterMeetings;
    private JButton addEventButton;

    public EventListPanel() {
        // Initialize events list as empty
        events = new ArrayList<>();
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        sortDropDown = new JComboBox<>(new String[]{"Sort by Name", "Sort by Reverse Name", "Sort by Date"});
        sortDropDown.addActionListener(e -> sortEvents());
        controlPanel.add(sortDropDown);

        filterCompleted = new JCheckBox("Filter Completed");
        filterDeadlines = new JCheckBox("Filter Deadlines");
        filterMeetings = new JCheckBox("Filter Meetings");

        ActionListener filterListener = e -> updateDisplay();
        filterCompleted.addActionListener(filterListener);
        filterDeadlines.addActionListener(filterListener);
        filterMeetings.addActionListener(filterListener);

        controlPanel.add(filterCompleted);
        controlPanel.add(filterDeadlines);
        controlPanel.add(filterMeetings);

        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> showEventTypeDialog());
        controlPanel.add(addEventButton);

        add(controlPanel, BorderLayout.NORTH);

        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        add(displayPanel, BorderLayout.CENTER);
    }

    // Method to show the event type dialog (Deadline or Meeting)
    private void showEventTypeDialog() {
        String[] options = {"Deadline", "Meeting"};
        int choice = JOptionPane.showOptionDialog(this,
                "Choose event type",
                "Event Type",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) { // Deadline chosen
            showDeadlineInputDialog();
        } else if (choice == 1) { // Meeting chosen
            showMeetingInputDialog();
        }
    }

    // Method to handle Deadline input
    private void showDeadlineInputDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter deadline name:");
        String dateInput = JOptionPane.showInputDialog(this, "Enter deadline time (yyyy-MM-dd HH:mm):");

        if (name != null && !name.isEmpty() && dateInput != null && !dateInput.isEmpty()) {
            try {
                LocalDateTime deadlineTime = LocalDateTime.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                addEvent(new Deadline(name, deadlineTime));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd HH:mm.");
            }
        }
    }

    // Method to handle Meeting input
    private void showMeetingInputDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter meeting name:");
        String startDateInput = JOptionPane.showInputDialog(this, "Enter meeting start time (yyyy-MM-dd HH:mm):");
        String endDateInput = JOptionPane.showInputDialog(this, "Enter meeting end time (yyyy-MM-dd HH:mm):");
        String location = JOptionPane.showInputDialog(this, "Enter meeting location:");

        if (name != null && !name.isEmpty() && startDateInput != null && !startDateInput.isEmpty() && endDateInput != null && !endDateInput.isEmpty() && location != null && !location.isEmpty()) {
            try {
                LocalDateTime startTime = LocalDateTime.parse(startDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                LocalDateTime endTime = LocalDateTime.parse(endDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                addEvent(new Meeting(name, startTime, endTime, location));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd HH:mm.");
            }
        }
    }

    public void addEvent(Event event) {
        events.add(event);
        updateDisplay();
    }

    // Method to remove event from the list and update display
    public void removeEvent(Event event) {
        events.remove(event);
        updateDisplay();
    }

    private void sortEvents() {
        if (sortDropDown.getSelectedItem().equals("Sort by Name")) {
            events.sort(Comparator.comparing(Event::getName));
        } else if (sortDropDown.getSelectedItem().equals("Sort by Reverse Name")) {
            events.sort(Comparator.comparing(Event::getName).reversed());
        } else {
            events.sort(Comparator.naturalOrder());
        }
        updateDisplay();
    }

    private void updateDisplay() {
        displayPanel.removeAll();
        for (Event event : events) {
            if ((filterCompleted.isSelected() && event instanceof Completable && ((Completable) event).isComplete()) ||
                    (filterDeadlines.isSelected() && event instanceof Deadline) ||
                    (filterMeetings.isSelected() && event instanceof Meeting)) {
                continue;
            }
            displayPanel.add(new EventPanel(event, this));  // Pass EventListPanel to EventPanel
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }
}