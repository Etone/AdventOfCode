package tech.corvin.aoc.day12;

import tech.corvin.aoc.general.grid.Coordinate;

import java.util.Set;

import static tech.corvin.aoc.general.grid.Coordinate.*;

public class Region {

    private final Set<Coordinate> value;
    private final int perimeter;
    private final int sides;


    public Region(Set<Coordinate> region) {
        value = region;
        perimeter = findPerimeter();
        sides = findSides();
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
        return value
                .stream()
                .map((cell) -> {
                    var orthogonal = cell.getOrthogonal()
                            .stream()
                            .filter(value::contains)
                            .toList()
                            .size();

                    return 4 - orthogonal;
                })
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int findSides() {
        // neat trick, number of corners = number of sides
        return value
                .stream()
                .mapToInt(this::findCorners)
                .sum();
    }

    private int findCorners(Coordinate coordinate) {
        var top = value.contains(coordinate.offset(TOP));
        var left = value.contains(coordinate.offset(LEFT));
        var right = value.contains(coordinate.offset(RIGHT));
        var bot = value.contains(coordinate.offset(BOTTOM));

        var top_left = value.contains(coordinate.offset(TOP_LEFT));
        var top_right = value.contains(coordinate.offset(TOP_RIGHT));
        var bot_left = value.contains(coordinate.offset(BOTTOM_LEFT));
        var bot_right = value.contains(coordinate.offset(BOTTOM_RIGHT));

        var corners = 0;

        if (left && bot && !bot_left) corners++;
        if (!left && !bot && !bot_left) corners++;
        if (!left && !bot && bot_left) corners++;

        if (right && bot && !bot_right) corners++;
        if (!right && !bot && !bot_right) corners++;
        if (!right && !bot && bot_right) corners++;


        if (left && top && !top_left) corners++;
        if (!left && !top && !top_left) corners++;
        if (!left && !top && top_left) corners++;


        if (right && top && !top_right) corners++;
        if (!right && !top && !top_right) corners++;
        if (!right && !top && top_right) corners++;

        return corners;
    }
}
