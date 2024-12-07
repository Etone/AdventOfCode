package tech.corvin.aoc.day6;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Part2 implements Part<Integer> {

    Grid<String> grid;
    Guard guard;

    HashSet<Coordinate> path = new HashSet<>();


    @Override
    public Integer solve(String inputPath) throws IOException {
        var input = Helper.getResourceFileAsString(inputPath);
        grid = Grid.fromString(input);
        AtomicInteger loops = new AtomicInteger();

        guard = new Guard(grid.findFirst("^").orElseThrow());
        calculatePath();

        path.forEach((c) -> {
            guard = new Guard(grid.findFirst("^").orElseThrow());
            var beforeObstacle = grid.getCell(c);
            grid.setCell(c, "#");
            if (guardPathLoops()) loops.getAndIncrement();
            grid.setCell(c, beforeObstacle);
        });

        return loops.get();
    }

    private void calculatePath() {
        Coordinate guardLocation;
        while ((guardLocation = guard.takeStep(grid)) != null) {
            path.add(guardLocation);
        }
    }

    // Returns true if path loops
    private boolean guardPathLoops() {
        List<PathStep> path = new ArrayList<>();
        path.add(new PathStep(guard.getPosition(), guard.getDirection()));

        while (guard.takeStep(grid) != null) {

            // detect Loop
            if (path.contains(new PathStep(guard.getPosition(), guard.getDirection()))) {
                return true;
            }

            path.add(new PathStep(guard.getPosition(), guard.getDirection()));
        }
        return false;
    }
}