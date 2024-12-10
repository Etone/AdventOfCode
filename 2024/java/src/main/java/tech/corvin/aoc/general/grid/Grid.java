package tech.corvin.aoc.general.grid;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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

    public List<T> getCellValues(Coordinate center, List<Coordinate> offsets) {
        return offsets
                .stream()
                .map((offset) -> getCell(center.offset(offset)))
                .toList();
    }

    public List<T> getDiagonalValues(Coordinate center) {
        var offsets = Stream.of(TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT)
                .filter(offset -> !center.offset(offset).isOOB(this))
                .toList();
        return getCellValues(center, offsets);
    }

    public List<Coordinate> getOrthogonalCells(Coordinate center) {
        var offsets = List.of(TOP, RIGHT, BOTTOM, LEFT);
        return offsets.stream().map(center::offset).filter(c -> !c.isOOB(this)).toList();
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

    public Map<T, List<Coordinate>> lookup(List<T> exclude) {
        var lookup = new HashMap<T, List<Coordinate>>();
        for (int row = 0; row < length(); row++) {
            for (int col = 0; col < width(); col++) {
                var val = getCell(row, col);
                if (exclude.contains(val)) continue;
                if (!lookup.containsKey(val)) lookup.put(val, new ArrayList<>());
                lookup.get(val).add(new Coordinate(row, col));
            }
        }
        return lookup;
    }

    public <R> Grid<R> map(Function<T, R> mapper) {
        R[][] copy = (R[][]) new Object[length()][width()];
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

    public List<Coordinate> bfs(
            Coordinate start,
            T goal,
            Function<Coordinate, List<Coordinate>> neighboringCellProvider,
            BiPredicate<Coordinate, Coordinate> addedFilter
    ) {
        var goals = new ArrayList<Coordinate>();

        var queue = new LinkedList<>(List.of(start));
        var seen = new HashSet<>(List.of(start));

        while (!queue.isEmpty()) {
            var current = queue.removeFirst();

            var neighbors = findNeighbors(
                    current,
                    neighboringCellProvider,
                    Predicate.not(seen::contains),
                    (c) -> addedFilter.test(current, c)
            );

            seen.add(current);

            goals.addAll(neighbors.stream().filter((c) -> getCell(c).equals(goal)).toList());
            queue.addAll(neighbors.stream().filter((c) -> !getCell(c).equals(goal)).toList());
        }
        return goals;
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
        T[][] copy = (T[][]) new Object[length()][width()]; //Might not be the best way to clone generic 2D array
        for (int row = 0; row < length(); row++) {
            copy[row] = Arrays.copyOf(value[row], width());
        }
        return copy;
    }

    @SafeVarargs
    private List<Coordinate> findNeighbors(Coordinate from,
                                           Function<Coordinate, List<Coordinate>> cellProvider,
                                           Predicate<Coordinate>... filters
    ) {
        var compositePredicate = Arrays.stream(filters).reduce(identity -> true, Predicate::and);
        return cellProvider.apply(from).stream().filter(compositePredicate).toList();
    }
}
