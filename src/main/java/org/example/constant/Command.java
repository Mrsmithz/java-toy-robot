package org.example.constant;

import java.util.Arrays;

public enum Command {
    PLACE,
    MOVE,
    REPORT,
    LEFT,
    RIGHT;

    public static Command parseCommand(String input) throws IllegalArgumentException {
        return Arrays.stream(Command.values())
                .filter(entry -> entry.name().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Command"));
    }
}
