import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//saves and loads the task list from a file

public class Storage {
    //fixed path for the storage file, relative to the project root
    private static final Path FILE_PATH = Path.of("data", "bucket.txt");

    //writes every task to the file, one per line
    public static void save(taskList items) {
        try {
            //the folder has to exist first - Files.write won't make it.
            //the file itself doesn't need checking, Files.write creates it if it's missing
            Files.createDirectories(FILE_PATH.getParent());

            ArrayList<String> lines = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                lines.add(items.get(i).toSaveString());
            }
            Files.write(FILE_PATH, lines);

        } catch (IOException e) {
            System.out.println("Couldn't save tasks to file - changes will be lost when the program exits.");
        }
    }

    //reads the file bucket.txt back into a taskList, or an empty one if there's nothing saved
    public static taskList load() {
        taskList items = new taskList();

        //first run - the file isn't there yet - return an empty list
        if (!Files.exists(FILE_PATH)) {
            return items;
        }

        try {
            List<String> lines = Files.readAllLines(FILE_PATH);

            //file is there but there's nothing in it - return an empty list
            if (lines.isEmpty()) {
                return items;
            }

            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }

                //parse gives back null for a corrupted line, so that line just gets skipped
                task t = parse(line);
                if (t != null) {
                    items.addItem(t);
                }
            }

        } catch (IOException e) {
            System.out.println("Couldn't read tasks from file - starting with an empty list.");
        }

        return items;
    }

    //turns one line like "D | 0 | return book | June 6th" back into a task
    private static task parse(String line) {
        //the | has to be escaped because split takes a regex, where | means "or"
        String[] values = line.split(" \\| ");

        if (values.length < 3) {
            return null;
        }

        String type = values[0];
        boolean isDone = values[1].equals("1");
        String name = values[2];

        //each type has its own number of values, so a wrong count means it's corrupted
        task t = null;
        if (type.equals("T") && values.length == 3) {
            t = new todo(name);
        } else if (type.equals("D") && values.length == 4) {
            t = new deadline(name, values[3]);
        } else if (type.equals("E") && values.length == 5) {
            t = new event(name, values[3], values[4]);
        }

        if (t != null) {
            t.setDone(isDone);
        }
        return t;
    }
}
