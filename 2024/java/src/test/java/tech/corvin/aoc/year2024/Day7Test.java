package tech.corvin.aoc.year2024;

import org.junit.jupiter.api.Test;
import tech.corvin.aoc.general.Day;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Test {

    Day<?,?> cut = new Day7();

    public Day7Test() throws IOException {
    }

    @Test
    void part1() {
        var actual = cut.part1();
        assertEquals(3749L, actual);
    }

    @Test
    void part2() {
        var actual = cut.part2();
        assertEquals(11387L, actual);
    }
}
