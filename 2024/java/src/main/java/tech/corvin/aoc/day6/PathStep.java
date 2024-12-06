package tech.corvin.aoc.day6;

import tech.corvin.aoc.general.grid.Coordinate;

public record PathStep(
        Coordinate coordinate,
        Coordinate direction
) {
}
