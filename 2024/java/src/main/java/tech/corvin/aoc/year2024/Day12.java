package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.Day;
import tech.corvin.aoc.general.grid.Grid;
import tech.corvin.aoc.general.grid.Region;

import java.io.IOException;
import java.util.Set;

public class Day12 extends Day2024<Integer, Integer> {

    private Set<Region> regions;

    public static void main(String[] args) throws IOException {
        new Day12().initialize().print();
    }

    public Day12() throws IOException {
        super(12);
    }


    @Override
    public Integer part1() {
        return regions.stream().mapToInt(Region::fencingCosts).sum();
    }

    @Override
    public Integer part2() {
       return regions.stream().mapToInt(Region::bulkOrderFencingCosts).sum();
    }

    @Override
    public Day<?, ?> initialize() throws IOException {
        Grid<String> garden = inputAsGrid();
        regions = garden.findAllRegions(garden::getOrthogonalCells);
        return this;
    }
}
