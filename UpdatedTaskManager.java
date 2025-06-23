package ToDoApp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskManager {
    private ArrayList<Task> taskList;

    public TaskManager() {
        taskList = new ArrayList<>();
    }

    public void addTask(Task task) {
        taskList.add(task);
        scheduleReminder(task);
    }

    public void deleteTask(int index) {
        if (index > -1 && index < taskList.size()) {
            Task t = taskList.get(index);
            System.out.println("Deleting task: " + t.getTitle());
            taskList.remove(index);
        }
    }

    public void markAsCompleted(int index) {
        if (index >= 0 && index < taskList.size()) {
            taskList.get(index).markAsCompleted();
        }
    }

    public ArrayList<Task> getTasks() {
        return taskList;
    }

    public void sortTasksByDueDate() {
        Collections.sort(taskList, Comparator.comparing(Task::getDueDate));
    }

    public void scheduleReminder(Task task) {
        System.out.println("Reminder scheduled for: " + task.getTitle());
    }
}
