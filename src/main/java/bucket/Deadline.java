package bucket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** A task that must be done before a given date, e.g. "return book (by: Oct 15 2019)". */
public class Deadline extends Task {
    /** How the date is shown to the user; the save file uses plain yyyy-mm-dd instead */
    private static final DateTimeFormatter DISPLAY = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private LocalDate by;

    /**
     * Creates a deadline.
     *
     * @param name Description the user typed.
     * @param by Date the task is due.
     */
    public Deadline(String name, LocalDate by) {
        super(name);
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return "D";
    }

    /**
     * Returns the save-file line with the due date appended in yyyy-mm-dd form,
     * which is what LocalDate.parse reads back.
     *
     * @return Save-file form of this deadline.
     */
    @Override
    public String toSaveString() {
        return super.toSaveString() + " | " + this.by;
    }

    /**
     * Returns the display form with the due date in MMM dd yyyy form.
     *
     * @return Display form of this deadline.
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by.format(DISPLAY) + ")";
    }
}
