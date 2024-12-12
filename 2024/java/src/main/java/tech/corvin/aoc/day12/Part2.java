package tech.corvin.aoc.day12;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;


public class Part2 implements Part<Integer> {

    @Override
    public Integer solve() throws IOException {
        var garden = Helper.getInputAsGrid("day12.txt");
        var regions = garden.findAllRegions(garden::getOrthogonalCells);
        return regions.stream().map(Region::bulkOrderFencingCosts).mapToInt(Integer::intValue).sum();
    }
}