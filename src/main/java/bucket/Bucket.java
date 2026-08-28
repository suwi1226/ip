package bucket;

import java.time.format.DateTimeParseException;

/**
 * Entry point for the Bucket chatbot.
 * Wires together the Ui, the Parser and the Storage, and runs the command loop
 * until the user types bye.
 */
public class Bucket {

    /**
     * Starts the chatbot.
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.start();
        ui.showWelcome();

        // Load whatever was saved last time
        TaskList items = Storage.load();
        ui.showLoading();

        if (items.isEmpty()) {
            ui.showNoTasks();
        } else {
            ui.showList(items);
        }

        String input = ui.readCommand();

        while (!input.equalsIgnoreCase("bye")) {
            Parser parser = new Parser(input);
            String command = parser.getCommand(); // todo, event, deadline, mark, unmark, list, delete
            String argument = parser.getArgument(); // The rest of the line, if any

            if (command.equals("todo")) {
                if (argument.isEmpty()) {
                    ui.showError("OOPS!!! The description of a todo cannot be empty.");
                } else {
                    addTask(items, new Todo(argument), ui);
                }

            } else if (command.equals("deadline")) {
                try {
                    addTask(items, Parser.toDeadline(argument), ui);
                } catch (DateTimeParseException e) {
                    ui.showError("OOPS!!! Dates need to look like 2019-10-15.");
                }

            } else if (command.equals("event")) {
                try {
                    addTask(items, Parser.toEvent(argument), ui);
                } catch (DateTimeParseException e) {
                    ui.showError("OOPS!!! Dates need to look like 2019-10-15.");
                }

            } else if (command.equals("mark") || command.equals("unmark")) {
                boolean isDone = command.equals("mark");
                Task task = items.get(Parser.toIndex(argument));
                task.setDone(isDone);
                ui.showMarked(task, isDone);

            } else if (command.equals("list")) {
                ui.showList(items);

            } else if (command.equals("delete")) {
                int index = Parser.toIndex(argument);
                Task task = items.get(index);
                items.removeItem(index);
                ui.showRemoved(task, items.size());

            } else {
                ui.showError("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

            // The list might have changed, so write it out again
            Storage.save(items);

            input = ui.readCommand();
        }

        ui.close();
        ui.showGoodbye();
    }

    /**
     * Adds a task to the list and tells the user about it.
     *
     * @param items List to add to.
     * @param task Task being added.
     * @param ui Used to print the confirmation.
     */
    private static void addTask(TaskList items, Task task, Ui ui) {
        items.addItem(task);
        ui.showAdded(task, items.size());
    }
}
