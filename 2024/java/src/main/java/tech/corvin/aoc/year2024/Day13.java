package tech.corvin.aoc.year2024;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day13 extends Day2024<Long, Long> {

    private Set<ClawMachine> machinesWithoutOffset;
    private Set<ClawMachine> machinesWithOffset;

    public static void main(String[] args) throws IOException {
        new Day13().print();
    }

    public Day13() throws IOException {
        super(13);
        parseInput();
    }


    @Override
    public Long part1() {
        return machinesWithoutOffset.stream().mapToLong(ClawMachine::findMinimumTokensNeeded).sum();
    }

    @Override
    public Long part2() {
       return machinesWithOffset.stream().mapToLong(ClawMachine::findMinimumTokensNeeded).sum();
    }


    private void parseInput() throws IOException {
        machinesWithoutOffset = Arrays.stream(input().split("\n\n"))
                .map(machine -> ClawMachine.fromString(machine, 0))
                .collect(Collectors.toSet());

        machinesWithOffset = Arrays.stream(input().split("\n\n"))
                .map(machine -> ClawMachine.fromString(machine, 10_000_000_000_000L))
                .collect(Collectors.toSet());
    }

    private record ClawMachine(
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
             *
             * A = Times A Button was pressed
             * B = Times B Button was pressed
             *
             * ax * A + bx * B = px;
             * ay * A + by * B = py;
             *
             * - Multiply with bx / by
             * by * ax * A + bx * B * by = px * by
             * bx * ay * A + by * B * bx = py * bx
             *
             * - Subtract formulas
             * by * ax * A - bx * ay * A = px * by - py * bx
             *
             * - Divide to get A
             * ASSUMPTION, ax * by - ay * bx != 0, else we would divide by zero, which would make this a lot harder
             * A = (px * by - py * bx) / (ax * by - ay * bx)
             *
             * - calculate B from this result
             * ax * A + bx * B = px
             * B = (px - ax * A) / bx
             *
             *
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

}
