package tech.corvin.aoc.day11;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Arrays;

public class Part1 implements Part<Long> {
    @Override
    public Long solve() throws IOException {
        var input = Arrays
                .stream(Helper.getResourceFileAsString("day11.txt")
                        .split(" "))
                .map(Long::parseLong)
                .toList();
        return input.stream().mapToLong(stone -> Stone.blink(stone, 25)).sum();
    }
}