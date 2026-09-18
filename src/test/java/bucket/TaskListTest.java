package bucket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    private Deadline deadlineOn(String name, int year, int month, int day) {
        return new Deadline(name, LocalDate.of(year, month, day));
    }

    private Event eventFrom(String name, int year, int month, int day) {
        return new Event(name,
                LocalDate.of(year, month, day),
                LocalDate.of(year, month, day).plusDays(1));
    }

    /** Returns the descriptions in list order, which is what sorting rearranges. */
    private String namesInOrder(TaskList items) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(items.get(i).getName());
        }
        return sb.toString();
    }

    @Test
    public void sort_deadlinesOutOfOrder_orderedChronologically() {
        TaskList items = new TaskList();
        items.addItem(deadlineOn("december", 2019, 12, 1));
        items.addItem(deadlineOn("january", 2019, 1, 15));
        items.addItem(deadlineOn("june", 2019, 6, 30));

        items.sort();

        assertEquals("january,june,december", namesInOrder(items));
    }

    // An event is ordered by when it starts, not when it ends
    @Test
    public void sort_eventsWithDifferentStarts_orderedByStartDate() {
        TaskList items = new TaskList();
        items.addItem(eventFrom("later", 2019, 10, 20));
        items.addItem(eventFrom("earlier", 2019, 10, 5));

        items.sort();

        assertEquals("earlier,later", namesInOrder(items));
    }

    // Deadlines and events interleave by date; todos have no date so they follow
    @Test
    public void sort_mixedTypes_todosLast() {
        TaskList items = new TaskList();
        items.addItem(new Todo("no date"));
        items.addItem(deadlineOn("due in march", 2019, 3, 1));
        items.addItem(eventFrom("meets in february", 2019, 2, 1));

        items.sort();

        assertEquals("meets in february,due in march,no date", namesInOrder(items));
    }

    // Todos all compare equal, and a stable sort leaves equal items where they were
    @Test
    public void sort_todosOnly_insertionOrderKept() {
        TaskList items = new TaskList();
        items.addItem(new Todo("first"));
        items.addItem(new Todo("second"));
        items.addItem(new Todo("third"));

        items.sort();

        assertEquals("first,second,third", namesInOrder(items));
    }

    // Two deadlines on one day keep their original order for the same reason
    @Test
    public void sort_sameDate_insertionOrderKept() {
        TaskList items = new TaskList();
        items.addItem(deadlineOn("added first", 2019, 5, 1));
        items.addItem(deadlineOn("added second", 2019, 5, 1));

        items.sort();

        assertEquals("added first,added second", namesInOrder(items));
    }

    @Test
    public void sort_emptyList_staysEmpty() {
        TaskList items = new TaskList();

        items.sort();

        assertEquals(0, items.size());
    }

    // Sorting rearranges the list without adding or dropping anything
    @Test
    public void sort_mixedTypes_sizeUnchanged() {
        TaskList items = new TaskList();
        items.addItem(new Todo("no date"));
        items.addItem(deadlineOn("due", 2019, 3, 1));
        items.addItem(eventFrom("meets", 2019, 2, 1));

        items.sort();

        assertEquals(3, items.size());
    }
}
