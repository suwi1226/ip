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
     * @return the raw line, before any parsing
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
     * @param items the list to print
     */
    public void showList(taskList items) {
        System.out.println(items);
    }

    /**
     * Confirms that a task was added.
     *
     * @param t the task that was added
     * @param count how many tasks there are now
     */
    public void showAdded(task t, int count) {
        System.out.println(LINE_BREAK);
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + t);
        System.out.println("Now you have " + count + " tasks in the list.");
        System.out.println(LINE_BREAK);
    }

    /**
     * Confirms that a task was deleted.
     *
     * @param t the task that was removed
     * @param count how many tasks are left
     */
    public void showRemoved(task t, int count) {
        System.out.println(LINE_BREAK);
        System.out.println("Noted. I've removed this task:");
        System.out.println("    " + t);
        System.out.println("Now you have " + count + " tasks in the list.");
        System.out.println(LINE_BREAK);
    }

    /**
     * Confirms that a task was marked done or not done.
     *
     * @param t the task that changed
     * @param isDone true if it was marked done, false if unmarked
     */
    public void showMarked(task t, boolean isDone) {
        System.out.println(LINE_BREAK);
        System.out.println(isDone
                ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:");
        System.out.println("  " + t);
        System.out.println(LINE_BREAK);
    }

    /**
     * Prints an error, so every error message comes out looking the same.
     *
     * @param message the text to show the user
     */
    public void showError(String message) {
        System.out.println(LINE_BREAK);
        System.out.println(message);
        System.out.println(LINE_BREAK);
    }

    /** Prints the sign-off shown when the user types bye. */
    public void showGoodbye() {
        System.out.println("\nBYEEEEEEE!");
        System.out.println(LINE_BREAK);
    }
}
