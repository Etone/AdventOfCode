package tech.corvin.aoc.year2024.day6;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.ArrayGrid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part1 implements Part<Integer> {

    ArrayGrid<String> arrayGrid;

    Guard guard;

    List<Coordinate> path = new ArrayList<>();

    @Override
    public Integer solve() throws IOException {
        arrayGrid = Helper.getInputAsGrid("2024/day6.txt");
        guard = new Guard(arrayGrid.findFirst("^").orElseThrow());
        path.add(guard.getPosition());

        calculatePath();

        return Math.toIntExact(path.stream().distinct().count());
    }

    private void calculatePath() {
        Coordinate guardLocation;
        while ((guardLocation = guard.takeStep(arrayGrid)) != null) {
            path.add(guardLocation);
        }
    }
}