package tech.corvin.aoc.day9;

import java.util.stream.IntStream;

public record FileOnDisk(
        int start,
        int end,
        int fileId
) {

    public int size() {
        return end - start;
    }

    public long checksum() {
        return IntStream.range(start, end).mapToLong((index) -> (long) index * fileId).sum();
    }
}
