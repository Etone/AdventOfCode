package tech.corvin.aoc.year2024.day6;

import tech.corvin.aoc.general.grid.Coordinate;

public record PathStep(
        Coordinate coordinate,
        Coordinate direction
) {
}
