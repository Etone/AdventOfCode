package tech.corvin.aoc.day7;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Part1 implements Part<Long> {

    @Override
    public Long solve() throws IOException {
        var input = Helper.getResourceFileAsString("day7.txt");
        var instructions = parseToInstructions(input);

        return instructions
                .stream()
                .filter((Instruction::isSolvable))
                .map(Instruction::testValue)
                .mapToLong(Long::longValue)
                .sum();
    }

    private List<Instruction> parseToInstructions(String input) {
        return Arrays.stream(input.split(System.lineSeparator())).map(Instruction::fromString).toList();
    }
}