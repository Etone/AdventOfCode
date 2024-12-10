package tech.corvin.aoc.day10;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Part1 implements Part<Integer> {

    @Override
    public Integer solve() throws IOException {
        var topographicMap = Helper.mapStringGridToIntGrid(Helper.getInputAsGrid("day10.txt"));
        return getScore(topographicMap);
    }

    private int getScore(Grid<Integer> map) {
        return map.findAll(0)
                .stream()
                .map((c) -> map.bfs(c, 9, (current, next) -> map.getCell(current) + 1 == map.getCell(next)).stream().distinct().collect(Collectors.toList()))
                .mapToInt(List::size)
                .sum();
    }
}