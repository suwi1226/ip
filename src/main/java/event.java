/** A task spanning a start and end time, e.g. "project meeting (from: Mon 2pm to: 4pm)". */
public class event extends task {
    private String from;
    private String to;

    public event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String toSaveString() {
        return super.toSaveString() + " | " + this.from + " | " + this.to;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
