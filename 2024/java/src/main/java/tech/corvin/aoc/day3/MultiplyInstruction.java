package tech.corvin.aoc.day3;

import java.util.Arrays;

public record MultiplyInstruction(int a, int b){

    public static MultiplyInstruction fromString(String s) {
        var numbers = Arrays.stream(s.replace("mul(", "").replace(")", "").split(",")).map(Integer::parseInt).toList();
        return new MultiplyInstruction(numbers.get(0),numbers.get(1));
    }
    public int execute() {
        return a*b;
    }
}
