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

public class Part1 implements Part<Integer> {

    @Override
    public Integer solve() throws IOException {
        var grid = Helper.getInputAsGrid("2024/day8.txt");

        HashSet<Coordinate> antinodes = new HashSet<>();

        var lookup = grid.lookup(List.of("."));

        for (String character : StringConstants.ALL_CHARACTERS) {
            var antennas = lookup.getOrDefault(character, Collections.emptyList());
            // pairUpList to get all Pairs possible for each Item in the List
            Helper.pairUpList(antennas)
                    .stream()
                    .map(CoordinatePair::fromEntry)
                    // We need to check distance from A -> B and from B -> A, so duplicate all entries with the flipped distance
                    .map((pair) -> List.of(pair, pair.flip()))
                    .flatMap(List::stream)

                    .forEach((pair) -> {
                        var antinode = pair.first().offset(pair.distance(), 2);
                        if (!antinode.isOOB(grid)) antinodes.add(antinode);
                    });
        }
        return antinodes.size();
    }
}