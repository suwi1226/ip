package bucket;

import java.time.LocalDate;

//splits the line the user typed into the command word and its argument,
//and turns arguments into the objects the rest of the program works with.
//nothing in here prints anything - that's the Ui's job

public class Parser {
    private String command;
    private String argument;

    public Parser(String input) {
        //split once only, so a description keeps any spaces inside it
        String[] parts = input.trim().split(" ", 2);
        this.command = parts[0];
        this.argument = parts.length > 1 ? parts[1] : "";
    }

    public String getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }

    //"return book /by 2019-10-15" -> a deadline
    public static Deadline toDeadline(String argument) {
        String[] detail = argument.split(" /by ", 2); //split into description and date
        return new Deadline(detail[0], LocalDate.parse(detail[1]));
    }

    //"project meeting /from 2019-10-15 /to 2019-10-16" -> an event
    public static Event toEvent(String argument) {
        String[] detail = argument.split(" /from ", 2); //splits into description and the rest
        String[] fromTo = detail[1].split(" /to ", 2); //splits the rest into start and end
        return new Event(detail[0], LocalDate.parse(fromTo[0]), LocalDate.parse(fromTo[1]));
    }

    //"2" -> 1, because the list is shown 1-based but stored 0-based
    public static int toIndex(String argument) {
        return Integer.parseInt(argument) - 1;
    }
}
