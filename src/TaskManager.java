import java.io.*;
import java.util.ArrayList;

public class TaskManager {

    private static final String FILE_NAME = "tasks.txt";

    public static void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.getName() + "," +
             task.getSubject() + "," +
             task.getDeadline() + "," +
             task.getPriority() + "," +
             task.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) return tasks;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

if (data.length == 5) {
    Task task = new Task(data[0], data[1], data[2], data[3]);
    if (data[4].equals("Done")) {
        task.markComplete();
    }
    tasks.add(task);
}

                Task task = new Task(data[0], data[1], data[2], data[3]);
                if (data[4].equals("Done")) {
                    task.markComplete();
                }

                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }
}