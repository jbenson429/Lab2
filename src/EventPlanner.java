import javax.swing.*;
import java.time.LocalDateTime;

// EventPlanner class
class EventPlanner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Event Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 1000);

            EventListPanel eventListPanel = new EventListPanel();
            //addDefaultEvents(eventListPanel);
            frame.add(eventListPanel);

            frame.setVisible(true);
        });
    }

   // public static void addDefaultEvents(EventListPanel events) {
     //   events.addEvent(new Deadline("Project Due", LocalDateTime.of(2025, 3, 1, 23, 59)));
        //events.addEvent(new Meeting("Team Meeting", LocalDateTime.of(2025, 2, 20, 10, 0), LocalDateTime.of(2025, 2, 20, 11, 0), "Room 101"));
    //}
}
