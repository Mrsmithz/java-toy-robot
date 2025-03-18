package org.example.constant;

import java.util.Arrays;

public enum Direction {
    NORTH(360),
    EAST(90),
    SOUTH(180),
    WEST(270);

    private final int degrees;

    Direction(int degrees) {
        this.degrees = degrees;
    }

    public static Direction calculateNextDirection(Direction direction, int degrees) {
        int newDegrees = (direction.getDegrees() + degrees);
        if (newDegrees == 0) {
            return NORTH;
        }

        return Arrays.stream(Direction.values())
                .filter(entry -> {
                    if (newDegrees > 360) {
                        return entry.getDegrees() == (newDegrees % 360);
                    } else {
                        return entry.getDegrees() == newDegrees;
                    }
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid direction"));
    }

    public int getDegrees() {
        return this.degrees;
    }
}
