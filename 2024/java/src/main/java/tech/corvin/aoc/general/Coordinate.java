package tech.corvin.aoc.general;

public record Coordinate(
        int row,
        int column
) {
    public static final Coordinate TOP = new Coordinate(-1, 0);
    public static final Coordinate RIGHT = new Coordinate(0, +1);
    public static final Coordinate BOTTOM = new Coordinate(+1, 0);
    public static final Coordinate LEFT = new Coordinate(0, -1);

    public static final Coordinate TOP_LEFT = new Coordinate(-1, -1);
    public static final Coordinate TOP_RIGHT = new Coordinate(-1, +1);
    public static final Coordinate BOTTOM_RIGHT = new Coordinate(+1, +1);
    public static final Coordinate BOTTOM_LEFT = new Coordinate(+1, -1);}
