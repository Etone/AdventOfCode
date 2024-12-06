package tech.corvin.aoc.day6;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.IntPair;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Part1 implements Part<Integer> {

    Grid<String> grid;

    Guard guard;

    List<Coordinate> path = new ArrayList<>();

    @Override
    public Integer solve(String inputPath) throws IOException {
        var input = Helper.getResourceFileAsString(inputPath);

        grid = Grid.fromString(input);
        guard = new Guard(grid.findFirst("^").orElseThrow());
        path.add(guard.getPosition());

        calculatePath();

        return Math.toIntExact(path.stream().distinct().count());
    }

    private void calculatePath() {
        Coordinate guardLocation;
        while ((guardLocation = guard.takeStep(grid)) != null) {
            path.add(guardLocation);
        }
    }
}