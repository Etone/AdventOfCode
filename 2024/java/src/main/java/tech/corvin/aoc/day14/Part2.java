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


public class Part2 implements Part<Integer> {

    private static int roomWidth;
    private static int roomHeight;

    @Override
    public Integer solve() throws IOException {
        var input = Helper.getResourceFileAsString("day14.txt");
        initializeGrid(input);

        var robotsString = input.split("\n\n")[1];
        var robots = parseRobots(robotsString);

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

        // sln from generated files and visual inspection.
        return safetyFactorPerSecond.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue)).orElseThrow().getKey();
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