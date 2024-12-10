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
        getPaths(topographicMap);
        return 0;
    }

    private void getPaths(Grid<Integer> map) {

    }
}