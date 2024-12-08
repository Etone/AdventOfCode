package tech.corvin.aoc.day8;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.StringConstants;
import tech.corvin.aoc.general.grid.Coordinate;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part1 implements Part<Integer> {

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
                var doubleDistance = distance.multiply(2);
                var firstAntinode = pair.getKey().offset(doubleDistance);
                if (!firstAntinode.isOOB(grid)) antinodes.add(firstAntinode);

                var flipped = distance.flip().multiply(2);
                var secondAntinode = pair.getValue().offset(flipped);
                if (!secondAntinode.isOOB(grid)) antinodes.add(secondAntinode);
            });
        }

        return antinodes.size();
    }
}