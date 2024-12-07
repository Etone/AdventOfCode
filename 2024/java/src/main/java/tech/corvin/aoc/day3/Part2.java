package tech.corvin.aoc.day3;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Part2 implements Part<Integer> {

    private boolean enabled = true;

    @Override
    public Integer solve() throws IOException {
        var instructions = Helper.getResourceFileAsString("day3.txt");
        var validInstructions = getValidInstructions(instructions);
        return sumInstructions(validInstructions);
    }

    private List<MultiplyInstruction> getValidInstructions(String input) {
        var validInstructions = new ArrayList<MultiplyInstruction>();

        var pattern = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\)");
        var matcher = pattern.matcher(input);

        while (matcher.find()) {
            var instructionText = matcher.group();

            if (instructionText.startsWith("do(")) {
                enabled = true;
                continue;
            }

            if (instructionText.startsWith("don't(")) {
                enabled = false;
                continue;
            }

            if (enabled) {
                var validInstruction = new MultiplyInstruction(Integer.parseInt(matcher.group(1)),Integer.parseInt(matcher.group(2)));
                validInstructions.add(validInstruction);
            }
        }
        return validInstructions;
    }

    private int sumInstructions(List<MultiplyInstruction> multiplyInstructions) {
        return multiplyInstructions.stream().map(MultiplyInstruction::execute).mapToInt(Integer::intValue).sum();
    }
}
