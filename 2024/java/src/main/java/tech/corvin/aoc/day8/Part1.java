package tech.corvin.aoc.day8;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.StringConstants;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.CoordinatePair;

import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part1 implements Part<Integer> {

    @Override
    public Integer solve() throws IOException {
        var grid = Helper.getInputAsGrid("day8.txt");

        HashSet<Coordinate> antinodes = new HashSet<>();

        for (String character : StringConstants.ALL_CHARACTERS) {
            var antennas = grid.findAll(character);

            var pairs = Helper.pairUpList(antennas).stream().map(CoordinatePair::fromEntry);

            var mapPairsToDistance = new HashMap<CoordinatePair, Coordinate>();

            pairs.forEach((pair) -> {
                mapPairsToDistance.put(pair, pair.first().distance(pair.second()));
                mapPairsToDistance.put(pair.flip(), pair.second().distance(pair.first()));
            });

            mapPairsToDistance.forEach((pair, distance) -> {
                var antinode = pair.first().offset(distance, 2);
                if (!antinode.isOOB(grid)) antinodes.add(antinode);
            });
        }
        return antinodes.size();
    }
}