package tech.corvin.aoc.day14;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.ArrayGrid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static tech.corvin.aoc.day14.Quadrant.*;
import static tech.corvin.aoc.day14.Quadrant.LOWER_RIGHT;


public class Part2 implements Part<String> {

    private static int roomWidth;
    private static int roomHeight;

    @Override
    public String solve() throws IOException {
        var input = Helper.getResourceFileAsString("day14.txt");
        initializeGrid(input);

        var robotsString = input.split("\n\n")[1];
        var robots = parseRobots(robotsString);

        for (int i = 0; i < roomWidth * roomHeight; i++) {
            int currentSecond = i; // JetBrains recommended
            var robotsAfterSeconds = robots.stream().map(r -> r.takeSteps(currentSecond, roomWidth, roomHeight)).toList();
            printRobots(robotsAfterSeconds, "" + currentSecond);
        }
        // sln from generated files and visual inspection.
        return "No result here, check created files in /tmp";
    }

    private void initializeGrid(String input) {
        var wh = Arrays.stream(input.split("\n\n")[0].split(",")).mapToInt(Integer::parseInt).toArray();
        roomWidth = wh[0];
        roomHeight = wh[1];
    }

    private List<Robot> parseRobots(String input) {
        return Arrays.stream(input.split(System.lineSeparator())).map(Robot::fromString).toList();
    }

    private void printRobots(List<Robot> robots, String filePrefix) {
        var output = new BufferedImage(roomWidth, roomHeight, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < roomHeight; row++) {
            for (int col = 0; col < roomWidth; col++) {
                output.setRGB(col,row, Color.BLACK.getRGB());
            }
        }

        robots.forEach((r) -> {
            output.setRGB(r.x(), r.y(), Color.WHITE.getRGB());
        });

        try {
            File f = new File("/tmp/aoc/day14/" + filePrefix + ".png");
            ImageIO.write(output, "png", f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}