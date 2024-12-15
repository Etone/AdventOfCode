package tech.corvin.aoc.year2024;

import org.junit.jupiter.api.Test;
import tech.corvin.aoc.general.Day;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    Day<?,?> cut = new Day11();

    public Day11Test() throws IOException {
    }

    @Test
    void part1() {
        var actual = cut.part1();
        assertEquals(55312L, actual);
    }

    @Test
    void part2() {
        var actual = cut.part2();
        assertEquals(65601038650482L, actual);
    }
}
