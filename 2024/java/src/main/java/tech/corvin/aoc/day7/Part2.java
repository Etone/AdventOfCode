package tech.corvin.aoc.day7;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Part2 implements Part<Long> {

    @Override
    public Long solve(String inputPath) throws IOException {
        var input = Helper.getResourceFileAsString(inputPath);
        var instructions = parseToInstructions(input);

        return instructions
                .stream()
                .filter((Instruction::isSolvableWithConcatenation))
                .map(Instruction::testValue)
                .mapToLong(Long::longValue)
                .sum();
    }

    private List<Instruction> parseToInstructions(String input) {
        return Arrays.stream(input.split(System.lineSeparator())).map(Instruction::fromString).toList();
    }
}