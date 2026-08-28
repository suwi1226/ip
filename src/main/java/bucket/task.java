package bucket;

/**
 * Base class for every kind of task - todo, deadline and event.
 * Holds the description and done state that all tasks share.
 */
public abstract class task {
    private boolean isDone;
    private String name;

    /**
     * Creates a task that starts off not done.
     *
     * @param name the description the user typed
     */
    public task(String name) {
        this.isDone = false;
        this.name = name;
    }

    /**
     * Returns the mark shown inside the second pair of brackets.
     *
     * @return "X" if done, a single space if not
     */
    public String doneString() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns this task's description.
     *
     * @return the description
     */
    public String getName() {
        return name;
    }

    /**
     * Marks this task as done or not done.
     *
     * @param isDone true to mark it done
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns the letter shown inside the first pair of brackets.
     * Each subclass answers for itself.
     *
     * @return "T", "D" or "E"
     */
    public abstract String getTypeIcon();

    /**
     * Returns one line for the save file, like "T | 1 | read book".
     * Subclasses append their own extra fields onto this.
     *
     * @return the save-file form of this task
     */
    public String toSaveString() {
        return String.format("%s | %d | %s", getTypeIcon(), isDone ? 1 : 0, this.name);
    }

    /**
     * Returns the form shown to the user, like "[T][X] read book".
     *
     * @return the display form of this task
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getTypeIcon(), doneString(), this.name);
    }
}
