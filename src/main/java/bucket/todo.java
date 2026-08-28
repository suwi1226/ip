package bucket;

/** A task with no date attached, e.g. "borrow book". */
public class todo extends task {

    /**
     * Creates a todo.
     *
     * @param name the description the user typed
     */
    public todo(String name) {
        super(name);
    }

    /**
     * Returns the letter used for todos.
     *
     * @return "T"
     */
    @Override
    public String getTypeIcon() {
        return "T";
    }
}
