package tech.corvin.aoc.general;

import tech.corvin.aoc.general.grid.ArrayGrid;
import tech.corvin.aoc.general.grid.Grid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.stream.Collectors;

public abstract class Day<Result1, Result2> {

    private final int year;
    private final int day;

    public Day(int year, int day) {
        this.day = day;
        this.year = year;
    }

    public abstract Result1 part1();

    public abstract Result2 part2();

    public static String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) throw new IllegalArgumentException("No file found under " + fileName);
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    public String input() throws IOException {
        return getResourceFileAsString(Path.of("" + year, "day" + day + ".txt").toString());
    }

    public Grid<String> inputAsGrid() throws IOException {
        return arrayGridFromString(input());
    }

    public void print() {
        System.out.println("Part 1:" + part1().toString());
        System.out.println("Part 2:" + part2().toString());
    }

    public void printWithRuntime() {
        var start = System.currentTimeMillis();
        var part1 = part1();
        var end = System.currentTimeMillis();
        System.out.printf("Day %d - Part 1: %s (took %d ms)%n", day, part1.toString(), end - start);

        start = System.currentTimeMillis();
        var part2 = part2();
        end = System.currentTimeMillis();
        System.out.printf("Day %d - Part 2: %s (took %d ms)%n", day, part2.toString(), end - start);
    }

    public ArrayGrid<String> arrayGridFromString(String gridAsText) {
        var rows = gridAsText.split(System.lineSeparator());
        var result = new String[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].split("");
        }
        return new ArrayGrid<>(result);
    }
}
