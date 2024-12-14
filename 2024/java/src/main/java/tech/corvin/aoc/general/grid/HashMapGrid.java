package tech.corvin.aoc.general.grid;

import tech.corvin.aoc.year2024.day12.Region;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static tech.corvin.aoc.general.grid.Coordinate.*;
import static tech.corvin.aoc.general.grid.Coordinate.BOTTOM_LEFT;

public class HashMapGrid<T> implements Grid<T> {

    private final HashMap<Coordinate, T> value;

    public HashMapGrid(Map<Coordinate, T> value) {
        this.value = new HashMap<>(value);
    }

    @Override
    public T getCell(Coordinate c) {
        return value.get(c);
    }

    @Override
    public T getCell(int row, int column) {
        return value.get(new Coordinate(row, column));
    }

    @Override
    public Grid<T> modify(Coordinate c, T value) {
        return null;
    }

    @Override
    public int length() {
        return value.keySet().stream().map(Coordinate::row).max(Comparator.comparingInt(c -> c)).orElseThrow();
    }

    @Override
    public int width() {
        return value.keySet().stream().map(Coordinate::column).max(Comparator.comparingInt(c -> c)).orElseThrow();
    }

    @Override
    public List<T> getCellValues(Coordinate center, List<Coordinate> offsets) {
        return offsets.stream().map(center::offset).map(this::getCell).toList();
    }

    @Override
    public List<T> getDiagonalValues(Coordinate center) {
        var offsets = List.of(TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT);
        return getCellValues(center, offsets);
    }

    @Override
    public List<Coordinate> getOrthogonalCells(Coordinate center) {
        return Stream.of(TOP, RIGHT, BOTTOM, LEFT)
                .map(center::offset)
                .filter(c -> !c.isOOB(this))
                .toList();
    }

    @Override
    public List<Coordinate> getAllValidCoordinates() {
        return value.keySet().stream().toList();
    }

    @Override
    public Optional<Coordinate> findFirst(T search) {
        return findAll(search).stream().findFirst();
    }

    @Override
    public List<Coordinate> findAll(T search) {
        return value.entrySet()
                .stream()
                .filter((entry) -> entry.getValue().equals(search))
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public Map<T, List<Coordinate>> lookup(List<T> exclude) {
        // TODO
        return Map.of();
    }

    @Override
    public <R> Grid<R> map(Function<T, R> mapper) {
        var mapped = value.entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), mapper.apply(entry.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new HashMapGrid<>(mapped);
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        value.values().forEach(consumer);
    }

    @Override
    public void forEachCoordinate(Consumer<Coordinate> consumer) {
        value.keySet().forEach(consumer);
    }

    @Override
    public List<Coordinate> bfs(Coordinate start, T goal, Function<Coordinate, List<Coordinate>> neighboringCellProvider, BiPredicate<Coordinate, Coordinate> addedFilter) {
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

    private Region findRegion(Coordinate start, Function<Coordinate, List<Coordinate>> neighboringCellProvider) {
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

    @SafeVarargs
    private List<Coordinate> findNeighbors(Coordinate from,
                                           Function<Coordinate, List<Coordinate>> cellProvider,
                                           Predicate<Coordinate>... filters
    ) {
        var compositePredicate = Arrays.stream(filters).reduce(_ -> true, Predicate::and);
        return cellProvider.apply(from).stream().filter(compositePredicate).toList();
    }
}
