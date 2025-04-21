import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

class EventListPanel extends JPanel implements EventObserver {
    private EventManager eventManager;
    private JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private JCheckBox filterCompleted;
    private JCheckBox filterDeadlines;
    private JCheckBox filterMeetings;
    private JButton addEventButton;

    public EventListPanel(EventManager eventManager) {
        this.eventManager = eventManager;
        eventManager.addObserver(this);

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

        updateDisplay();
    }

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

        if (choice == 0) {
            showDeadlineInputDialog();
        } else if (choice == 1) {
            showMeetingInputDialog();
        }
    }

    private void showDeadlineInputDialog() {
        try {
            String name = JOptionPane.showInputDialog(this, "Enter deadline name:");
            String dateInput = JOptionPane.showInputDialog(this, "Enter deadline time (yyyy-MM-dd HH:mm):");

            if (name != null && dateInput != null && !name.isEmpty() && !dateInput.isEmpty()) {
                LocalDateTime deadlineTime = LocalDateTime.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                Event deadline = EventFactory.createEvent("deadline", name, deadlineTime);
                eventManager.addEvent(deadline);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd HH:mm.");
        }
    }

    private void showMeetingInputDialog() {
        try {
            String name = JOptionPane.showInputDialog(this, "Enter meeting name:");
            String startDateInput = JOptionPane.showInputDialog(this, "Enter meeting start time (yyyy-MM-dd HH:mm):");
            String endDateInput = JOptionPane.showInputDialog(this, "Enter meeting end time (yyyy-MM-dd HH:mm):");
            String location = JOptionPane.showInputDialog(this, "Enter meeting location:");

            if (name != null && startDateInput != null && endDateInput != null && location != null &&
                    !name.isEmpty() && !startDateInput.isEmpty() && !endDateInput.isEmpty() && !location.isEmpty()) {

                LocalDateTime startTime = LocalDateTime.parse(startDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                LocalDateTime endTime = LocalDateTime.parse(endDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                Event meeting = EventFactory.createEvent("meeting", name, startTime, endTime, location);
                eventManager.addEvent(meeting);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd HH:mm.");
        }
    }

    public void removeEvent(Event event) {
        eventManager.removeEvent(event);
    }

    private void sortEvents() {
        if ("Sort by Name".equals(sortDropDown.getSelectedItem())) {
            eventManager.getEvents().sort(Comparator.comparing(Event::getName));
        } else if ("Sort by Reverse Name".equals(sortDropDown.getSelectedItem())) {
            eventManager.getEvents().sort(Comparator.comparing(Event::getName).reversed());
        } else {
            eventManager.getEvents().sort(Comparator.naturalOrder());
        }
        updateDisplay();
    }

    private void updateDisplay() {
        displayPanel.removeAll();
        for (Event event : eventManager.getEvents()) {
            if ((filterCompleted.isSelected() && event instanceof Completable && ((Completable) event).isComplete()) ||
                    (filterDeadlines.isSelected() && event instanceof Deadline) ||
                    (filterMeetings.isSelected() && event instanceof Meeting)) {
                continue;
            }
            displayPanel.add(new EventPanel(event, this));
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public void onEventListChanged() {
        updateDisplay();
    }
}
