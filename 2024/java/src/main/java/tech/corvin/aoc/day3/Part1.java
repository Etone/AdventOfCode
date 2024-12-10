package tech.corvin.aoc.day3;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Part1 implements Part<Integer> {
    @Override
    public Integer solve() throws IOException {
        var instructions = Helper.getResourceFileAsString("day3.txt");
        var validInstructions = getValidInstructions(instructions);
        return sumInstructions(validInstructions);
    }

    private List<MultiplyInstruction> getValidInstructions(String input) {
        var validInstructions = new ArrayList<MultiplyInstruction>();

        var pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        var matcher = pattern.matcher(input);

        while (matcher.find()) {
            var validInstruction = new MultiplyInstruction(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))); //Matching Groups are indexed starting at 1
            validInstructions.add(validInstruction);
        }
        return validInstructions;
    }

    private int sumInstructions(List<MultiplyInstruction> multiplyInstructions) {
        return multiplyInstructions.stream().map(MultiplyInstruction::execute).mapToInt(Integer::intValue).sum();
    }
}
