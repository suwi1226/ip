package bucket;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves the task list to a file and reads it back.
 * Each task is one line, e.g. "D | 0 | return book | 2019-10-15".
 */
public class Storage {
    //fixed path for the storage file, relative to the project root
    private static final Path FILE_PATH = Path.of("data", "bucket.txt");

    /**
     * Writes every task to the file, one per line, creating the data folder
     * on the first run. A failure is reported but does not stop the program.
     *
     * @param items the list to write out
     */
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

    /**
     * Reads the save file back into a task list. A missing or empty file is the
     * normal first-run case and gives an empty list rather than an error.
     * Lines that cannot be read are skipped so one bad line does not lose the rest.
     *
     * @return the tasks that were saved, or an empty list if there were none
     */
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

    /**
     * Turns one saved line back into a task, or returns null if the line is
     * corrupted. Which constructor to call can only be decided from the type
     * letter, so this is an if-chain rather than polymorphism.
     *
     * @param line one line from the save file
     * @return the task that line describes, or null if it cannot be read
     */
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
        try {
            if (type.equals("T") && values.length == 3) {
                t = new todo(name);
            } else if (type.equals("D") && values.length == 4) {
                t = new deadline(name, LocalDate.parse(values[3]));
            } else if (type.equals("E") && values.length == 5) {
                t = new event(name, LocalDate.parse(values[3]), LocalDate.parse(values[4]));
            }
        } catch (DateTimeParseException e) {
            //the saved date isn't a real date, so the whole line counts as corrupted
            return null;
        }

        if (t != null) {
            t.setDone(isDone);
        }
        return t;
    }
}
