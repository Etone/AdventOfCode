package tech.corvin.aoc.day1;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.IntPair;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Part1 implements Part<Integer> {

    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();

    @Override
    public Integer solve() throws IOException {
        var input = Helper.getResourceFileAsString("day2/input1.txt");

        parseInput(input);
        return calculateDifferenceBetweenLocations(pairUpRawLists()).stream().mapToInt(Integer::intValue).sum();
    }

    private void parseInput(String input) {
        var locationPairs = input.split(System.lineSeparator());
        Arrays.stream(locationPairs)
                .map((line) -> line.split("\\s+"))
                .forEach((pairOfLocationIds) -> {
                    left.add(Integer.parseInt(pairOfLocationIds[0]));
                    right.add(Integer.parseInt(pairOfLocationIds[1]));
                });
    }

    private List<IntPair> pairUpRawLists() {
        var pairedUp = new ArrayList<IntPair>();
        Collections.sort(left);
        Collections.sort(right);

        for (int i = 0; i < left.size(); i++) {
            pairedUp.add(new IntPair(left.get(i), right.get(i)));
        }
        return pairedUp;
    }

    private List<Integer> calculateDifferenceBetweenLocations(List<IntPair> pairedUpLocationIds) {
        return pairedUpLocationIds.stream().map(IntPair::diff).toList();
    }
}
