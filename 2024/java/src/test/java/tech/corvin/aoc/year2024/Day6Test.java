package tech.corvin.aoc.year2024;

import org.junit.jupiter.api.Test;
import tech.corvin.aoc.general.Day;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {

    Day<?,?> cut = new Day6();

    public Day6Test() throws IOException {
    }

    @Test
    void part1() {
        var actual = cut.part1();
        assertEquals(41, actual);
    }

    @Test
    void part2() {
        var actual = cut.part2();
        assertEquals(6, actual);
    }
}
