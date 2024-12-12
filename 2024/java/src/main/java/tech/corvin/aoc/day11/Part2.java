package tech.corvin.aoc.day11;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;


public class Part2 implements Part<Long> {
    @Override
    public Long solve() throws IOException {
        var input = Arrays
                .stream(Helper.getResourceFileAsString("day11.txt")
                        .split(" "))
                .map(Long::parseLong)
                .toList();
        return input.stream().mapToLong(stone -> Stone.blink(stone, 75)).sum();
    }
}