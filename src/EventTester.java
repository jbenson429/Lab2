import javax.swing.*;

class EventTester {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Event Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 1000);

            EventManager eventManager = new EventManager(); //  Create manager
            EventListPanel eventListPanel = new EventListPanel(eventManager); //  Pass it in
            frame.add(eventListPanel);

            frame.setVisible(true);
        });
    }
}

