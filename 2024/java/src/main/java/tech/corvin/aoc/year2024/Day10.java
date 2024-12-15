package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 extends Day2024<Integer, Integer> {

    private Grid<Integer> topographicMap;

    // List of List representing each peak that can be reached from each starting location,
    // peaks can be present multiple times when more then one path leads to them
    private List<List<Coordinate>> peaks;

    public static void main(String[] args) throws IOException {
        new Day10().print();
    }

    public Day10() throws IOException {
        super(10);
        parseInput();
    }

    @Override
    public Integer part1() {
        return peaks.stream().map(HashSet::new).mapToInt(HashSet::size).sum();
    }

    @Override
    public Integer part2() {
        return peaks.stream().mapToInt(List::size).sum();
    }


    private void parseInput() throws IOException {
        topographicMap = inputAsGrid().map(Integer::parseInt);

        peaks = topographicMap.findAll(0)
                .stream()
                .map((c) -> topographicMap.bfs(
                                c,
                                9,
                                topographicMap::getOrthogonalCells,
                                (current, next) -> topographicMap.getCell(current) + 1 == topographicMap.getCell(next))
                ).toList();
    }
}
