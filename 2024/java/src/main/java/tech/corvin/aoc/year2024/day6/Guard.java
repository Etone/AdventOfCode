package tech.corvin.aoc.year2024.day6;

import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.util.List;

import static tech.corvin.aoc.general.grid.Coordinate.*;

public class Guard {
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

    public Coordinate getPosition() {
        return position;
    }

    public Coordinate getDirection() {
        return direction;
    }
}
