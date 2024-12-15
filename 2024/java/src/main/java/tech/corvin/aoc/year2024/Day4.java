package tech.corvin.aoc.year2024;


import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static tech.corvin.aoc.general.grid.Coordinate.*;


public class Day4 extends Day2024<Integer, Integer> {

    private Grid<String> grid;

    public static void main(String[] args) throws IOException {
        new Day4().print();
    }

    public Day4() throws IOException {
        super(4);
        parseInput();
    }


    @Override
    public Integer part1() {
        AtomicInteger xmas = new AtomicInteger();
        grid.forEachCoordinate(c -> xmas.addAndGet(checkForXMAS(c)));
        return xmas.get();
    }

    @Override
    public Integer part2() {
        AtomicInteger crossMAS = new AtomicInteger();
        grid.forEachCoordinate(c -> crossMAS.addAndGet(checkForCrossMAS(c)));
        return crossMAS.get();
    }


    private void parseInput() throws IOException {
        grid = inputAsGrid();
    }

    private int checkForXMAS(Coordinate c) {
        var value = grid.getCell(c);
        if (!value.equals("X")) return 0;

        var offsets = List.of(TOP_LEFT, TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT);

        return offsets.stream().mapToInt((direction) -> {
            var one = c.offset(direction);
            var two = c.offset(direction, 2);
            var three = c.offset(direction, 3);

            if (one.isOOB(grid) || two.isOOB(grid) || three.isOOB(grid)) return 0;

            if (grid.getCell(one).equals("M")
                    && grid.getCell(two).equals("A")
                    && grid.getCell(three).equals("S")) return 1;

            return 0;
        }).sum();
    }

    private int checkForCrossMAS(Coordinate c) {
        if (!grid.getCell(c).equals("A")) return 0;
        var corners = grid.getDiagonalValues(c);
        if (Collections.frequency(corners, "M") == 2
                && Collections.frequency(corners, "S") == 2
                && grid.getCellValues(c, List.of(TOP_LEFT, BOTTOM_RIGHT)).stream().distinct().count() != 1) return 1;

        return 0;
    }
}
