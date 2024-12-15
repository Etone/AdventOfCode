package tech.corvin.aoc.year2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Day7 extends Day2024<Long, Long> {
    private List<Instruction> instructions;

    public static void main(String[] args) throws IOException {
        new Day7().print();
    }

    public Day7() throws IOException {
        super(7);
        parseInput();
    }


    @Override
    public Long part1() {
        return instructions
                .stream()
                .filter(Instruction::isSolvable)
                .map(Instruction::testValue)
                .mapToLong(Long::longValue)
                .sum();
    }

    @Override
    public Long part2() {
        return instructions
                .stream()
                .filter(Instruction::isSolvableWithConcatenation)
                .map(Instruction::testValue)
                .mapToLong(Long::longValue)
                .sum();
    }


    private void parseInput() throws IOException {
        instructions = Arrays.stream(input().split(System.lineSeparator())).map(Instruction::fromString).toList();
    }

    private record Instruction(
            long testValue,
            List<Long> numbers
    ) {
        public static Instruction fromString(String s) {
            var testValue = Long.parseLong(s.split(":")[0]);
            var numbers = Arrays
                    .stream(s.split(":")[1].split(" "))
                    .filter(Predicate.not(String::isEmpty))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            return new Instruction(testValue, numbers);
        }

        public boolean isSolvable() {
            if (numbers.size() == 1) return numbers.getFirst() == testValue;
            return multiply().isSolvable() || add().isSolvable();
        }

        public boolean isSolvableWithConcatenation() {
            if (numbers.size() == 1) return numbers.getFirst() == testValue;
            return multiply().isSolvableWithConcatenation()
                    || add().isSolvableWithConcatenation()
                    || concatenate().isSolvableWithConcatenation();
        }

        private Instruction multiply() {
            return operate((a, b) -> a * b);
        }

        private Instruction add() {
            return operate(Long::sum);
        }

        private Instruction concatenate() {
            return operate((a, b) -> Long.parseLong("%d%d".formatted(a, b)));
        }

        private Instruction operate(BiFunction<Long, Long, Long> operator) {
            var copy = new ArrayList<>(numbers);
            var firstElement = copy.removeFirst();
            var secondElement = copy.removeFirst();
            copy.addFirst(operator.apply(firstElement, secondElement));
            return new Instruction(testValue, copy);
        }
    }
}
