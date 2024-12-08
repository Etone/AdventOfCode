package tech.corvin.aoc.general.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static tech.corvin.aoc.general.grid.Coordinate.*;

public class Grid<T> {

    private final T[][] value;

    public Grid(T[][] value) {
        this.value = value;
    }

    public T getCell(Coordinate c) {
        return getCell(c.row(), c.column());
    }

    public T getCell(int row, int column) {
        return value[row][column];
    }

    public Grid<T> modify(Coordinate c, T value) {
        var copy = copyArray();
        copy[c.row()][c.column()] = value;
        return new Grid<>(copy);
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

    public List<T> getCellValues(Coordinate center, List<Coordinate> offsets) {
        return offsets
                .stream()
                .map((offset) -> getCell(center.offset(offset)))
                .toList();
    }

    public List<T> getOrthogonal(Coordinate center) {
        var offsets = List.of(TOP, RIGHT, BOTTOM, LEFT);
        return getCellValues(center, offsets);
    }

    public List<T> getDiagonal(Coordinate center) {
        var offsets = List.of(TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT);
        return getCellValues(center, offsets);
    }

    public List<T> getAdjacent(Coordinate center) {
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

    public List<Coordinate> findAll(T search) {
        var result = new ArrayList<Coordinate>();
        for (int row = 0; row < length(); row++) {
            for (int col = 0; col < width(); col++) {
                if (getCell(row, col).equals(search)) {
                    result.add(new Coordinate(row, col));
                }
            }
        }
        return result;
    }

    public <R> Grid<R> map(Function<T, R> mapper) {
        R[][] copy = (R[][])new Object[length()][width()];
        for (int row = 0; row < length(); row++) {
            for (int col = 0; col < width(); col++) {
                copy[row][col] = mapper.apply(value[row][col]);
            }
        }
        return new Grid<>(copy);
    }

    public void forEach(Consumer<T> consumer) {
        for (int row = 0; row < length(); row++) {
            for (int col = 0; col < width(); col++) {
                consumer.accept(value[row][col]);
            }
        }
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

    private T[][] copyArray() {
        T[][] copy = (T[][])new Object[length()][width()]; //Might not be the best way to clone generic 2D array
        for (int row = 0; row < length(); row++) {
            copy[row] = Arrays.copyOf(value[row], width());
        }
        return copy;
    }
}
