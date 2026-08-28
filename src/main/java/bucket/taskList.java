package bucket;

import java.util.ArrayList;

/** Holds the tasks the user has added, in the order they were added. */
public class taskList {
    private ArrayList<task> items = new ArrayList<>();

    /**
     * Adds a task to the end of the list.
     *
     * @param item the task to add
     */
    public void addItem(task item) {
        items.add(item);
    }

    /**
     * Removes the task at the given position.
     *
     * @param index zero-based position of the task to remove
     */
    public void removeItem(int index) {
        items.remove(index);
    }

    /**
     * Returns the task at the given position.
     *
     * @param index zero-based position, so "mark 2" means index 1
     * @return the task at that position
     */
    public task get(int index) {
        return items.get(index);
    }

    /**
     * Returns how many tasks are stored.
     *
     * @return the number of tasks
     */
    public int size() {
        return items.size();
    }

    /**
     * Returns whether there are no tasks.
     *
     * @return true if the list is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Returns the whole list numbered from 1, ready to print.
     *
     * @return the display form of the list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------\n");
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append(String.format("%d.%s\n", i + 1, items.get(i)));
        }
        sb.append("-------------------------");
        return sb.toString();
    }
}
