package tech.corvin.aoc;

import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.List;

public class RunAllParts {

    private static final List<Part<?>> parts = List.of(
            new tech.corvin.aoc.day1.Part1(), new tech.corvin.aoc.day1.Part2(),
            new tech.corvin.aoc.day2.Part1(), new tech.corvin.aoc.day2.Part2(),
            new tech.corvin.aoc.day3.Part1(), new tech.corvin.aoc.day3.Part2(),
            new tech.corvin.aoc.day4.Part1(), new tech.corvin.aoc.day4.Part2(),
            new tech.corvin.aoc.day5.Part1(), new tech.corvin.aoc.day5.Part2(),
            new tech.corvin.aoc.day6.Part1(), new tech.corvin.aoc.day6.Part2(),
            new tech.corvin.aoc.day7.Part1(), new tech.corvin.aoc.day7.Part2(),
            new tech.corvin.aoc.day8.Part1(), new tech.corvin.aoc.day8.Part2(),
            new tech.corvin.aoc.day9.Part1(), new tech.corvin.aoc.day9.Part2(),
            new tech.corvin.aoc.day10.Part1(), new tech.corvin.aoc.day10.Part2(),
            new tech.corvin.aoc.day11.Part1(), new tech.corvin.aoc.day11.Part2(),
            new tech.corvin.aoc.day12.Part1(), new tech.corvin.aoc.day12.Part2(),
            new tech.corvin.aoc.day13.Part1(), new tech.corvin.aoc.day13.Part2(),
            new tech.corvin.aoc.day14.Part1(), new tech.corvin.aoc.day14.Part2()
    );

    public static void main(String[] args) {
        parts.forEach(part -> {
            try {
                var start = System.currentTimeMillis();
                var result = part.solve();
                var end = System.currentTimeMillis();

                System.out.println(
                        part.getClass().getCanonicalName().substring(16) +
                                ": " + result +
                                " (took " + (end - start) + "ms)"
                );
            } catch (IOException e) {
                // Ignore
            }
        });
    }
}
