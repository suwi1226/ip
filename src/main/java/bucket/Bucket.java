package bucket;

import java.time.format.DateTimeParseException;

/**
 * The chatbot itself: holds the task list and turns one line of user input
 * into one reply.
 *
 * The command loop used to live here. It has been pulled out into getResponse so
 * that a caller decides when to hand over the next line. The console loop below
 * still reads lines in a while loop, while the GUI calls getResponse once per
 * button press, which is the only shape an event-driven front end can use.
 */
public class Bucket {
    private final TaskList items;
    private final Ui ui;

    /**
     * Creates a chatbot with whatever was saved last time already loaded.
     * JavaFX needs a no-argument constructor, and there is nothing to configure,
     * so this is the only one.
     */
    public Bucket() {
        this.ui = new Ui();
        this.items = Storage.load();
    }

    /**
     * Returns whether a line asks the chatbot to shut down.
     * Both front ends need to know this, so it lives here rather than in either one.
     *
     * @param input Raw line the user typed.
     * @return True if the line is the bye command.
     */
    public static boolean isExitCommand(String input) {
        return input.trim().equalsIgnoreCase("bye");
    }

    /**
     * Returns the greeting plus whatever was loaded from the save file.
     * Shown once when a front end starts up.
     *
     * @return Startup message.
     */
    public String getWelcomeMessage() {
        String body = items.isEmpty() ? ui.getNoTasks() : ui.getList(items);
        return ui.getWelcome() + "\n\n" + body;
    }

    /**
     * Runs one line of user input and returns what the chatbot says back.
     * The list is written out afterwards, so a crash cannot lose a change.
     *
     * Bad input is answered with a message rather than an exception: a GUI has no
     * console to print a stack trace to, and one mistyped command should not take
     * the window down.
     *
     * @param input Raw line the user typed.
     * @return Reply to show the user.
     */
    public String getResponse(String input) {
        Parser parser = new Parser(input);
        String command = parser.getCommand();
        String argument = parser.getArgument();

        try {
            String response = execute(command, argument);
            Storage.save(items);
            return response;

        } catch (DateTimeParseException e) {
            return ui.getError("OOPS!!! Dates need to look like 2019-10-15.");
        } catch (ArrayIndexOutOfBoundsException e) {
            return ui.getError("OOPS!!! That command is missing a part. Try\n"
                    + "  deadline return book /by 2019-10-15\n"
                    + "  event project meeting /from 2019-10-15 /to 2019-10-16");
        } catch (NumberFormatException e) {
            return ui.getError("OOPS!!! I need a task number, e.g. mark 2.");
        } catch (IndexOutOfBoundsException e) {
            return ui.getError("OOPS!!! There is no task with that number.");
        }
    }

    /**
     * Picks the right action for a command word and returns its reply.
     * Anything malformed is left to throw, so getResponse can turn every failure
     * into a message in one place.
     *
     * @param command Command word, e.g. "deadline".
     * @param argument Rest of the line, if any.
     * @return Reply to show the user.
     */
    private String execute(String command, String argument) {
        if (command.equals("todo")) {
            if (argument.isEmpty()) {
                return ui.getError("OOPS!!! The description of a todo cannot be empty.");
            }
            return addTask(new Todo(argument));

        } else if (command.equals("deadline")) {
            return addTask(Parser.toDeadline(argument));

        } else if (command.equals("event")) {
            return addTask(Parser.toEvent(argument));

        } else if (command.equals("mark") || command.equals("unmark")) {
            boolean isDone = command.equals("mark");
            Task task = items.get(Parser.toIndex(argument));
            task.setDone(isDone);
            return ui.getMarked(task, isDone);

        } else if (command.equals("list")) {
            return ui.getList(items);

        } else if (command.equals("find")) {
            if (argument.isEmpty()) {
                return ui.getError("OOPS!!! Tell me what to search for, e.g. find book.");
            }
            return ui.getFound(items.find(argument));

        } else if (command.equals("delete")) {
            int index = Parser.toIndex(argument);
            Task task = items.get(index);
            items.removeItem(index);
            return ui.getRemoved(task, items.size());

        } else if (isExitCommand(command)) {
            return ui.getGoodbye();

        } else {
            return ui.getError("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Adds a task to the list and returns the confirmation for it.
     *
     * @param task Task being added.
     * @return Confirmation text.
     */
    private String addTask(Task task) {
        items.addItem(task);
        return ui.getAdded(task, items.size());
    }

    /**
     * Runs the chatbot on the console.
     * The GUI does not come through here; it starts at Launcher instead.
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        Bucket bucket = new Bucket();
        Ui ui = new Ui();
        ui.start();

        System.out.println(ui.getBanner());
        printBlock(bucket.getWelcomeMessage());

        String input = ui.readCommand();
        while (!isExitCommand(input)) {
            printBlock(bucket.getResponse(input));
            input = ui.readCommand();
        }

        printBlock(bucket.getResponse(input));
        ui.close();
    }

    /**
     * Prints one message fenced by dividers, so console output stays evenly spaced.
     *
     * @param message Text to print.
     */
    private static void printBlock(String message) {
        System.out.println(Ui.LINE_BREAK);
        System.out.println(message);
        System.out.println(Ui.LINE_BREAK);
    }
}
