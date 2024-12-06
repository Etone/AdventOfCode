package tech.corvin.aoc.general.grid;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static tech.corvin.aoc.general.grid.Coordinate.*;

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

    public void setCell(Coordinate cell, T value) {
        this.value[cell.row()][cell.column()] = value;
    }

    public int length() {
        return value.length;
    }

    public int width() {
        return value[0].length;
    }

    public Coordinate topLeft() {
        return new Coordinate(0, 0);
    }

    public Coordinate topRight() {
        return new Coordinate(0, width());
    }

    public Coordinate bottomLeft() {
        return new Coordinate(length(), 0);
    }

    public Coordinate bottomRight() {
        return new Coordinate(length(), width());
    }

    public List<T> getAdjacent(Coordinate center, List<Coordinate> offsets) {
        return offsets
                .stream()
                .map((offset) -> getCell(center.offset(offset)))
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

    public Optional<Coordinate> findFirst(T search) {
        for (int row = 0; row < length(); row++) {
            for (int col = 0; col < width(); col++) {
                if (getCell(row, col).equals(search)) {
                    return Optional.of(new Coordinate(row, col));
                }
            }
        }
        return Optional.empty();
    }


    public boolean isOOB(Coordinate c) {
        return c.row() < 0 || c.column() < 0 || c.row() > length() - 1 || c.column() > width() - 1;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        Arrays.stream(value).forEach((row) -> {
            Arrays.stream(row).forEach((col -> string.append(col.toString())));
            string.append(System.lineSeparator());
        });

        return string.toString();
    }
}
