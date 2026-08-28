package bucket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

//tests for turning the raw "event ..." argument into an event object

public class ParserTest {

    //the save form is used to check the parse, because it shows the dates
    //as plain yyyy-mm-dd - so a wrong date can't hide behind the display format
    @Test
    public void toEvent_validInput_allThreePartsParsed() {
        event e = Parser.toEvent("project meeting /from 2019-10-15 /to 2019-10-16");
        assertEquals("E | 0 | project meeting | 2019-10-15 | 2019-10-16", e.toSaveString());
    }

    //the description must survive the split with its spaces intact,
    //and the /from and /to markers must not be swallowed into it
    @Test
    public void toEvent_descriptionWithManySpaces_keptWhole() {
        event e = Parser.toEvent("team lunch at the new place /from 2020-01-01 /to 2020-01-02");
        assertEquals("E | 0 | team lunch at the new place | 2020-01-01 | 2020-01-02", e.toSaveString());
    }

    //a date that isn't a real date should be rejected, not silently accepted
    @Test
    public void toEvent_invalidDate_throwsDateTimeParseException() {
        assertThrows(DateTimeParseException.class, ()
                -> Parser.toEvent("meeting /from tomorrow /to 2019-10-16"));
    }

    //month 13 parses as text but isn't a real date, so it must still be rejected
    @Test
    public void toEvent_impossibleDate_throwsDateTimeParseException() {
        assertThrows(DateTimeParseException.class, ()
                -> Parser.toEvent("meeting /from 2019-13-01 /to 2019-10-16"));
    }
}
