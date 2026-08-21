public class task {
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
}
