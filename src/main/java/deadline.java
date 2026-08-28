/** A task that must be done before a given date, e.g. "return book (by: Sunday)". */
public class deadline extends task {
    private String by;

    public deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + " | " + this.by;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by + ")";
    }
}
