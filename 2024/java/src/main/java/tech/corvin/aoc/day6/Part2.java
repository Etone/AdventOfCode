package tech.corvin.aoc.day6;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static tech.corvin.aoc.general.grid.Coordinate.*;

public class Part2 implements Part<Integer> {

    Grid<String> grid;
    Guard guard;

    List<Coordinate> path = new ArrayList<>();


    @Override
    public Integer solve(String inputPath) throws IOException {
        var input = Helper.getResourceFileAsString(inputPath);
        grid = Grid.fromString(input);
        var loops = 0;

        for (int row = 0; row < grid.length(); row++) {
            for (int col = 0; col < grid.width(); col++) {
                Coordinate toCheck =  new Coordinate(row, col);

                if (toCheck.equals(guard.getPosition())) continue;
                var beforeObstacle = grid.getCell(toCheck);
                grid.setCell(toCheck, "#");
                if (guardPathLoops()) loops++;

                grid.setCell(toCheck, beforeObstacle);
            }
        }
        return loops;
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
            if(path.contains(new PathStep(guard.getPosition(), guard.getDirection()))) {
                return true;
            }

            path.add(new PathStep(guard.getPosition(), guard.getDirection()));
        }
        return false;
    }
}