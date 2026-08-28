package bucket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** A task that must be done before a given date, e.g. "return book (by: Oct 15 2019)". */
public class deadline extends task {
    //how the date is shown to the user - the save file uses plain yyyy-mm-dd instead
    private static final DateTimeFormatter DISPLAY = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private LocalDate by;

    /**
     * Creates a deadline.
     *
     * @param name the description the user typed
     * @param by the date the task is due
     */
    public deadline(String name, LocalDate by) {
        super(name);
        this.by = by;
    }

    /**
     * Returns the letter used for deadlines.
     *
     * @return "D"
     */
    @Override
    public String getTypeIcon() {
        return "D";
    }

    /**
     * Returns the save-file line with the due date appended in yyyy-mm-dd form,
     * which is what LocalDate.parse reads back.
     *
     * @return the save-file form of this deadline
     */
    @Override
    public String toSaveString() {
        return super.toSaveString() + " | " + this.by;
    }

    /**
     * Returns the display form with the due date in MMM dd yyyy form.
     *
     * @return the display form of this deadline
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + this.by.format(DISPLAY) + ")";
    }
}
