package tech.corvin.aoc.year2024;

import org.junit.jupiter.api.Test;
import tech.corvin.aoc.general.Day;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {

    Day<?,?> cut = new Day1().initialize();

    public Day1Test() throws IOException {
    }

    @Test
    void part1() {
        var actual = cut.part1();
        assertEquals(11, actual);
    }

    @Test
    void part2() {
        var actual = cut.part2();
        assertEquals(31, actual);
    }
}
