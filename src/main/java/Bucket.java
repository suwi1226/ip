import java.util.Scanner;

public class Bucket {
    public static void main(String[] args) {
        String banner = " ____   _   _   ____  _  __ _____  _____ \n"
                + "| __ ) | | | | / ___|| |/ /| ____||_   _|\n"
                + "|  _ \\ | | | || |    | ' / |  _|    | |  \n"
                + "| |_) || |_| || |___ | . \\ | |___   | |  \n"
                + "|____/  \\___/  \\____||_|\\_\\|_____|  |_|  \n";
        String lineBreak = "-------------------------";
        System.out.println(lineBreak);
        System.out.println(banner);
        System.out.println("Hello! I'm Bucket\nYou again?");
        System.out.println(lineBreak);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("bye")) {
            System.out.println(lineBreak);
            System.out.println(input);
            System.out.println(lineBreak);
            input = scanner.nextLine();
        }
        
        System.out.println("BYE!");
        System.out.println(lineBreak);
    }
}
