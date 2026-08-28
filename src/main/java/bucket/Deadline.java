package bucket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    //how the date is shown to the user - the save file uses plain yyyy-mm-dd instead
    private static final DateTimeFormatter DISPLAY = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private LocalDate by;

    public Deadline(String name, LocalDate by) {
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
        return super.toString() + " (by: " + this.by.format(DISPLAY) + ")";
    }
}
