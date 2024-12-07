package tech.corvin.aoc.day6;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Part2 implements Part<Integer> {

    @Override
    public Integer solve() throws IOException {
        var grid = Helper.getInputAsGrid("day6.txt");
        AtomicInteger loops = new AtomicInteger();

        var guardStart = grid.findFirst("^").orElseThrow();
        var originalGuard = new Guard(guardStart);
        var path = calculatePath(originalGuard, grid);

        path.parallelStream().forEach((c) -> {
            var guard = new Guard(guardStart);
            var modifiedGrid = grid.modify(c, "#");
            if (guardPathLoops(guard, modifiedGrid)) loops.getAndIncrement();
        });

        return loops.get();
    }

    private HashSet<Coordinate> calculatePath(Guard guard, Grid<String> grid) {
        HashSet<Coordinate> path = new HashSet<>();
        Coordinate guardLocation;
        while ((guardLocation = guard.takeStep(grid)) != null) {
            path.add(guardLocation);
        }
        return path;
    }

    // Returns true if path loops
    private boolean guardPathLoops(Guard guard, Grid<String> grid) {
        HashSet<PathStep> path = new HashSet<>();
        path.add(new PathStep(guard.getPosition(), guard.getDirection()));

        while (guard.takeStep(grid) != null) {
            // detect Loop
            if (!path.add(new PathStep(guard.getPosition(), guard.getDirection()))) {
                return true;
            }
        }
        return false;
    }
}