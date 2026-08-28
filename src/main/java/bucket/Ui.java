package bucket;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//everything the user sees or types goes through here, so no other class needs System.out

public class Ui {
    private static final String LINE_BREAK = "-------------------------";
    private static final String BANNER = " ____   _   _   ____  _  __ _____  _____ \n"
            + "| __ ) | | | | / ___|| |/ /| ____||_   _|\n"
            + "|  _ \\ | | | || |    | ' / |  _|    | |  \n"
            + "| |_) || |_| || |___ | . \\ | |___   | |  \n"
            + "|____/  \\___/  \\____||_|\\_\\|_____|  |_|  \n";

    private Scanner scanner;

    //opens the input stream - call this before reading any commands.
    //pairs with close() so the scanner's lifetime is obvious from Bucket
    public void start() {
        scanner = new Scanner(System.in);
    }

    //reads the next line the user types
    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

    public void showWelcome() {
        System.out.println(LINE_BREAK);
        System.out.println(BANNER);
        System.out.println("Hello! I'm Bucket\nYou again?");
        System.out.println(LINE_BREAK);
    }

    //small pause so it looks like it's actually doing something
    public void showLoading() {
        System.out.println("Loading tasks ...");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void showNoTasks() {
        System.out.println("\nNo tasks found.");
        System.out.println(LINE_BREAK);
    }

    public void showList(taskList items) {
        System.out.println(items);
    }

    public void showAdded(task t, int count) {
        System.out.println(LINE_BREAK);
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + t);
        System.out.println("Now you have " + count + " tasks in the list.");
        System.out.println(LINE_BREAK);
    }

    public void showRemoved(task t, int count) {
        System.out.println(LINE_BREAK);
        System.out.println("Noted. I've removed this task:");
        System.out.println("    " + t);
        System.out.println("Now you have " + count + " tasks in the list.");
        System.out.println(LINE_BREAK);
    }

    public void showMarked(task t, boolean isDone) {
        System.out.println(LINE_BREAK);
        System.out.println(isDone
                ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:");
        System.out.println("  " + t);
        System.out.println(LINE_BREAK);
    }

    //every error message comes out looking the same
    public void showError(String message) {
        System.out.println(LINE_BREAK);
        System.out.println(message);
        System.out.println(LINE_BREAK);
    }

    public void showGoodbye() {
        System.out.println("\nBYEEEEEEE!");
        System.out.println(LINE_BREAK);
    }
}
