package org.example;

import org.example.model.Robot;
import org.example.service.FileReaderService;
import org.example.service.RobotService;
import org.example.service.implement.FileReaderServiceImpl;
import org.example.service.implement.RobotServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        Scanner scanner = new Scanner(System.in);
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        RobotService robotService = new RobotServiceImpl(robot, scanner, fileReaderService);
        robotService.start();
    }
}