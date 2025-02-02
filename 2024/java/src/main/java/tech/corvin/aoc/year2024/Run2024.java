package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.Day;

import java.io.IOException;
import java.util.List;

public class Run2024 {

    static List<Day<?,?>> days;

    static {
        try {
            days = List.of(
                    new Day1(), new Day2(), new Day3(), new Day4(), new Day5(),
                    new Day6(), new Day7(), new Day8(), new Day9(), new Day10(),
                    new Day11(), new Day12(), new Day13(), new Day14(), new Day15()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Run2024() {
    }

    public static void main(String[] args) throws IOException {
        for (Day<?, ?> day : days) {
            day.printWithRuntime();
        }
    }
}
