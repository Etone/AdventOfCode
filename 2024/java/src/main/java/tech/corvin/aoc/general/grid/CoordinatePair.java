package tech.corvin.aoc.general.grid;

import java.util.Map.Entry;

public record CoordinatePair(
        Coordinate first,
        Coordinate second
) {
    public CoordinatePair flip() {
        return new CoordinatePair(second, first);
    }

    public static CoordinatePair fromEntry(Entry<Coordinate, Coordinate> entry) {
        return new CoordinatePair(entry.getKey(), entry.getValue());
    }
}
