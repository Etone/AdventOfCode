package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.Day;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static tech.corvin.aoc.year2024.Day14.Quadrant.*;

public class Day14 extends Day2024<Integer, Integer> {

    private static int roomWidth;
    private static int roomHeight;

    private List<Robot> robots;

    private static final String PATH_TO_FRAMES = "/tmp/aoc/day14/";


    public static void main(String[] args) throws IOException {
        var day14 = new Day14();
        day14.initialize();
        day14.print();
        day14.part2WithPNGs();
    }

    public Day14() throws IOException {
        super(14);
    }


    @Override
    public Integer part1() {
        var robotsInQuadrant = robots.stream().map(r -> r.takeSteps(100, roomWidth, roomHeight)).collect(groupingBy(r -> r.quadrant(roomWidth, roomHeight)));

        return Stream.of(UPPER_LEFT, UPPER_RIGHT, LOWER_LEFT, LOWER_RIGHT)
                .mapToInt((quadrant) -> robotsInQuadrant.get(quadrant).size())
                .reduce((a, b) -> a * b)
                .orElseThrow();
    }

    @Override
    public Integer part2() {
        var safetyFactorPerSecond = new HashMap<Integer, Integer>();

        for (int i = 0; i < roomWidth * roomHeight; i++) {
            int currentSecond = i; // JetBrains recommended
            var robotsInQuadrant = robots.stream().map(r -> r.takeSteps(currentSecond, roomWidth, roomHeight)).collect(groupingBy(r -> r.quadrant(roomWidth, roomHeight)));

            var safetyFactor =  Stream.of(UPPER_LEFT, UPPER_RIGHT, LOWER_LEFT, LOWER_RIGHT)
                    .mapToInt((quadrant) -> robotsInQuadrant.get(quadrant).size())
                    .reduce((a, b) -> a * b)
                    .orElseThrow();

            safetyFactorPerSecond.put(currentSecond, safetyFactor);
        }

        // Minimum SafteyFactor means minimum entropy means the robots stand quite clumped together,
        // which for a christmas tree should be true
        return safetyFactorPerSecond.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue)).orElseThrow().getKey();
    }

    public void part2WithPNGs() {
        for (int i = 0; i < roomWidth * roomHeight; i++) {
            int currentSecond = i; // JetBrains recommended
            var robotsAfterSeconds = robots.stream().map(r -> r.takeSteps(currentSecond, roomWidth, roomHeight)).toList();
            printRobots(robotsAfterSeconds, "" + currentSecond);
        }
    }


    @Override
    public Day<?, ?> initialize() throws IOException {
        var wh = Arrays.stream(input().split("\n\n")[0].split(",")).mapToInt(Integer::parseInt).toArray();
        roomWidth = wh[0];
        roomHeight = wh[1];

        robots = Arrays.stream(input().split("\n\n")[1].split(System.lineSeparator())).map(Robot::fromString).toList();
        return this;
    }

    private void printRobots(List<Robot> robots, String filePrefix) {
        var output = new BufferedImage(roomWidth, roomHeight, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < roomHeight; row++) {
            for (int col = 0; col < roomWidth; col++) {
                output.setRGB(col,row, Color.BLACK.getRGB());
            }
        }

        robots.forEach((r) -> output.setRGB(r.x(), r.y(), Color.WHITE.getRGB()));

        try {
            Files.createDirectories(Path.of(PATH_TO_FRAMES));
            File f = new File(PATH_TO_FRAMES + filePrefix + ".png");
            ImageIO.write(output, "png", f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        public Robot takeSteps(int times, int roomWidth, int roomHeight) {
            // Java % produces remainder, which can be negative. To get the real modulus, we do floorMod here
            var y = Math.floorMod(this.y + vy * times, roomHeight);
            var x = Math.floorMod(this.x + vx * times, roomWidth);

            return new Robot(x, y, 0, 0);
        }

        public Quadrant quadrant(int roomWidth, int roomHeight) {
            var left = x < roomWidth / 2;
            var right = x > roomWidth / 2;
            var upper = y < roomHeight / 2;
            var lower = y > roomHeight / 2;

            if (upper && left) return UPPER_LEFT;
            if (upper && right) return UPPER_RIGHT;
            if (lower && left) return LOWER_LEFT;
            if (lower && right) return LOWER_RIGHT;

            return NONE;
        }
    }

    enum Quadrant {
        UPPER_LEFT, LOWER_LEFT, UPPER_RIGHT, LOWER_RIGHT, NONE
    }

}
