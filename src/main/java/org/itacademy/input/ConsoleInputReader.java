package org.itacademy.input;

import java.util.Scanner;

public class ConsoleInputReader implements InputReader {
    public static ConsoleInputReader instance;
    private final Scanner scanner;

    private ConsoleInputReader() {
        this.scanner = new Scanner(System.in);
    }

    public static ConsoleInputReader getInstance(){
        if (instance == null){
            instance = new ConsoleInputReader();
        }
        return instance;
    }

    @Override
    public String readString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    @Override
    public boolean readYesNo(String message) {
        while (true) {
            System.out.print(message + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid option. Please enter 'y' or 'n'.");
            }
        }
    }

}
