package tech.corvin.aoc.day13;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class Part2 implements Part<Long> {

    @Override
    public Long solve() throws IOException {
        var input = Helper.getResourceFileAsString("day13.txt");

        var machines = parseInput(input);

        return machines.stream()
                .mapToLong(ClawMachine::findMinimumTokensNeeded)
                .sum();
    }

    private Set<ClawMachine> parseInput(String input) {
        return Arrays.stream(input.split("\n\n"))
                .map(machine -> ClawMachine.fromString(machine, 10_000_000_000_000L))
                .collect(Collectors.toSet());
    }
}