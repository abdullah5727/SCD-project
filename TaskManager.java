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
}
