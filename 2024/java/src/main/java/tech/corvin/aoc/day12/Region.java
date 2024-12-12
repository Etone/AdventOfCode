package tech.corvin.aoc.day12;

import tech.corvin.aoc.general.grid.Coordinate;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Region {

    private final Set<Coordinate> value;
    private final int perimeter;
    private final int sides;

    public Region(Set<Coordinate> region) {
        value = region;
        perimeter = findPerimeter();
        sides = 0;
    }

    public int size() {
        return value.size();
    }

    public int perimeter() {
        return perimeter;
    }

    public int sides() {
        return sides;
    }

    public int fencingCosts() {
        return size() * perimeter();
    }

    public int bulkOrderFencingCosts() {
        return size() * sides();
    }

    public Set<Coordinate> getValue() {
        return value;
    }

    private int findPerimeter() {
        var cells = value
                .stream()
                .map(Coordinate::getOrthogonal)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        cells.removeAll(value);
        return cells.size();
    }
}
