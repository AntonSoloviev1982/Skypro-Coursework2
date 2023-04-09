package model;



import java.time.LocalDate;
import java.util.Objects;

public class Task {

    public static int idGenerator = 1;

    private int id;

    private String title;

    private RepeatabilityEnum repeatability;

    private String description;

    private TypeEnum type;

    private LocalDate dateTime;

    public Task() {
    }

    public Task(String title, RepeatabilityEnum repeatability, String description,
                TypeEnum type) {
        this.title = title;
        this.repeatability = repeatability;
        this.description = description;
        this.type = type;
        this.dateTime = LocalDate.now();
        this.id = idGenerator;
        idGenerator++;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public RepeatabilityEnum getRepeatability() {
        return repeatability;
    }

    public String getDescription() {
        return description;
    }

    public TypeEnum getType() {
        return type;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", repeatability=" + repeatability +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", dateTime=" + dateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && repeatability == task.repeatability
                && Objects.equals(description, task.description) && type == task.type
                && Objects.equals(dateTime, task.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, repeatability, description, type, dateTime);
    }

}
