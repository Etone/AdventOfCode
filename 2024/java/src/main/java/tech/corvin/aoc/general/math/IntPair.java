package tech.corvin.aoc.general.math;

public record IntPair(int left, int right) implements Comparable<IntPair> {
    public int diff() {
        return Math.abs(left - right);
    }

    public int sum() { return left + right; }

    // Comparator for IntRanges. Left starts earlier so is first
    @Override
    public int compareTo(IntPair o) {
        return left - o.left;
    }
}
