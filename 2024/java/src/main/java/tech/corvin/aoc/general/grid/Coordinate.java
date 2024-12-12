package tech.corvin.aoc.general.grid;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record Coordinate(
        int row,
        int column
) {
    public static final Coordinate TOP = new Coordinate(-1, 0);
    public static final Coordinate RIGHT = new Coordinate(0, +1);
    public static final Coordinate BOTTOM = new Coordinate(+1, 0);
    public static final Coordinate LEFT = new Coordinate(0, -1);

    public static final Coordinate TOP_LEFT = TOP.offset(LEFT);
    public static final Coordinate TOP_RIGHT = TOP.offset(RIGHT);
    public static final Coordinate BOTTOM_RIGHT = BOTTOM.offset(RIGHT);
    public static final Coordinate BOTTOM_LEFT = BOTTOM.offset(LEFT);

    public Coordinate offset(int row, int column) {
        return offset(new Coordinate(row, column));
    }

    public Coordinate offset(Coordinate by) {
        return offset(by, 1);
    }

    public Coordinate offset(Coordinate by, int factor) {
        return new Coordinate(row + factor * by.row, column + factor * by.column);
    }

    public Coordinate distance(Coordinate other) {
        return new Coordinate(other.row - this.row, other.column - this.column);
    }

    public Coordinate multiply(int factor) {
        return new Coordinate(row * factor, column * factor);
    }

    public Coordinate flip() {
        return multiply(-1);
    }

    public boolean isOOB(Grid g) {
        return row < 0 || column < 0 || row > g.length() - 1 || column > g.width() - 1;
    }

    public Set<Coordinate> getOrthogonal() {
        var offsets = List.of(TOP, RIGHT, BOTTOM, LEFT);
        return offsets.stream().map(this::offset).collect(Collectors.toUnmodifiableSet());
    }
}
