package tech.corvin.aoc.day14;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static tech.corvin.aoc.day14.Part1.Quadrant.*;

public class Part1 implements Part<Integer> {

    protected static int roomWidth;
    protected static int roomHeight;

    @Override
    public Integer solve() throws IOException {
        var input = Helper.getResourceFileAsString("day14.txt");
        initializeGrid(input);

        var robotsString = input.split("\n\n")[1];
        var robots = parseRobots(robotsString);

        var robotsInQuadrant = robots.stream().map(r -> r.takeSteps(100)).collect(groupingBy(Robot::quadrant));

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

    private record Robot(
            int x, int y,
            int vx, int vy
    ) {
        private static final Pattern PATTERN = Pattern.compile("(-?\\d+)");


        public static Robot fromString(String s) {
            var matcher = PATTERN.matcher(s);
            var numbers = Arrays.stream(matcher.results()
                            .map(MatchResult::group)
                            .toArray(String[]::new))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);

            return new Robot(numbers[0], numbers[1], numbers[2], numbers[3]);
        }

        public Robot takeSteps(int times) {
            // Java % produces remainder, which can be negative. To get the real modulus, we do floorMod here
            var y = Math.floorMod(this.y + vy * 100, roomHeight);
            var x = Math.floorMod(this.x + vx * times, roomWidth);

            return new Robot(x, y, vx, vy);
        }

        public Quadrant quadrant() {
            var left = x < roomWidth / 2;
            var right = x > roomWidth / 2;
            var upper = y < roomHeight / 2;
            var lower = y > roomHeight / 2;

            if (upper && left) return UPPER_LEFT;
            if (upper && right) return UPPER_RIGHT;
            if (lower && left) return Quadrant.LOWER_LEFT;
            if (lower && right) return Quadrant.LOWER_RIGHT;

            return Quadrant.NONE;
        }
    }

    enum Quadrant {
        UPPER_LEFT, LOWER_LEFT, UPPER_RIGHT, LOWER_RIGHT, NONE
    }
}