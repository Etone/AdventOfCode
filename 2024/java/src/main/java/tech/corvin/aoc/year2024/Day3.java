package tech.corvin.aoc.year2024;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Day3 extends Day2024<Integer, Integer> {

    List<MultiplyInstruction> instructions = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        new Day3().print();
    }

    public Day3() throws IOException {
        super(3);
        parseInput();
    }


    @Override
    public Integer part1() {
        return instructions.stream().mapToInt(MultiplyInstruction::execute).sum();
    }

    @Override
    public Integer part2() {
        return instructions.stream().filter(MultiplyInstruction::enabled).mapToInt(MultiplyInstruction::execute).sum();
    }


    private void parseInput() throws IOException {
        var enabled = true;
        var pattern = Pattern.compile("do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\)");
        var matcher = pattern.matcher(input());

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

            var validInstruction = new MultiplyInstruction(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    enabled
            );
            instructions.add(validInstruction);
        }
    }

    private record MultiplyInstruction(int a, int b, boolean enabled) {
        public int execute() {
            return a * b;
        }
    }
}
