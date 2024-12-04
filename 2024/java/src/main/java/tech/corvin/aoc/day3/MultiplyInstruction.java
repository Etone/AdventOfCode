package tech.corvin.aoc.day3;

import java.util.Arrays;

public record MultiplyInstruction(int a, int b) {
    public int execute() {
        return a * b;
    }
}
