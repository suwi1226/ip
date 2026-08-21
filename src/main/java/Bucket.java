import java.util.Scanner;

public class Bucket {
    public static void main(String[] args) {
        String banner = " ____   _   _   ____  _  __ _____  _____ \n"
                + "| __ ) | | | | / ___|| |/ /| ____||_   _|\n"
                + "|  _ \\ | | | || |    | ' / |  _|    | |  \n"
                + "| |_) || |_| || |___ | . \\ | |___   | |  \n"
                + "|____/  \\___/  \\____||_|\\_\\|_____|  |_|  \n";
        String lineBreak = "-------------------------";
        taskList items = new taskList();

        System.out.println(lineBreak);
        System.out.println(banner);
        System.out.println("Hello! I'm Bucket\nYou again?");
        System.out.println(lineBreak);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("bye")) {
            if (input.startsWith("mark")) {
                // Handle mark command
                String[] parts = input.split(" ", 2);
                String command = parts[0]; // "mark"
                int taskNumber = Integer.parseInt(parts[1]) - 1;

                items.get(taskNumber).setDone(true);

            } 
            
            else {
                //standard add
                task item = new task(input);
                items.addItem(item);
            }
            
            System.out.println(items.toString());            
            input = scanner.nextLine();
        }
        
        scanner.close();
        System.out.println("BYE!");
        System.out.println(lineBreak);
    }
}
