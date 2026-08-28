import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Bucket {
    private static final String LINE_BREAK = "-------------------------";

    public static void main(String[] args) {
        String banner = " ____   _   _   ____  _  __ _____  _____ \n"
                + "| __ ) | | | | / ___|| |/ /| ____||_   _|\n"
                + "|  _ \\ | | | || |    | ' / |  _|    | |  \n"
                + "| |_) || |_| || |___ | . \\ | |___   | |  \n"
                + "|____/  \\___/  \\____||_|\\_\\|_____|  |_|  \n";
        System.out.println(LINE_BREAK);
        System.out.println(banner);
        System.out.println("Hello! I'm Bucket\nYou again?");
        System.out.println(LINE_BREAK);

        //load whatever was saved last time
        taskList items = Storage.load();
        System.out.println("Loading tasks ...");
        
        //time delay
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (items.isEmpty()) {
            System.out.println("\nNo tasks found.");
            System.out.println(LINE_BREAK);
        } else {
            System.out.println(items);
        }

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("bye")) {
            // Split once only, so a description keeps any spaces inside it.
            String[] parts = input.trim().split(" ", 2);
            String command = parts[0]; //todo, event, deadline, mark, unmark, list, delete
            String argument = parts.length > 1 ? parts[1] : ""; //the rest of the line, if any, is the argument

            if (command.equals("todo")) {
                //add todo
                if (argument.isEmpty()) {
                    System.out.println(LINE_BREAK);
                    System.out.println("OOPS!!! The description of a todo cannot be empty.");
                    System.out.println(LINE_BREAK);
                } else {
                    addTask(items, new todo(argument));
                }

            } else if (command.equals("deadline")) {
                //add deadline
                // "return book /by Sunday" -> ["return book", "Sunday"]
                String[] detail = argument.split(" /by ", 2); //split into description and deadline
                addTask(items, new deadline(detail[0], detail[1]));

            } else if (command.equals("event")) {
                //add event
                // "project meeting /from Mon 2pm /to 4pm" -> description, then start, then end
                String[] detail = argument.split(" /from ", 2); //spilts into description and the rest
                String[] fromTo = detail[1].split(" /to ", 2); //splits the rest into start and end
                addTask(items, new event(detail[0], fromTo[0], fromTo[1]));

            } else if (command.equals("mark") || command.equals("unmark")) {
                //mark or unmark a task
                boolean isDone = command.equals("mark");
                task t = items.get(Integer.parseInt(argument) - 1);
                t.setDone(isDone);

                System.out.println(LINE_BREAK);
                System.out.println(isDone
                        ? "Nice! I've marked this task as done:"
                        : "OK, I've marked this task as not done yet:");
                System.out.println("  " + t);
                System.out.println(LINE_BREAK);

            } else if (command.equals("list")) {
                //list all tasks
                System.out.println(items);

            } else if (command.equals("delete")) {
                //delete a task
                int index = Integer.parseInt(argument) - 1;
                task t = items.get(index);
                items.removeItem(index);
                System.out.println(LINE_BREAK);
                System.out.println("Noted. I've removed this task:");
                System.out.println("    " + t);
                System.out.println("Now you have " + items.size() + " tasks in the list.");
                System.out.println(LINE_BREAK);

            } else {
                //exception for unknown command
                System.out.println(LINE_BREAK);
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
                System.out.println(LINE_BREAK);
            }

            //the list might have changed, so write it out again
            Storage.save(items);

            input = scanner.nextLine();
        }

        scanner.close();
        System.out.println("\nBYEEEEEEE!");
        System.out.println(LINE_BREAK);
    }

    /** Adds a task and prints the confirmation the Level-4 sample output asks for. */
    private static void addTask(taskList items, task t) {
        items.addItem(t);
        System.out.println(LINE_BREAK);
        System.out.println("Got it. I've added this task:");
        System.out.println("    " + t);
        System.out.println("Now you have " + items.size() + " tasks in the list.");
        System.out.println(LINE_BREAK);
    }
}
