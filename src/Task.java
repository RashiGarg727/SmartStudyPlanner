public class Task {
    private String name;
    private String subject;
    private String deadline;
    private String status;

    public Task(String name, String subject, String deadline) {
        this.name = name;
        this.subject = subject;
        this.deadline = deadline;
        this.status = "Pending";
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