package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class Day11 extends Day2024<Long, Long> {

    private List<Long> stones;

    public static void main(String[] args) throws IOException {
        new Day11().initialize().print();
    }

    public Day11() throws IOException {
        super(11);
    }


    @Override
    public Long part1() {
        return stones.stream().mapToLong(stone -> Stone.blink(stone, 25)).sum();
    }

    @Override
    public Long part2() {
        return stones.stream().mapToLong(stone -> Stone.blink(stone, 75)).sum();

    }

    @Override
    public Day<?, ?> initialize() throws IOException {
        stones = Arrays.stream(input().split(" "))
                .map(Long::parseLong)
                .toList();
        return this;
    }

    private static class Stone {
        private static final Hashtable<CachingKey, Long> cache = new Hashtable<>(100_000);
        static int hits = 0;

        public static Long blink(long stone, int times) {
            var key = new CachingKey(stone, times);
            if (cache.containsKey(key)) {
                hits++;
                return cache.get(key);
            }
            if (times == 0) return 1L;

            var result = 0L;
            if (stone == 0) result = blink(1L, times - 1);

            else if (String.valueOf(stone).length() % 2 == 0) {
                var stoneAsText = String.valueOf(stone);
                var firstHalf = Long.parseLong(stoneAsText.substring(0, stoneAsText.length() / 2));
                var secondHalf = Long.parseLong(stoneAsText.substring(stoneAsText.length() / 2));
                result = blink(firstHalf, times - 1) + blink(secondHalf, times - 1);
            }

            else result = blink(stone * 2024, times - 1);

            cache.put(key, result);
            return result;
        }

        private record CachingKey(long stone, int steps) { }
    }
}
