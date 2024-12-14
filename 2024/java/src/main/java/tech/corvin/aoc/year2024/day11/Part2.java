package tech.corvin.aoc.year2024.day11;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Arrays;


public class Part2 implements Part<Long> {
    @Override
    public Long solve() throws IOException {
        var input = Arrays
                .stream(Helper.getResourceFileAsString("2024/day11.txt")
                        .split(" "))
                .map(Long::parseLong)
                .toList();
        return input.stream().mapToLong(stone -> Stone.blink(stone, 75)).sum();
    }
}