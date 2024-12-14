package tech.corvin.aoc.year2024.day8;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.StringConstants;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.CoordinatePair;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class Part2 implements Part<Integer> {

    @Override
    public Integer solve() throws IOException {
        var grid = Helper.getInputAsGrid("2024/day8.txt");

        var antinodes = new HashSet<Coordinate>();

        var lookup = grid.lookup(List.of("."));

        for (String character : StringConstants.ALL_CHARACTERS) {
            var antennas = lookup.getOrDefault(character, Collections.emptyList());
            Helper.pairUpList(antennas)
                    .stream()
                    .map(CoordinatePair::fromEntry)
                    .map((pair) -> List.of(pair, pair.flip()))
                    .flatMap(List::stream)
                    .forEach((pair) -> {
                        var antinode = pair.first();
                        while (!antinode.isOOB(grid)) {
                            antinodes.add(antinode);
                            antinode = antinode.offset(pair.distance());
                        }
                    });
        }
        return antinodes.size();
    }
}