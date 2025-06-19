public class Task {
    private String title;
    private String dueDate;
    private boolean isCompleted;

    public Task(String title, String dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        isCompleted = true;
    }

    @Override
    public String toString() {
        return title + " | Due: " + dueDate + " | Status: " + (isCompleted ? "Completed" : "Pending");
    }
}
