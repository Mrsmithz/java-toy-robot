package org.example.model;

import org.example.constant.Command;
import org.example.constant.Direction;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Robot implements Commandable {

    private static final Integer MAX_X = 5;
    private static final Integer MAX_Y = 5;
    private static final Integer MIN_X = 0;
    private static final Integer MIN_Y = 0;
    private static final Integer LEFT_TURN_DEGREES = -90;
    private static final Integer RIGHT_TURN_DEGREES = 90;
    private static final Integer STEP = 1;
    private static final String OUT_OF_TABLE_WARNING_MSG = "I'll be out of the table";
    private Integer x;
    private Integer y;
    private Direction direction;

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public Direction getDirection() {
        return this.direction;
    }

    private void move() {
        if (!isPlaced()) {
            System.out.println("Robot is not placed.");
            return;
        }

        switch (direction) {
            case NORTH, SOUTH -> moveY(direction);
            case EAST, WEST -> moveX(direction);
            default -> handleError(direction);
        }
    }

    private void moveX(Direction direction) {
        switch (direction) {
            case EAST -> {
                if (this.x >= MIN_X && this.x < MAX_X) {
                    this.x += STEP;
                } else {
                    System.out.println(OUT_OF_TABLE_WARNING_MSG);
                }
            }
            case WEST -> {
                if (this.x > MIN_X && this.x <= MAX_X) {
                    this.x -= STEP;
                } else {
                    System.out.println(OUT_OF_TABLE_WARNING_MSG);
                }
            }
            default -> handleError(direction);
        }
    }

    private void moveY(Direction direction) {
        switch (direction) {
            case NORTH -> {
                if (this.y >= MIN_Y && this.y < MAX_Y) {
                    this.y += STEP;
                } else {
                    System.out.println(OUT_OF_TABLE_WARNING_MSG);
                }
            }
            case SOUTH -> {
                if (this.y > MIN_Y && this.y <= MAX_Y) {
                    this.y -= STEP;
                } else {
                    System.out.println(OUT_OF_TABLE_WARNING_MSG);
                }
            }
            default -> handleError(direction);
        }
    }

    private void handleError(Direction direction) {
        throw new IllegalStateException("Unexpected value: " + direction);
    }

    private void turn(int degrees) {
        if (!isPlaced()) {
            System.out.println("Robot is not placed.");
            return;
        }

        this.direction = Direction.calculateNextDirection(this.direction, degrees);
    }

    @Override
    public void doCommand(String input) {
        try {
            if (isValidRobotPlace(input)) {
                this.place(input);
            }

            Command command = Command.parseCommand(input);

            switch (command) {
                case MOVE -> this.move();
                case REPORT -> this.report();
                case LEFT -> this.turn(LEFT_TURN_DEGREES);
                case RIGHT -> this.turn(RIGHT_TURN_DEGREES);
                default -> System.out.println("Invalid command");
            }

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void place(String input) {
        String[] commands = getCommands(input);

        this.x = Integer.parseInt(commands[0]);
        this.y = Integer.parseInt(commands[1]);
        this.direction = Direction.valueOf(commands[2]);
        System.out.println("Robot placed");
    }

    private void report() {
        if (isPlaced() && !isOutOfTheTable()) {
            System.out.printf("%d,%d,%s%n", this.x, this.y, this.direction);
        }
    }

    private boolean isOutOfTheTable() {
        return this.x < MIN_X || this.x > MAX_X || this.y < MIN_Y || this.y > MAX_Y;
    }

    private boolean isPlaced() {
        return this.x != null && this.y != null && this.direction != null;
    }

    private boolean isValidRobotPlace(String input) {
        Pattern pattern = Pattern.compile("^PLACE\\s(-\\d+|\\d+),(-\\d+|\\d+),(NORTH|SOUTH|EAST|WEST)$");
        return pattern.matcher(input).matches();
    }

    private String[] getCommands(String input) {
        return Arrays.stream(input.split(" "))
                .filter(entry -> !entry.equals(Command.PLACE.name()))
                .findFirst()
                .map(entry -> entry.split(","))
                .orElseThrow(() -> new IllegalArgumentException("Invalid command"));
    }
}
