package tech.corvin.aoc.day11;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

public class Part1 implements Part<Long> {

    Hashtable<CachingKey, Long> cache = new Hashtable<>(100_000);

    @Override
    public Long solve() throws IOException {
        var input = Arrays
                .stream(Helper.getResourceFileAsString("day11.txt")
                        .split(" "))
                .map(Long::parseLong)
                .toList();
        return input.stream().mapToLong(stone -> calculateStonesAfter(stone, 25)).sum();
    }

    public Long calculateStonesAfter(Long stone, int steps) {
        var key = new CachingKey(stone, steps);
        if (cache.containsKey(key)) return cache.get(key);
        if (steps == 0) return 1L;

        var result = 0L;
        if (stone == 0) result = calculateStonesAfter(1L, steps - 1);
        else if (stone.toString().length() % 2 == 0) {
            var stoneAsText = stone.toString();
            var firstHalf = Long.parseLong(stoneAsText.substring(0, stoneAsText.length() / 2));
            var secondHalf = Long.parseLong(stoneAsText.substring(stoneAsText.length() / 2));
            result = calculateStonesAfter(firstHalf, steps - 1) + calculateStonesAfter(secondHalf, steps - 1);
        } else result = calculateStonesAfter(stone * 2024, steps - 1);

        cache.put(key, result);
        return result;
    }

    private record CachingKey(Long stone, int steps) {
    }
}