import java.util.Scanner;

public class Bucket {
    public static void main(String[] args) {
        String banner = " ____   _   _   ____  _  __ _____  _____ \n"
                + "| __ ) | | | | / ___|| |/ /| ____||_   _|\n"
                + "|  _ \\ | | | || |    | ' / |  _|    | |  \n"
                + "| |_) || |_| || |___ | . \\ | |___   | |  \n"
                + "|____/  \\___/  \\____||_|\\_\\|_____|  |_|  \n";
        String lineBreak = "-------------------------";
        String[] items = new String[100];
        int index = 0;

        System.out.println(lineBreak);
        System.out.println(banner);
        System.out.println("Hello! I'm Bucket\nYou again?");
        System.out.println(lineBreak);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("bye")) {
            items[index] = input;
            index++;

            System.out.println(lineBreak);
            for (int i = 0; i < index; i++) {
                System.out.println((i + 1) + ". " + items[i]);
            }
            System.out.println(lineBreak);
            input = scanner.nextLine();
        }
        
        scanner.close();
        System.out.println("BYE!");
        System.out.println(lineBreak);
    }
}
