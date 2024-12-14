package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static tech.corvin.aoc.general.grid.Coordinate.*;
import static tech.corvin.aoc.general.grid.Coordinate.LEFT;


public class Day6 extends Day2024<Integer, Integer> {

    Grid<String> grid;
    Guard guard;
    Set<Coordinate> path = new HashSet<>();

    public static void main(String[] args) throws IOException {
        new Day6().print();
    }

    public Day6() throws IOException {
        super(6);
        parseInput();
    }


    @Override
    public Integer part1() {
        return path.size();
    }

    @Override
    public Integer part2() {
        AtomicInteger loops = new AtomicInteger();

        path.forEach(c -> {
            var modifiedGrid = grid.modify(c, "#");
            if (guardPathLoops(modifiedGrid)) loops.getAndIncrement();
        });

        return loops.get();
    }


    private void parseInput() throws IOException {
        grid = inputAsGrid();
        var guardStartPosition = grid.findFirst("^").orElseThrow();
        guard = new Guard(guardStartPosition);
        path.add(guard.position);

        var pathGuard = new Guard(guardStartPosition);
        Coordinate guardLocation;
        while ((guardLocation = pathGuard.takeStep(grid)) != null) {
            path.add(guardLocation);
        }
    }

    // Returns true if path loops
    private boolean guardPathLoops(Grid<String> grid) {
        HashSet<PathStep> path = new HashSet<>();

        var guardStart = new Coordinate(guard.position.row(), guard.position.column());
        var simulatedGuard = new Guard(guardStart);

        path.add(new PathStep(simulatedGuard.position(), simulatedGuard.direction()));
        while (simulatedGuard.takeStep(grid) != null) {
            // detect Loop
            if (!path.add(new PathStep(simulatedGuard.position(), simulatedGuard.direction()))) {
                return true;
            }
        }
        return false;
    }

    private static class Guard {
        private Coordinate position;
        private Coordinate direction;

        public Guard(Coordinate c) {
            position = c;
            direction = TOP;
        }

        private final List<Coordinate> turnOrder = List.of(TOP, RIGHT, BOTTOM, LEFT);
        private int obstacles = 0;

        public Coordinate takeStep(Grid<String> g) {
            var nextPosition = position.offset(direction);
            if (nextPosition.isOOB(g)) return null;
            if (g.getCell(nextPosition).equals("#")) {
                direction = turnOrder.get(++obstacles % 4);
                return position;
            }
            position = nextPosition;
            return position;
        }

        public Coordinate position() {
            return position;
        }

        public Coordinate direction() {
            return direction;
        }
    }

    private record PathStep(
            Coordinate coordinate,
            Coordinate direction
    ) {}
}
