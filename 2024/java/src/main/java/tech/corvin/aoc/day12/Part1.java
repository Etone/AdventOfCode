package tech.corvin.aoc.day12;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.Coordinate;

import java.io.IOException;

public class Part1 implements Part<Integer> {

    @Override
    public Integer solve() throws IOException {
        var garden = Helper.getInputAsGrid("day12.txt");
        var regions = garden.findAllRegions(garden::getOrthogonalCells);
        return regions.stream().peek((r) -> System.out.println(r.size() + " - " + r.perimeter())).map(Region::fencingCosts).mapToInt(Integer::intValue).sum();
    }
}