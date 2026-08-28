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

    //one line for the save file, like T | 1 | read book
    public String toSaveString() {
        return String.format("%s | %d | %s", getTypeIcon(), isDone ? 1 : 0, this.name);
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getTypeIcon(), doneString(), this.name);
    }
}
