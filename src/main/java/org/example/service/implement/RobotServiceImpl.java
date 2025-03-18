package org.example.service.implement;

import org.example.model.Commandable;
import org.example.service.FileReaderService;
import org.example.service.RobotService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class RobotServiceImpl implements RobotService {

    private final Commandable robot;
    private final Scanner scanner;
    private final FileReaderService fileReaderService;

    public RobotServiceImpl(Commandable robot, Scanner scanner, FileReaderService fileReaderService) {
        this.robot = robot;
        this.scanner = scanner;
        this.fileReaderService = fileReaderService;
    }

    @Override
    public void start() throws IOException {
        System.out.println("Please enter the input type would you like to do");
        System.out.println("Enter 1 for Command line input");
        System.out.println("Enter 2 for File input");
        String input = scanner.nextLine();
        switch (input) {
            case "1" -> inputWithCommandLind();
            case "2" -> inputWithFile();
            default -> throw new IllegalArgumentException("Invalid input type");
        }
    }

    private void inputWithCommandLind() {
        do {
            String input = scanner.nextLine();

            if (input.equals("DONE")) {
                break;
            }

            robot.doCommand(input);
        } while (true);
    }

    private void inputWithFile() throws IOException {
        System.out.println("Please enter .txt file name example: test.txt");
        String input = scanner.nextLine();

        try (BufferedReader reader = fileReaderService.getBufferedReader(input)) {
            String line = reader.readLine();

            while (line != null) {
                robot.doCommand(line);
                line = reader.readLine();
            }

        } catch (FileNotFoundException ex) {
            System.out.println("file not found");
            throw ex;
        } catch (IOException ex) {
            System.out.println("io error");
            throw ex;
        }
    }
}
