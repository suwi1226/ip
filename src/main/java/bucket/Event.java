package bucket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    //how the dates are shown to the user - the save file uses plain yyyy-mm-dd instead
    private static final DateTimeFormatter DISPLAY = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private LocalDate from;
    private LocalDate to;

    public Event(String name, LocalDate from, LocalDate to) {
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
        return super.toString() + " (from: " + this.from.format(DISPLAY)
                + " to: " + this.to.format(DISPLAY) + ")";
    }
}
