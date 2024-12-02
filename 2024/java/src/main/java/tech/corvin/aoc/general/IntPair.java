package tech.corvin.aoc.general;

public record IntPair(int left, int right) {

    public int diff() {
        return Math.abs(left - right);
    }
}
