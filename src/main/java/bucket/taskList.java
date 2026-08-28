package bucket;

import java.util.ArrayList;

public class taskList {
    private ArrayList<task> items = new ArrayList<>();

    public void addItem(task item) {
        items.add(item);
    }

    public void removeItem(int index) {
        items.remove(index);
    }

    public task get(int index) {
        return items.get(index);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    //returns the tasks whose description contains the keyword, ignoring case
    public taskList find(String keyword) {
        taskList matches = new taskList();
        String needle = keyword.toLowerCase();
        for (task t : items) {
            if (t.getName().toLowerCase().contains(needle)) {
                matches.addItem(t);
            }
        }
        return matches;
    }

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
