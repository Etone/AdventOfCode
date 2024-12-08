package tech.corvin.aoc.day8;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.StringConstants;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.CoordinatePair;

import java.io.IOException;
import java.util.HashMap;
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
            var mapPairsToDistance = new HashMap<CoordinatePair, Coordinate>();
            Helper.pairUpList(antennas)
                    .stream()
                    .map(CoordinatePair::fromEntry)
                    .forEach((pair) -> {
                        mapPairsToDistance.put(pair, pair.first().distance(pair.second()));
                        mapPairsToDistance.put(pair.flip(), pair.second().distance(pair.first()));
                    });

            mapPairsToDistance.forEach((pair, distance) -> {
                var antinode = pair.first();

                while (!antinode.isOOB(grid)) {
                    antinodes.add(antinode);
                    antinode = antinode.offset(distance);
                }
            });
        }
        return antinodes.size();
    }
}