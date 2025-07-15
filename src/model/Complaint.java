package model;

public class Complaint {
    private int id;
    private String userEmail;
    private String title;
    private String description;
    private String status;

    public Complaint() {}

    public Complaint(String userEmail, String title, String description) {
        this.userEmail = userEmail;
        this.title = title;
        this.description = description;
        this.status = "Open";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
