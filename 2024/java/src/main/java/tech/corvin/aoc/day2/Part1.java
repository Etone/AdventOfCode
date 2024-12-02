package tech.corvin.aoc.day2;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Part1 implements Part<Integer> {
    @Override
    public Integer solve() throws IOException {
        var input = Helper.getResourceFileAsString("day2/input1.txt");
        var reports = parseInput(input);
        return reports.stream().filter(Report::isSafe).toList().size();
    }

    private List<Report> parseInput(String input) {
        return Arrays.stream(input.split(System.lineSeparator())).map((rawLevels) -> {
            var levels = Arrays.stream(rawLevels.split("\\s")).map(Integer::parseInt).toList();
            return new Report(levels);
        }).toList();
    }
}
