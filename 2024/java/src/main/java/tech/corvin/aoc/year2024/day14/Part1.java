package tech.corvin.aoc.year2024.day14;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static tech.corvin.aoc.year2024.day14.Quadrant.*;

public class Part1 implements Part<Integer> {

    private static int roomWidth;
    private static int roomHeight;

    @Override
    public Integer solve() throws IOException {
        var input = Helper.getResourceFileAsString("2024/day14.txt");
        initializeGrid(input);

        var robotsString = input.split("\n\n")[1];
        var robots = parseRobots(robotsString);

        var robotsInQuadrant = robots.stream().map(r -> r.takeSteps(100, roomWidth, roomHeight)).collect(groupingBy(r -> r.quadrant(roomWidth, roomHeight)));

        return Stream.of(UPPER_LEFT, UPPER_RIGHT, LOWER_LEFT, LOWER_RIGHT)
                .mapToInt((quadrant) -> robotsInQuadrant.get(quadrant).size())
                .reduce((a, b) -> a * b)
                .orElseThrow();
    }

    private void initializeGrid(String input) {
        var wh = Arrays.stream(input.split("\n\n")[0].split(",")).mapToInt(Integer::parseInt).toArray();
        roomWidth = wh[0];
        roomHeight = wh[1];
    }

    private List<Robot> parseRobots(String input) {
        return Arrays.stream(input.split(System.lineSeparator())).map(Robot::fromString).toList();
    }
}