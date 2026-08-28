package bucket;

import java.time.format.DateTimeParseException;

public class Bucket {
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.start();
        ui.showWelcome();

        //load whatever was saved last time
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
            String command = parser.getCommand(); //todo, event, deadline, mark, unmark, list, delete
            String argument = parser.getArgument(); //the rest of the line, if any

            if (command.equals("todo")) {
                //add todo
                if (argument.isEmpty()) {
                    ui.showError("OOPS!!! The description of a todo cannot be empty.");
                } else {
                    addTask(items, new Todo(argument), ui);
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
                Task t = items.get(Parser.toIndex(argument));
                t.setDone(isDone);
                ui.showMarked(t, isDone);

            } else if (command.equals("list")) {
                //list all tasks
                ui.showList(items);

            } else if (command.equals("delete")) {
                //delete a task
                int index = Parser.toIndex(argument);
                Task t = items.get(index);
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

    //adds a task to the list and tells the user about it
    private static void addTask(TaskList items, Task t, Ui ui) {
        items.addItem(t);
        ui.showAdded(t, items.size());
    }
}
