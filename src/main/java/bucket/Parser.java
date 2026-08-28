package bucket;

import java.time.LocalDate;

/**
 * Splits the line the user typed into the command word and its argument,
 * and turns arguments into the objects the rest of the program works with.
 */
public class Parser {
    private String command;
    private String argument;

    /**
     * Splits one line of user input into its command word and the rest.
     *
     * @param input the raw line the user typed
     */
    public Parser(String input) {
        //split once only, so a description keeps any spaces inside it
        String[] parts = input.trim().split(" ", 2);
        this.command = parts[0];
        this.argument = parts.length > 1 ? parts[1] : "";
    }

    /**
     * Returns the first word of the input.
     *
     * @return the command word, e.g. "deadline"
     */
    public String getCommand() {
        return command;
    }

    /**
     * Returns everything after the first word.
     *
     * @return the argument, or an empty string if there was none
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Builds a deadline from an argument like "return book /by 2019-10-15".
     *
     * @param argument the part of the line after the command word
     * @return the deadline described by the argument
     * @throws java.time.format.DateTimeParseException if the date is not yyyy-mm-dd
     */
    public static deadline toDeadline(String argument) {
        String[] detail = argument.split(" /by ", 2); //split into description and date
        return new deadline(detail[0], LocalDate.parse(detail[1]));
    }

    /**
     * Builds an event from an argument like
     * "project meeting /from 2019-10-15 /to 2019-10-16".
     *
     * @param argument the part of the line after the command word
     * @return the event described by the argument
     * @throws java.time.format.DateTimeParseException if either date is not yyyy-mm-dd
     */
    public static event toEvent(String argument) {
        String[] detail = argument.split(" /from ", 2); //splits into description and the rest
        String[] fromTo = detail[1].split(" /to ", 2); //splits the rest into start and end
        return new event(detail[0], LocalDate.parse(fromTo[0]), LocalDate.parse(fromTo[1]));
    }

    /**
     * Turns the task number the user typed into a list index.
     *
     * @param argument the number as text, e.g. "2"
     * @return the zero-based index, one lower than what the user typed
     * @throws NumberFormatException if the argument is not a whole number
     */
    public static int toIndex(String argument) {
        return Integer.parseInt(argument) - 1;
    }
}
