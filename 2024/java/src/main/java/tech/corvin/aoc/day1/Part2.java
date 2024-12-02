package tech.corvin.aoc.day1;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Part2 implements Part<Integer> {
    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();

    @Override
    public Integer solve(String inputFilePath) throws IOException {
        var input = Helper.getResourceFileAsString(inputFilePath);
        parseInput(input);
        return calculateSimilarityBetweenLists();
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

    private Integer calculateSimilarityBetweenLists() {
        AtomicInteger similarity = new AtomicInteger();

        left.forEach((location) -> similarity.addAndGet(location * Collections.frequency(right, location)));

        return similarity.get();
    }
}
