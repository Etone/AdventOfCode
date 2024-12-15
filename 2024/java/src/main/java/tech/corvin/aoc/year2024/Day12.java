package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;
import tech.corvin.aoc.general.grid.Region;

import java.io.IOException;
import java.util.Set;

import static tech.corvin.aoc.general.grid.Coordinate.*;
import static tech.corvin.aoc.general.grid.Coordinate.BOTTOM_RIGHT;

public class Day12 extends Day2024<Integer, Integer> {

    private Set<Region> regions;

    public static void main(String[] args) throws IOException {
        new Day12().print();
    }

    public Day12() throws IOException {
        super(12);
        parseInput();
    }


    @Override
    public Integer part1() {
        return regions.stream().mapToInt(Region::fencingCosts).sum();
    }

    @Override
    public Integer part2() {
       return regions.stream().mapToInt(Region::bulkOrderFencingCosts).sum();
    }


    private void parseInput() throws IOException {
        Grid<String> garden = inputAsGrid();
        regions = garden.findAllRegions(garden::getOrthogonalCells);
    }
}
