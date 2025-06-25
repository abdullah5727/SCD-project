package ToDoApp;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

public class ToDoAppGUI extends JFrame {
    private JTextField titleField, dueDateField;
    private DefaultListModel<String> listModel;
    private JList<String> taskJList;
    private TaskManager taskManager;

    public ToDoAppGUI() {
        setTitle("To-Do List Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 250));

        taskManager = new TaskManager();
        Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font headingFont = new Font("Segoe UI", Font.BOLD, 18);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(210, 210, 220)),
            new EmptyBorder(20, 30, 20, 30)
        ));
        inputPanel.setBackground(new Color(255, 255, 255));

        JLabel heading = new JLabel("Add New Task");
        heading.setFont(headingFont);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setForeground(new Color(50, 70, 90));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255));
        formPanel.setBorder(new EmptyBorder(15, 0, 5, 0));
        
        
        GridBagConstraints gbcTitleLabel = new GridBagConstraints();
        gbcTitleLabel.gridx = 0;
        gbcTitleLabel.gridy = 0;
        gbcTitleLabel.insets = new Insets(5, 5, 5, 15);
        gbcTitleLabel.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Task Title:"), gbcTitleLabel);
        
        GridBagConstraints gbcTitleField = new GridBagConstraints();
        gbcTitleField.gridx = 1;
        gbcTitleField.gridy = 0;
        gbcTitleField.fill = GridBagConstraints.HORIZONTAL;
        gbcTitleField.weightx = 1.0;
        gbcTitleField.insets = new Insets(5, 5, 5, 15);
        titleField = createStyledTextField(20);
        formPanel.add(titleField, gbcTitleField);
        
        
        GridBagConstraints gbcDateLabel = new GridBagConstraints();
        gbcDateLabel.gridx = 0;
        gbcDateLabel.gridy = 1;
        gbcDateLabel.insets = new Insets(5, 5, 5, 15);
        gbcDateLabel.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Due Date:"), gbcDateLabel);
        
        GridBagConstraints gbcDateField = new GridBagConstraints();
        gbcDateField.gridx = 1;
        gbcDateField.gridy = 1;
        gbcDateField.fill = GridBagConstraints.HORIZONTAL;
        gbcDateField.insets = new Insets(5, 5, 5, 15);
        dueDateField = createStyledTextField(10);
        dueDateField.setToolTipText("Format: YYYY-MM-DD");
        formPanel.add(dueDateField, gbcDateField);
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(new Color(255, 255, 255));
        
        JButton addButton = createPrimaryButton("Add Task", new Color(80, 150, 220));
        JButton sortButton = createPrimaryButton("Sort by Date", new Color(230, 230, 230));
        
        buttonPanel.add(addButton);
        buttonPanel.add(sortButton);
        
        GridBagConstraints gbcButtonPanel = new GridBagConstraints();
        gbcButtonPanel.gridx = 0;
        gbcButtonPanel.gridy = 2;
        gbcButtonPanel.gridwidth = 2;
        gbcButtonPanel.fill = GridBagConstraints.CENTER;
        gbcButtonPanel.insets = new Insets(15, 0, 0, 0);
        formPanel.add(buttonPanel, gbcButtonPanel);

        inputPanel.add(heading);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(formPanel);

        
        listModel = new DefaultListModel<>();
        taskJList = new JList<>(listModel);
        taskJList.setFont(mainFont);
        taskJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskJList.setBackground(new Color(250, 250, 255));
        taskJList.setSelectionBackground(new Color(180, 210, 240)); 
        taskJList.setSelectionForeground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(taskJList);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        scrollPane.setViewportBorder(new EmptyBorder(0, 0, 0, 0));
        
        JPanel listContainer = new JPanel(new BorderLayout());
        listContainer.setBorder(new EmptyBorder(20, 30, 10, 30));
        listContainer.setBackground(new Color(245, 245, 250));
        listContainer.add(scrollPane, BorderLayout.CENTER);

        
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(new Color(245, 245, 250));
        actionPanel.setBorder(new EmptyBorder(10, 30, 20, 30));
        
        JButton completeButton = createSuccessButton("Complete", new Color(100, 200, 120));
        JButton deleteButton = createDangerButton("Delete", new Color(240, 100, 100)); 
        
        completeButton.setMnemonic(KeyEvent.VK_C);
        deleteButton.setMnemonic(KeyEvent.VK_D);
        
        actionPanel.add(completeButton);
        actionPanel.add(Box.createHorizontalStrut(15));
        actionPanel.add(deleteButton);

        
        add(inputPanel, BorderLayout.NORTH);
        add(listContainer, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

       
        addButton.addActionListener(e -> addTask());
        titleField.addActionListener(e -> addTask());
        dueDateField.addActionListener(e -> addTask());

        deleteButton.addActionListener(e -> {
            int selectedIndex = taskJList.getSelectedIndex();
            if (selectedIndex != -1) {
                int confirm = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to delete this task?", 
                    "Confirm Deletion", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    taskManager.deleteTask(selectedIndex);
                    refreshTaskList();
                    showSuccessMessage("Task deleted successfully");
                }
            } else {
                showWarning("Please select a task to delete");
            }
        });

        completeButton.addActionListener(e -> {
            int selectedIndex = taskJList.getSelectedIndex();
            if (selectedIndex != -1) {
                taskManager.markAsCompleted(selectedIndex);
                refreshTaskList();
                showSuccessMessage("Task marked as completed");
            } else {
                showWarning("Please select a task to complete");
            }
        });

        sortButton.addActionListener(e -> {
            taskManager.sortTasksByDueDate();
            refreshTaskList();
        });
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    ToDoAppGUI.this,
                    "Are you sure you want to exit?",
                    "Exit To-Do List",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(200, 200, 210)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        field.setBackground(new Color(250, 250, 255));
        return field;
    }

    private JButton createPrimaryButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(new CompoundBorder(
            new LineBorder(new Color(bgColor.getRed()-20, bgColor.getGreen()-20, bgColor.getBlue()-20), 1, true),
            new EmptyBorder(8, 20, 8, 20)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createSuccessButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(new CompoundBorder(
            new LineBorder(new Color(bgColor.getRed()-20, bgColor.getGreen()-20, bgColor.getBlue()-20), 1, true),
            new EmptyBorder(8, 20, 8, 20)
        ));
        return button;
    }

    private JButton createDangerButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(new CompoundBorder(
            new LineBorder(new Color(bgColor.getRed()-20, bgColor.getGreen()-20, bgColor.getBlue()-20), 1, true),
            new EmptyBorder(8, 20, 8, 20)
        ));
        return button;
    }

    private void addTask() {
        String title = titleField.getText().trim();
        String dueDate = dueDateField.getText().trim();

        if (title.isEmpty()) {
            showWarning("Task title cannot be empty");
            titleField.requestFocusInWindow();
            return;
        }

        if (dueDate.isEmpty()) {
            showWarning("Due date cannot be empty");
            dueDateField.requestFocusInWindow();
            return;
        }

        if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dueDate)) {
            showWarning("Invalid date format. Please use YYYY-MM-DD");
            dueDateField.requestFocusInWindow();
            return;
        }

        try {
            taskManager.addTask(new Task(title, dueDate));
            refreshTaskList();
            titleField.setText("");
            dueDateField.setText("");
            titleField.requestFocusInWindow();
            
            showSuccessMessage("Task added successfully!");
        } catch (Exception ex) {
            showError("Error adding task: " + ex.getMessage());
        }
    }

    private void refreshTaskList() {
        listModel.clear();
        for (Task task : taskManager.getTasks()) {
            listModel.addElement(task.toString());
        }
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(
            this, 
            message, 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
            this, 
            message, 
            "Warning", 
            JOptionPane.WARNING_MESSAGE
        );
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
            this, 
            message, 
            "Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            javax.swing.UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 13));
            javax.swing.UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 12));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            ToDoAppGUI app = new ToDoAppGUI();
            app.setVisible(true);
        });
    }
}
