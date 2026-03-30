import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
public class PlannerUI extends JFrame {
    private JComboBox<String> priorityBox;
    private JLabel statsLabel;
    private ArrayList<Task> tasks = TaskManager.loadTasks();
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

        inputPanel.add(new JLabel("Priority:"));
priorityBox = new JComboBox<>(new String[]{"High", "Medium", "Low"});
inputPanel.add(priorityBox);

        JButton addButton = new JButton("Add Task");
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);
        statsLabel = new JLabel("Total Tasks: 0 | Completed: 0");
add(statsLabel, BorderLayout.WEST);
        // Table
        String[] columns = {"Task", "Subject", "Deadline", "Priority", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        applyPriorityColors();
        for (Task task : tasks) {
    tableModel.addRow(new Object[]{
            task.getName(),
            task.getSubject(),
            task.getDeadline(),
            task.getStatus()
    });
}

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
        updateStats();
    }

    private void addTask() {
    String taskName = taskField.getText().trim();
    String subject = subjectField.getText().trim();
    String deadline = deadlineField.getText().trim();
    String priority = priorityBox.getSelectedItem().toString();

    if (taskName.isEmpty() || subject.isEmpty() || deadline.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill all fields!");
        return;
    }

    Task task = new Task(taskName, subject, deadline, priority);
    tasks.add(task);

    tableModel.addRow(new Object[]{
        task.getName(),
        task.getSubject(),
        task.getDeadline(),
        task.getPriority(),
        task.getStatus()
});

    TaskManager.saveTasks(tasks);
    updateStats();

    taskField.setText("");
    subjectField.setText("");
    deadlineField.setText("");
}

    private void markComplete() {
    int selectedRow = table.getSelectedRow();

    if (selectedRow != -1) {
        tasks.get(selectedRow).markComplete();
        tableModel.setValueAt("Done", selectedRow, 3);
        TaskManager.saveTasks(tasks);
    }
    updateStats();
}

   private void deleteTask() {
    int selectedRow = table.getSelectedRow();

    if (selectedRow != -1) {
        tasks.remove(selectedRow);
        tableModel.removeRow(selectedRow);
        TaskManager.saveTasks(tasks);
    }
    updateStats();
}
private void updateStats() {
    int total = tasks.size();
    int completed = 0;

    for (Task t : tasks) {
        if (t.getStatus().equals("Done")) {
            completed++;
        }
    }

    statsLabel.setText("Total Tasks: " + total + " | Completed: " + completed);
}
private void applyPriorityColors() {
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            java.awt.Component cell = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            String priority = table.getValueAt(row, 3).toString();

            if (!isSelected) {
                if (priority.equals("High")) {
                    cell.setBackground(new Color(255, 180, 180));
                } else if (priority.equals("Medium")) {
                    cell.setBackground(new Color(255, 220, 150));
                } else {
                    cell.setBackground(new Color(200, 255, 200));
                }
            }

            return cell;
        }
    });
}
}