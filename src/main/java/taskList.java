import java.util.ArrayList;

public class taskList {
    ArrayList<task> items = new ArrayList<>(100);
    int index = 0;

    public taskList() {
    }

    public void addItem(task item) {
        items.add(item);
        index++;
    }

    public task get(int index) {
        return items.get(index);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------\n");
        sb.append("Here are the items in your bucket:\n");
        for (int i = 0; i < index; i++) {
            sb.append(String.format("[%s] %d. %s\n", items.get(i).doneString(), (i + 1), items.get(i).getName()));
        }
        sb.append("-------------------------");
        return sb.toString();
    }
}
