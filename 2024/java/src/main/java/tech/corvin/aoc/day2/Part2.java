package tech.corvin.aoc.day2;

import tech.corvin.aoc.general.Part;

import java.util.Arrays;
import java.util.List;


public class Part2 implements Part<String, Integer> {
    @Override
    public Integer solve(String reportsDocument) {
        var reports = parseInput(reportsDocument);
        return reports.stream().filter(Report::isSafeDampened).toList().size();
    }

    private List<Report> parseInput(String input) {
        return Arrays.stream(input.split(System.lineSeparator())).map((rawLevels) -> {
            var levels = Arrays.stream(rawLevels.split("\\s")).map(Integer::parseInt).toList();
            return new Report(levels);
        }).toList();
    }
}
