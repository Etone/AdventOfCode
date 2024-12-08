package tech.corvin.aoc.day8;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.StringConstants;
import tech.corvin.aoc.general.grid.Coordinate;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;


public class Part2 implements Part<Integer> {

    @Override
    public Integer solve() throws IOException {
        var grid = Helper.getInputAsGrid("day8.txt");

        HashSet<Coordinate> antinodes = new HashSet<>();

        for (String character : StringConstants.ALL_CHARACTERS) {
            var antennas = grid.findAll(character);

            var pairs = Helper.pairUpList(antennas);

            var mapPairsToDistance = pairs
                    .stream()
                    .map((pair) -> Map.entry(pair, pair.getKey().distance(pair.getValue())))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            mapPairsToDistance.forEach((pair, distance) -> {
                antinodes.add(pair.getKey());
                antinodes.add(pair.getValue());

                var antinode = pair.getKey().offset(distance);
                while (!antinode.isOOB(grid)) {
                    antinodes.add(antinode);
                    antinode = antinode.offset(distance);
                }

                var antinodeFlipped = pair.getKey().offset(distance.flip());
                while (!antinodeFlipped.isOOB(grid)) {
                    antinodes.add(antinodeFlipped);
                    antinodeFlipped = antinodeFlipped.offset(distance.flip());
                }
            });
        }

        return antinodes.size();
    }
}