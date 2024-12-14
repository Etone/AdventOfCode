package tech.corvin.aoc.year2024.day14;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;


public class Part2WithImageGeneration implements Part<String> {

    private static int roomWidth;
    private static int roomHeight;

    private static final String PATH_TO_FRAMES = "/tmp/aoc/day14/";

    @Override
    public String solve() throws IOException {
        var input = Helper.getResourceFileAsString("2024/day14.txt");
        initializeGrid(input);

        var robotsString = input.split("\n\n")[1];
        var robots = parseRobots(robotsString);

        for (int i = 0; i < roomWidth * roomHeight; i++) {
            int currentSecond = i; // JetBrains recommended
            var robotsAfterSeconds = robots.stream().map(r -> r.takeSteps(currentSecond, roomWidth, roomHeight)).toList();
            printRobots(robotsAfterSeconds, "" + currentSecond);
        }

        // sln from generated files and visual inspection.
        return "Please check /tmp/aoc/day14 for each frame and see if you can spot the tree";
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
            Files.createDirectories(Path.of(PATH_TO_FRAMES));
            File f = new File(PATH_TO_FRAMES + filePrefix + ".png");
            ImageIO.write(output, "png", f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}