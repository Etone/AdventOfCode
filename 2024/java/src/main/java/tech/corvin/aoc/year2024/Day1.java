package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day1 extends Day2024<Integer, Integer> {

    private final List<Integer> left = new ArrayList<>();
    private final List<Integer> right = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new Day1().initialize().print();
    }

    public Day1() throws IOException {
        super(1);
    }


    @Override
    public Integer part1() {
        var pairedUp = new ArrayList<LocationPair>();
        var left = new ArrayList<>(this.left);
        var right = new ArrayList<>(this.right);

        left.sort(Integer::compareTo);
        right.sort(Integer::compareTo);

        for (int i = 0; i < left.size(); i++) {
            pairedUp.add(new LocationPair(left.get(i), right.get(i)));
        }

        return pairedUp.stream().mapToInt(LocationPair::diff).sum();
    }

    @Override
    public Integer part2() {
        return left.stream().mapToInt(location -> location * Collections.frequency(right, location)).sum();
    }

    @Override
    public Day<?, ?> initialize() throws IOException {
        var locationPairs = input().split(System.lineSeparator());
        Arrays.stream(locationPairs)
                .map(line -> line.split("\\s+"))
                .forEach(pair -> {
                    left.add(Integer.parseInt(pair[0]));
                    right.add(Integer.parseInt(pair[1]));
                });
        return this;
    }

    private record LocationPair(
            int left, int right
    ) {
        public int diff() {
            return Math.abs(right - left);
        }
    }
}
