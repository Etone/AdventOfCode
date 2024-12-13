package tech.corvin.aoc.day13;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;


public record ClawMachine(
        int ax,
        int ay,
        int bx,
        int by,
        long px,
        long py
) {

    public static ClawMachine fromString(String input, long offsetPrice) {
        var pattern = Pattern.compile("(\\d+)");
        var matcher = pattern.matcher(input);

        var allNumbers = Arrays.stream(matcher.results()
                        .map(MatchResult::group)
                        .toArray(String[]::new))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        return new ClawMachine(
                allNumbers[0], allNumbers[1], //Button A press
                allNumbers[2], allNumbers[3], //Button B press
                allNumbers[4] + offsetPrice, allNumbers[5] + offsetPrice // Prize Location
        );
    }

    public long findMinimumTokensNeeded() {
        /*
        * Linear Algebra is not my strong suite, so I looked it up
        * Using floating numbers here, since these equations will always intersect somewhere
        * We can't partially press buttons, so we need to filter these solutions out later
        */
        double aButtonPressed = (double) (px * by - py * bx) / (ax * by - ay * bx);
        double bButtonPressed = (double) (px - ax * aButtonPressed) / bx;

        // Later here: double % 1 == 0 checks if the sln was integer and only then returns the correct value
        if (aButtonPressed % 1 == 0 && bButtonPressed % 1 == 0) {
            return (long) (aButtonPressed * 3 + bButtonPressed * 1);
        }
        return 0;
    }
}
