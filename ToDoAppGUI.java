import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class ToDoAppGUI {
    private JFrame frame;
    private JTextField titleField, dueDateField;
    private DefaultListModel<String> listModel;
    private JList<String> taskJList;
    private TaskManager taskManager;

    public ToDoAppGUI() {
        taskManager = new TaskManager();
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        frame = new JFrame("To-Do List Application");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        titleField = new JTextField(15);
        dueDateField = new JTextField(10);

        JButton addButton = new JButton("Add Task");
        JButton sortButton = new JButton("Sort by Due Date");

        inputPanel.add(new JLabel("Task Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Due Date (yyyy-mm-dd):"));
        inputPanel.add(dueDateField);
        inputPanel.add(addButton);
        inputPanel.add(sortButton);

        // List Panel
        listModel = new DefaultListModel<>();
        taskJList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskJList);

        // Control Panel
        JPanel controlPanel = new JPanel();
        JButton completeButton = new JButton("Mark as Completed");
        JButton deleteButton = new JButton("Delete Task");

        controlPanel.add(completeButton);
        controlPanel.add(deleteButton);

        // Add Panels to Frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String dueDate = dueDateField.getText().trim();

            if (title.isEmpty() || dueDate.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Both title and due date are required.");
                return;
            }

            // Validate date format (yyyy-mm-dd)
            if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dueDate)) {
                JOptionPane.showMessageDialog(frame, "Due date must be in format yyyy-mm-dd.");
                return;
            }

            try {
                taskManager.addTask(new Task(title, dueDate));
                refreshTaskList();
                titleField.setText("");
                dueDateField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error adding task: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int selectedIndex = taskJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskManager.deleteTask(selectedIndex);
                    refreshTaskList();
                } else {
                    JOptionPane.showMessageDialog(frame, "Select a task to delete.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting task: " + ex.getMessage());
            }
        });

        completeButton.addActionListener(e -> {
            try {
                int selectedIndex = taskJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskManager.markAsCompleted(selectedIndex);
                    refreshTaskList();
                } else {
                    JOptionPane.showMessageDialog(frame, "Select a task to mark as completed.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error updating task: " + ex.getMessage());
            }
        });

        sortButton.addActionListener(e -> {
            try {
                taskManager.sortTasksByDueDate();
                refreshTaskList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error sorting tasks: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }

    private void refreshTaskList() {
        listModel.clear();
        for (Task task : taskManager.getTasks()) {
            listModel.addElement(task.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoAppGUI::new);
    }
}
