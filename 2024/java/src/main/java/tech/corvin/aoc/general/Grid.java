package tech.corvin.aoc.general;

import java.util.List;
import java.util.stream.Stream;

import static tech.corvin.aoc.general.Coordinate.*;

public record Grid<T>(T[][] value) {

    public static Grid<String> fromString(String gridAsText) {
        var rows = gridAsText.split(System.lineSeparator());


        var result = new String[rows.length][rows[0].length()];

        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].split("");
        }
        return new Grid<>(result);
    }

    public T getCell(Coordinate c) {
        return getCell(c.row(), c.column());
    }

    public T getCell(int row, int column) {
        return value[row][column];
    }

    public int length() {
        return value.length;
    }

    public int width() {
        return value[0].length;
    }

    public List<T> getAdjacent(Coordinate center, List<Coordinate> offsets) {
        return offsets
                .stream()
                .map((offset) -> getCell(center.row() + offset.row(), center.column() + offset.column()))
                .toList();
    }

    public List<T> getOrthogonal(Coordinate center) {
        var offsets = List.of(TOP, RIGHT, BOTTOM, LEFT);
        return getAdjacent(center, offsets);
    }

    public List<T> getDiagonal(Coordinate center) {
        var offsets = List.of(TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT);
        return getAdjacent(center, offsets);
    }

    public List<T> getAllAdjacent(Coordinate center) {
        return Stream.concat(getDiagonal(center).stream(), getOrthogonal(center).stream()).toList();
    }
}
