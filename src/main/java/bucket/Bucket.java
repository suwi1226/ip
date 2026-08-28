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
     * @param args not used
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.start();
        ui.showWelcome();

        //load whatever was saved last time
        taskList items = Storage.load();
        ui.showLoading();

        if (items.isEmpty()) {
            ui.showNoTasks();
        } else {
            ui.showList(items);
        }

        String input = ui.readCommand();

        while (!input.equalsIgnoreCase("bye")) {
            Parser parser = new Parser(input);
            String command = parser.getCommand(); //todo, event, deadline, mark, unmark, list, delete
            String argument = parser.getArgument(); //the rest of the line, if any

            if (command.equals("todo")) {
                //add todo
                if (argument.isEmpty()) {
                    ui.showError("OOPS!!! The description of a todo cannot be empty.");
                } else {
                    addTask(items, new todo(argument), ui);
                }

            } else if (command.equals("deadline")) {
                //add deadline
                try {
                    addTask(items, Parser.toDeadline(argument), ui);
                } catch (DateTimeParseException e) {
                    ui.showError("OOPS!!! Dates need to look like 2019-10-15.");
                }

            } else if (command.equals("event")) {
                //add event
                try {
                    addTask(items, Parser.toEvent(argument), ui);
                } catch (DateTimeParseException e) {
                    ui.showError("OOPS!!! Dates need to look like 2019-10-15.");
                }

            } else if (command.equals("mark") || command.equals("unmark")) {
                //mark or unmark a task
                boolean isDone = command.equals("mark");
                task t = items.get(Parser.toIndex(argument));
                t.setDone(isDone);
                ui.showMarked(t, isDone);

            } else if (command.equals("list")) {
                //list all tasks
                ui.showList(items);

            } else if (command.equals("delete")) {
                //delete a task
                int index = Parser.toIndex(argument);
                task t = items.get(index);
                items.removeItem(index);
                ui.showRemoved(t, items.size());

            } else {
                //unknown command
                ui.showError("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

            //the list might have changed, so write it out again
            Storage.save(items);

            input = ui.readCommand();
        }

        ui.close();
        ui.showGoodbye();
    }

    /**
     * Adds a task to the list and tells the user about it.
     *
     * @param items the list to add to
     * @param t the task being added
     * @param ui used to print the confirmation
     */
    private static void addTask(taskList items, task t, Ui ui) {
        items.addItem(t);
        ui.showAdded(t, items.size());
    }
}
