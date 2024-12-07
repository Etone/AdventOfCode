package tech.corvin.aoc.day3;

public record MultiplyInstruction(int a, int b) {
    public int execute() {
        return a * b;
    }
}
