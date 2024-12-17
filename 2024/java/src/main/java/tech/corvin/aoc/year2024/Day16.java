package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.Day;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;

public class Day16 extends Day2024<Integer, Integer> {

    private Grid<String> maze;

    public static void main(String[] args) throws IOException {
        new Day16().initialize().print();
    }

    public Day16() {
        super(16);
    }

    @Override
    public Integer part1() {
        return 0;
    }

    @Override
    public Integer part2() {
       return 0;
    }

    @Override
    public Day<?, ?> initialize() throws IOException {
        maze = inputAsGrid();
        var start = maze.findFirst("S").orElseThrow();
        var a = maze.bfs(start, "E", maze::getOrthogonalCells, (current, nextStep) -> !maze.getCell(nextStep).equals("#"));
        return this;
    }
}
