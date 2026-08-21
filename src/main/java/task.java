//abstract class for all tasks - todo, event, deadline

public abstract class task {
    private boolean isDone;
    private String name;

    public task(String name) {
        this.isDone = false;
        this.name = name;
    }

    public String doneString() {
        return (isDone ? "X" : " ");
    }

    public String getName() {
        return name;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public abstract String getTypeIcon();

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getTypeIcon(), doneString(), this.name);
    }
}
