package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.Day;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.*;

import static tech.corvin.aoc.general.grid.Coordinate.*;

public class Day15 extends Day2024<Integer, Integer> {

    private Grid<String> warehouse;
    private Coordinate robot;
    private List<Coordinate> moveOffsets;
    private Set<Coordinate> boxes;
    private Set<Coordinate> walls;

    public static void main(String[] args) throws IOException {
        new Day15().initialize().print();
    }

    public Day15() throws IOException {
        super(15);
    }


    @Override
    public Integer part1() {
        return boxes.stream().mapToInt(coordinate -> 100 * coordinate.row() + coordinate.column()).sum();
    }

    @Override
    public Integer part2() {
       return 0;
    }


    @Override
    public Day<?, ?> initialize() throws IOException {
        var input = input();
        var mapOfWarehouse = input.split("\n\n")[0];
        var robotInstructions = input.split("\n\n")[1].replace("\n", "");
        warehouse = arrayGridFromString(mapOfWarehouse);

        robot = warehouse.findFirst("@").orElseThrow();
        moveOffsets = Arrays.stream(robotInstructions.split("")).map(s -> switch (s) {
            case "^" -> TOP;
            case ">" -> RIGHT;
            case "<" -> LEFT;
            case "v" -> BOTTOM;
            default -> throw new IllegalStateException("Each instruction must be of ^<>V");
        }).toList();

        boxes = new HashSet<>(warehouse.findAll("O"));
        walls = new HashSet<>(warehouse.findAll("#"));
        simulateRobot();

        return this;
    }

    private void simulateRobot() {

        var simulatedRobot = new Coordinate(robot.row(), robot.column());

        for (Coordinate instruction : moveOffsets) {
            var robotWantToMove = simulatedRobot.offset(instruction);

            if (walls.contains(robotWantToMove)) continue;

            if (boxes.contains(robotWantToMove)) {
                var boxStackToMove = new ArrayDeque<Coordinate>();
                var box = robotWantToMove;
                boxStackToMove.push(box);
                while (boxes.contains(box.offset(instruction))) {
                    box = box.offset(instruction);
                    boxStackToMove.push(box);
                }

                if (walls.contains(box.offset(instruction))) continue;

                while (!boxStackToMove.isEmpty()) {
                    var boxToMove = boxStackToMove.pop();
                    boxes.remove(boxToMove);
                    boxes.add(boxToMove.offset(instruction));
                }
            }
            simulatedRobot = robotWantToMove;
        }
    }
}
