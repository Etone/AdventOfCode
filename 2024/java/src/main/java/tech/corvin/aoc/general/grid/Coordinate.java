package tech.corvin.aoc.general.grid;

public record Coordinate (
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

    public Coordinate offset(Coordinate by) {
        return new Coordinate(row + by.row, column + by.column);
    }

    public Coordinate offset(int row, int column) {
        return offset(new Coordinate(row, column));
    }

    public boolean isOOB(Grid g) {
        return row < 0 || column < 0 || row > g.length() - 1 || column > g.width() - 1;
    }
}
