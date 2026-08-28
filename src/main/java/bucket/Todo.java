package bucket;

/** A task with no date attached, e.g. "borrow book". */
public class Todo extends Task {

    /**
     * Creates a todo.
     *
     * @param name Description the user typed.
     */
    public Todo(String name) {
        super(name);
    }

    @Override
    public String getTypeIcon() {
        return "T";
    }
}
