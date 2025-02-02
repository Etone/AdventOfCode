package tech.corvin.aoc.general.grid;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static tech.corvin.aoc.general.grid.Coordinate.*;

public class ArrayGrid<T> implements Grid<T> {

    private final T[][] value;

    public ArrayGrid(T[][] value) {
        this.value = value;
    }

    @Override
    public T getCell(Coordinate c) {
        return getCell(c.row(), c.column());
    }

    @Override
    public T getCell(int row, int column) {
        return value[row][column];
    }

    @Override
    public Grid<T> modify(Coordinate c, T value) {
        var copy = copyArray();
        copy[c.row()][c.column()] = value;
        return new ArrayGrid<>(copy);
    }

    @Override
    public int length() {
        return value.length;
    }

    @Override
    public int width() {
        return value[0].length;
    }

    @Override
    public List<T> getCellValues(Coordinate center, List<Coordinate> offsets) {
        return offsets
                .stream()
                .map((offset) -> getCell(center.offset(offset)))
                .toList();
    }

    @Override
    public List<T> getDiagonalValues(Coordinate center) {
        var offsets = Stream.of(TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT)
                .filter(offset -> !center.offset(offset).isOOB(this))
                .toList();
        return getCellValues(center, offsets);
    }

    @Override
    public List<Coordinate> getOrthogonalCells(Coordinate center) {
        var offsets = List.of(TOP, RIGHT, BOTTOM, LEFT);
        return offsets.stream().map(center::offset).filter(c -> !c.isOOB(this)).toList();
    }

    @Override
    public List<Coordinate> getAllValidCoordinates() {
        var coordinates = new ArrayList<Coordinate>();

        for (int row = 0; row < length(); row++) {
            for (int col = 0; col < width(); col++) {
                coordinates.add(new Coordinate(row, col));
            }
        }

        return coordinates;
    }

    @Override
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

    @Override
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

    @Override
    public Map<T, List<Coordinate>> lookup(List<T> exclude) {
        var lookup = new Hashtable<T, List<Coordinate>>();
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

    @Override
    public <R> Grid<R> map(Function<T, R> mapper) {
        R[][] copy = (R[][]) new Object[length()][width()];
        for (int row = 0; row < length(); row++) {
            for (int col = 0; col < width(); col++) {
                copy[row][col] = mapper.apply(value[row][col]);
            }
        }
        return new ArrayGrid<>(copy);
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        for (int row = 0; row < length(); row++) {
            for (int col = 0; col < width(); col++) {
                consumer.accept(value[row][col]);
            }
        }
    }

    @Override
    public void forEachCoordinate(Consumer<Coordinate> consumer) {
        for (int row = 0; row < length(); row++) {
            for (int col = 0; col < width(); col++) {
                consumer.accept(new Coordinate(row, col));
            }
        }
    }

    @Override
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

    /**
     * Find regions with the same value as the one in the starting cell
     * Uses breadth-first flood-fill to do so
     *
     * @param start coordinate to start from
     * @param neighboringCellProvider Function which provides "reachable" cells (e.g. orthogonal neighbors)
     * @return Set of Coordinates that are in the region
     */
    public Region findRegion(Coordinate start, Function<Coordinate, List<Coordinate>> neighboringCellProvider) {
        var regionValue = getCell(start);
        var region = new HashSet<Coordinate>();
        var queue = new ArrayDeque<>(List.of(start));

        var seen = new HashSet<Coordinate>();

        while (!queue.isEmpty()) {
            var current = queue.removeFirst();
            if (seen.contains(current)) continue;

            region.add(current);

            var neighbors = findNeighbors(
                    current,
                    neighboringCellProvider,
                    Predicate.not(region::contains),
                    (c) -> getCell(c).equals(regionValue)
            );

            seen.add(current);
            queue.addAll(neighbors);
        }
        return new Region(region);
    }

    @Override
    public Set<Region> findAllRegions(Function<Coordinate, List<Coordinate>> neighboringCellProvider) {
        var regions = new HashSet<Region>();
        var seen = new HashSet<Coordinate>();

        forEachCoordinate((current -> {
            if (seen.contains(current)) return;

            var region = findRegion(current, neighboringCellProvider);
            seen.addAll(region.getValue());
            regions.add(region);
        }));

        return regions;
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

    @SuppressWarnings("unchecked")
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
