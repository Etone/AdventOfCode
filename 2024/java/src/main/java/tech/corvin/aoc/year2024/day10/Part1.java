package tech.corvin.aoc.year2024.day10;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.List;

public class Part1 implements Part<Integer> {

    @Override
    public Integer solve() throws IOException {
        var topographicMap = Helper.mapStringGridToIntGrid(Helper.getInputAsGrid("2024/day10.txt"));
        return getScore(topographicMap);
    }

    private int getScore(Grid<Integer> map) {
        return map.findAll(0)
                .stream()
                .map((c) -> map.bfs(
                                c,
                                9,
                                map::getOrthogonalCells,
                                (current, next) -> map.getCell(current) + 1 == map.getCell(next))
                        .stream()
                        .distinct()
                        .toList())
                .mapToInt(List::size)
                .sum();
    }
}