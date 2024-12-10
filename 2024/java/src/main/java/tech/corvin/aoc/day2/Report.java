package tech.corvin.aoc.day2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Report(List<Integer> levels) {

    public boolean isSafe() {
        return isAscOrDesc() && checkAdj();
    }

    public boolean isSafeDampened() {
        var dampenedReports = new ArrayList<Report>();
        dampenedReports.add(this);

        for (int i = 0; i <= levels.size() - 1; i++) {
            var levelsAfter = levels.subList(i + 1, levels.size());
            var levelsBefore = levels.subList(0, i);
            var dampenedLevels = Stream.concat(levelsBefore.stream(), levelsAfter.stream()).toList();
            dampenedReports.add(new Report(dampenedLevels));
        }
        return dampenedReports.stream().anyMatch(Report::isSafe);
    }

    private boolean isAscOrDesc() {
        return levels.stream().sorted().toList().equals(levels)
                || levels.stream().sorted(Comparator.reverseOrder()).toList().equals(levels);
    }

    private boolean checkAdj() {
        return IntStream.range(0, levels.size() - 1).allMatch((index) -> {
                    var diff = Math.abs(levels.get(index) - levels.get(index + 1));
                    return diff >= 1 && diff <= 3;
                }
        );
    }
}
