package tech.corvin.aoc.day11;

import java.util.Hashtable;

public class Stone {
    private static final Hashtable<CachingKey, Long> cache = new Hashtable<>(100_000);
    static int hits = 0;

    public static Long blink(long stone, int times) {
        var key = new CachingKey(stone, times);
        if (cache.containsKey(key)) {
            hits++;
            return cache.get(key);
        };
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
