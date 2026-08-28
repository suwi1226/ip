package bucket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** A task spanning a start and end date, e.g. "project meeting (from: Oct 15 2019 to: Oct 16 2019)". */
public class event extends task {
    //how the dates are shown to the user - the save file uses plain yyyy-mm-dd instead
    private static final DateTimeFormatter DISPLAY = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private LocalDate from;
    private LocalDate to;

    /**
     * Creates an event.
     *
     * @param name the description the user typed
     * @param from the date the event starts
     * @param to the date the event ends
     */
    public event(String name, LocalDate from, LocalDate to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the letter used for events.
     *
     * @return "E"
     */
    @Override
    public String getTypeIcon() {
        return "E";
    }

    /**
     * Returns the save-file line with both dates appended in yyyy-mm-dd form,
     * which is what LocalDate.parse reads back.
     *
     * @return the save-file form of this event
     */
    @Override
    public String toSaveString() {
        return super.toSaveString() + " | " + this.from + " | " + this.to;
    }

    /**
     * Returns the display form with both dates in MMM dd yyyy form.
     *
     * @return the display form of this event
     */
    @Override
    public String toString() {
        return super.toString() + " (from: " + this.from.format(DISPLAY)
                + " to: " + this.to.format(DISPLAY) + ")";
    }
}
