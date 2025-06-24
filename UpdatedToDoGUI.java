import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class ToDoAppGUI extends JFrame {
    private JTextField titleField, dueDateField;
    private DefaultListModel<String> listModel;
    private JList<String> taskJList;
    private TaskManager taskManager;

    public ToDoAppGUI() {
        setTitle("📝 To-Do List Application");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        taskManager = new TaskManager();
        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        // Header Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        inputPanel.setBackground(new Color(255, 255, 255));

        JLabel heading = new JLabel("Add New Task");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setForeground(new Color(33, 53, 85));

        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setBackground(new Color(255, 255, 255));

        titleField = new JTextField(15);
        dueDateField = new JTextField(10);
        JButton addButton = createStyledButton("➕ Add Task");
        JButton sortButton = createStyledButton("📅 Sort by Due Date");

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Due (yyyy-mm-dd):"));
        formPanel.add(dueDateField);
        formPanel.add(addButton);
        formPanel.add(sortButton);

        inputPanel.add(heading);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(formPanel);

        // Task List Panel
        listModel = new DefaultListModel<>();
        taskJList = new JList<>(listModel);
        taskJList.setFont(font);
        taskJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskJList.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        JScrollPane scrollPane = new JScrollPane(taskJList);
        scrollPane.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Action Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        JButton completeButton = createStyledButton("✅ Mark Completed");
        JButton deleteButton = createStyledButton("🗑 Delete Task");

        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        // Add Panels
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Task
        addButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String dueDate = dueDateField.getText().trim();

            if (title.isEmpty() || dueDate.isEmpty()) {
                showError("Both title and due date are required.");
                return;
            }

            if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dueDate)) {
                showError("Due date must be in format yyyy-mm-dd.");
                return;
            }

            try {
                taskManager.addTask(new Task(title, dueDate));
                refreshTaskList();
                titleField.setText("");
                dueDateField.setText("");

                int option = JOptionPane.showConfirmDialog(this, "Task added. Do you want to continue?", "Continue?", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            } catch (Exception ex) {
                showError("Error adding task: " + ex.getMessage());
            }
        });

        // Delete Task
        deleteButton.addActionListener(e -> {
            try {
                int selectedIndex = taskJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskManager.deleteTask(selectedIndex);
                    refreshTaskList();

                    int option = JOptionPane.showConfirmDialog(this, "Task deleted. Do you want to continue?", "Continue?", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                } else {
                    showError("Select a task to delete.");
                }
            } catch (Exception ex) {
                showError("Error deleting task: " + ex.getMessage());
            }
        });

        // Mark Completed
        completeButton.addActionListener(e -> {
            try {
                int selectedIndex = taskJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskManager.markAsCompleted(selectedIndex);
                    refreshTaskList();

                    int option = JOptionPane.showConfirmDialog(this, "Task marked as completed. Do you want to continue?", "Continue?", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                } else {
                    showError("Select a task to mark as completed.");
                }
            } catch (Exception ex) {
                showError("Error updating task: " + ex.getMessage());
            }
        });

        // Sort Tasks
        sortButton.addActionListener(e -> {
            try {
                taskManager.sortTasksByDueDate();
                refreshTaskList();
            } catch (Exception ex) {
                showError("Error sorting tasks: " + ex.getMessage());
            }
        });
    }

    private void refreshTaskList() {
        listModel.clear();
        for (Task task : taskManager.getTasks()) {
            listModel.addElement(task.toString());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText("Click to " + text.toLowerCase());
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoAppGUI app = new ToDoAppGUI();
            app.setVisible(true);
        });
    }
}
