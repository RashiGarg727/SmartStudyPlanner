import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlannerUI extends JFrame {
    private ArrayList<Task> tasks = new ArrayList<>();
    private JTextField taskField;
    private JTextField subjectField;
    private JTextField deadlineField;
    private JTable table;
    private DefaultTableModel tableModel;

    public PlannerUI() {
        setTitle("Smart Study Planner");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel (Inputs)
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        inputPanel.add(new JLabel("Task Name:"));
        taskField = new JTextField();
        inputPanel.add(taskField);

        inputPanel.add(new JLabel("Subject:"));
        subjectField = new JTextField();
        inputPanel.add(subjectField);

        inputPanel.add(new JLabel("Deadline:"));
        deadlineField = new JTextField();
        inputPanel.add(deadlineField);

        JButton addButton = new JButton("Add Task");
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Task", "Subject", "Deadline", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();

        JButton completeButton = new JButton("Mark Complete");
        JButton deleteButton = new JButton("Delete Task");

        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addTask());
        completeButton.addActionListener(e -> markComplete());
        deleteButton.addActionListener(e -> deleteTask());

        setVisible(true);
    }

    private void addTask() {
    String taskName = taskField.getText().trim();
    String subject = subjectField.getText().trim();
    String deadline = deadlineField.getText().trim();

    if (taskName.isEmpty() || subject.isEmpty() || deadline.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill all fields!");
        return;
    }

    Task task = new Task(taskName, subject, deadline);
    tasks.add(task);

    tableModel.addRow(new Object[]{
            task.getName(),
            task.getSubject(),
            task.getDeadline(),
            task.getStatus()
    });

    taskField.setText("");
    subjectField.setText("");
    deadlineField.setText("");
}

    private void markComplete() {
    int selectedRow = table.getSelectedRow();

    if (selectedRow != -1) {
        tasks.get(selectedRow).markComplete();
        tableModel.setValueAt("Done", selectedRow, 3);
    }
}

    private void deleteTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        }
    }
}