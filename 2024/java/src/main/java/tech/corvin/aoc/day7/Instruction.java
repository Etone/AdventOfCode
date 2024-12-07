package tech.corvin.aoc.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record Instruction(
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
        var multiply = multiply();
        var add = add();
        return multiply.isSolvable() || add.isSolvable();
    }

    public boolean isSolvableWithConcatenation() {
        if (numbers.size() == 1) return numbers.getFirst() == testValue;
        var multiply = multiply();
        var add = add();
        var concat = concatenate();
        return multiply.isSolvableWithConcatenation()
                || add.isSolvableWithConcatenation()
                || concat.isSolvableWithConcatenation();
    }

    private Instruction multiply() {
        var multiplied = new ArrayList<>(numbers);

        var firstElement = multiplied.removeFirst();
        var secondElement = multiplied.removeFirst();

        multiplied.addFirst(firstElement * secondElement);
        return new Instruction(testValue, multiplied);
    }

    private Instruction add() {
        var added = new ArrayList<>(numbers);

        var firstElement = added.removeFirst();
        var secondElement = added.removeFirst();

        added.addFirst(firstElement + secondElement);
        return new Instruction(testValue, added);
    }

    private Instruction concatenate() {
        var concat = new ArrayList<>(numbers);

        var firstElement = concat.removeFirst();
        var secondElement = concat.removeFirst();

        concat.addFirst(Long.parseLong("%d%d".formatted(firstElement, secondElement)));
        return new Instruction(testValue, concat);
    }
}
