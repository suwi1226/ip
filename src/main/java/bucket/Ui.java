package bucket;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Handles everything the user sees or types, so no other class needs System.out.
 * Keeping the printing in one place means every message is formatted the same way.
 */
public class Ui {
    private static final String LINE_BREAK = "-------------------------";
    private static final String BANNER = " ____   _   _   ____  _  __ _____  _____ \n"
            + "| __ ) | | | | / ___|| |/ /| ____||_   _|\n"
            + "|  _ \\ | | | || |    | ' / |  _|    | |  \n"
            + "| |_) || |_| || |___ | . \\ | |___   | |  \n"
            + "|____/  \\___/  \\____||_|\\_\\|_____|  |_|  \n";

    private Scanner scanner;

    /**
     * Opens the input stream. Call this before reading any commands.
     * Pairs with close() so the scanner's lifetime is obvious from Bucket.
     */
    public void start() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line the user types.
     *
     * @return Raw line, before any parsing.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Closes the input stream. */
    public void close() {
        scanner.close();
    }

    /** Prints the banner and greeting shown at startup. */
    public void showWelcome() {
        System.out.println(LINE_BREAK);
        System.out.println(BANNER);
        System.out.println("Hello! I'm Bucket\nYou again?");
        System.out.println(LINE_BREAK);
    }

    /** Prints the loading message, with a short pause so it is readable. */
    public void showLoading() {
        System.out.println("Loading tasks ...");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /** Tells the user nothing was loaded from the save file. */
    public void showNoTasks() {
        System.out.println("\nNo tasks found.");
        System.out.println(LINE_BREAK);
    }

    /**
     * Prints the whole task list.
     *
     * @param items List to print.
     */
    public void showList(TaskList items) {
        System.out.println(items);
    }

    /**
     * Confirms that a task was added.
     *
     * @param task Task that was added.
     * @param count How many tasks there are now.
     */
    public void showAdded(Task task, int count) {
        System.out.println(LINE_BREAK);
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
        System.out.println(LINE_BREAK);
    }

    /**
     * Confirms that a task was deleted.
     *
     * @param task Task that was removed.
     * @param count How many tasks are left.
     */
    public void showRemoved(Task task, int count) {
        System.out.println(LINE_BREAK);
        System.out.println("Noted. I've removed this task:");
        System.out.println("    " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
        System.out.println(LINE_BREAK);
    }

    /**
     * Confirms that a task was marked done or not done.
     *
     * @param task Task that changed.
     * @param isDone True if it was marked done, false if unmarked.
     */
    public void showMarked(Task task, boolean isDone) {
        System.out.println(LINE_BREAK);
        System.out.println(isDone
                ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:");
        System.out.println("  " + task);
        System.out.println(LINE_BREAK);
    }

    /**
     * Prints an error, so every error message comes out looking the same.
     *
     * @param message Text to show the user.
     */
    public void showError(String message) {
        System.out.println(LINE_BREAK);
        System.out.println(message);
        System.out.println(LINE_BREAK);
    }

    /**
     * Prints the tasks that matched a find, numbered from 1.
     * The numbering restarts at 1, so it does not line up with the full list.
     *
     * @param matches Tasks that matched the keyword.
     */
    public void showFound(TaskList matches) {
        System.out.println(LINE_BREAK);
        if (matches.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + "." + matches.get(i));
            }
        }
        System.out.println(LINE_BREAK);
    }

    /** Prints the sign-off shown when the user types bye. */
    public void showGoodbye() {
        System.out.println("\nBYEEEEEEE!");
        System.out.println(LINE_BREAK);
    }
}
