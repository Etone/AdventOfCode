package tech.corvin.aoc.general.math;

public record IntRange(int start, int end) implements Comparable<IntRange> {
    public int size() {
        return Math.abs(start - end);
    }

    // Comparator for IntRanges. Left starts earlier so is first
    @Override
    public int compareTo(IntRange o) {
        return start - o.start;
    }
}
