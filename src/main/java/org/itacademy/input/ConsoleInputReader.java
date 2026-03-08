package org.itacademy.input;

import java.util.Scanner;

public class ConsoleInputReader implements InputReader {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    @Override
    public long readLong(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid long number.");
            }
        }
    }
}