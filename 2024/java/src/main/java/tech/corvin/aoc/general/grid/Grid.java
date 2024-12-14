package tech.corvin.aoc.general.grid;

import tech.corvin.aoc.year2024.day12.Region;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Grid<T> {
    T getCell(Coordinate c);

    T getCell(int row, int column);

    Grid<T> modify(Coordinate c, T value);

    int length();

    int width();

    List<T> getCellValues(Coordinate center, List<Coordinate> offsets);

    List<T> getDiagonalValues(Coordinate center);

    List<Coordinate> getOrthogonalCells(Coordinate center);

    List<Coordinate> getAllValidCoordinates();

    Optional<Coordinate> findFirst(T search);

    List<Coordinate> findAll(T search);

    Map<T, List<Coordinate>> lookup(List<T> exclude);

    <R> Grid<R> map(Function<T, R> mapper);

    void forEach(Consumer<T> consumer);

    void forEachCoordinate(Consumer<Coordinate> consumer);

    /**
     * Returns a list with all cells that are reachable from the start coordinate which
     * - fulfill the filter provided by addedFilter
     * - Contain the target Value given by goal
     * This can contain duplicate entries, if a Cell is reachable by more than one path.
     * The Path is not returned. Uses Breath-first search
     *
     * @param start                   Starting coordinate
     * @param goal                    Value of cell that is considered a goal
     * @param neighboringCellProvider Function which provides "reachable" cells (e.g. orthogonal neighbors)
     * @param addedFilter             Filters that cells need to fulfill to be considered
     * @return The list of coordinates of the cells that can be reached and hold a value provided in goal
     */
    List<Coordinate> bfs(
            Coordinate start,
            T goal,
            Function<Coordinate, List<Coordinate>> neighboringCellProvider,
            BiPredicate<Coordinate, Coordinate> addedFilter
    );

    Set<Region> findAllRegions(Function<Coordinate, List<Coordinate>> neighboringCellProvider);
}
