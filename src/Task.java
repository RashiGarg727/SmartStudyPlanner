public class Task {
    private String priority;
    private String name;
    private String subject;
    private String deadline;
    private String status;

    public Task(String name, String subject, String deadline, String priority) {
    this.name = name;
    this.subject = subject;
    this.deadline = deadline;
    this.priority = priority;
    this.status = "Pending";
}
    public String getPriority() {
    return priority;
} 
    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public void markComplete() {
        status = "Done";
    }
}