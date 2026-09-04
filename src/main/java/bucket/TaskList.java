package bucket;

import java.util.ArrayList;

/** Holds the tasks the user has added, in the order they were added. */
public class TaskList {
    private ArrayList<Task> items = new ArrayList<>();

    /**
     * Adds a task to the end of the list.
     *
     * @param item Task to add.
     */
    public void addItem(Task item) {
        items.add(item);
    }

    /**
     * Removes the task at the given position.
     *
     * @param index Zero-based position of the task to remove.
     */
    public void removeItem(int index) {
        items.remove(index);
    }

    /**
     * Returns the task at the given position.
     *
     * @param index Zero-based position, so "mark 2" means index 1.
     * @return Task at that position.
     */
    public Task get(int index) {
        return items.get(index);
    }

    /**
     * Returns how many tasks are stored.
     *
     * @return Number of tasks in the list.
     */
    public int size() {
        return items.size();
    }

    /**
     * Returns whether there are no tasks.
     *
     * @return True if the list is empty.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Returns the tasks whose description contains the given keyword.
     * Matching ignores case, so "BOOK" finds "read book".
     *
     * @param keyword Text to look for in each task description.
     * @return New list holding only the matching tasks, in their original order.
     */
    public TaskList find(String keyword) {
        TaskList matches = new TaskList();
        String needle = keyword.toLowerCase();
        for (Task task : items) {
            if (task.getName().toLowerCase().contains(needle)) {
                matches.addItem(task);
            }
        }
        return matches;
    }

    /**
     * Returns the tasks numbered from 1, one per line.
     * Headings and dividers are left to Ui, so the same text suits the console
     * and the GUI without either front end having to strip anything out.
     *
     * @return Display form of the list.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(String.format("%d.%s", i + 1, items.get(i)));
        }
        return sb.toString();
    }
}
