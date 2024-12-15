package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.StringConstants;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.CoordinatePair;
import tech.corvin.aoc.general.grid.Grid;
import tech.corvin.aoc.general.util.ListUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Day8 extends Day2024<Integer, Integer> {

    private Grid<String> grid;
    private final List<CoordinatePair> pairs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new Day8().print();
    }

    public Day8() throws IOException {
        super(8);
        initializeDay();
    }


    @Override
    public Integer part1() {
        HashSet<Coordinate> antinodes = new HashSet<>();

       pairs.forEach(pair -> {
            var antinode = pair.first().offset(pair.distance(), 2);
            if (!antinode.isOOB(grid)) antinodes.add(antinode);
        });

        return antinodes.size();
    }

    @Override
    public Integer part2() {
        var antinodes = new HashSet<Coordinate>();
       pairs.forEach(pair -> {
            var antinode = pair.first();
            while (!antinode.isOOB(grid)) {
                antinodes.add(antinode);
                antinode = antinode.offset(pair.distance());
            }
        });
        return antinodes.size();
    }


    private void initializeDay() throws IOException {
        grid = inputAsGrid();
        var lookupTable = grid.lookup(List.of("."));

        for (String character : StringConstants.ALL_CHARACTERS) {
            var antennas = lookupTable.getOrDefault(character, Collections.emptyList());
            var pairsFromAntenna = ListUtil.pairUpList(antennas)
                    .stream()
                    .map(CoordinatePair::fromEntry)
                    .map(pair -> List.of(pair, pair.flip()))
                    .flatMap(List::stream)
                    .toList();
            pairs.addAll(pairsFromAntenna);
        }
    }
}
