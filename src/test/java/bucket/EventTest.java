package bucket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

//tests for how an event renders itself, both on screen and in the save file.
//built directly from LocalDates here, so no parsing is involved

public class EventTest {

    private Event makeEvent() {
        return new Event("project meeting",
                LocalDate.of(2019, 10, 15),
                LocalDate.of(2019, 10, 16));
    }

    //on screen the dates are the friendly MMM dd yyyy form
    @Test
    public void toString_notDone_showsEmptyBoxAndFormattedDates() {
        assertEquals("[E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)",
                makeEvent().toString());
    }

    @Test
    public void toString_done_showsCross() {
        Event e = makeEvent();
        e.setDone(true);
        assertEquals("[E][X] project meeting (from: Oct 15 2019 to: Oct 16 2019)",
                e.toString());
    }

    //in the file the dates stay as yyyy-mm-dd, so LocalDate.parse can read them back
    @Test
    public void toSaveString_notDone_usesZeroAndIsoDates() {
        assertEquals("E | 0 | project meeting | 2019-10-15 | 2019-10-16",
                makeEvent().toSaveString());
    }

    @Test
    public void toSaveString_done_usesOne() {
        Event e = makeEvent();
        e.setDone(true);
        assertEquals("E | 1 | project meeting | 2019-10-15 | 2019-10-16",
                e.toSaveString());
    }
}
