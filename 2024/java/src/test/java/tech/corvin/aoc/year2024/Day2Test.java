package tech.corvin.aoc.year2024;

import org.junit.jupiter.api.Test;
import tech.corvin.aoc.general.Day;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Test {

    Day<?,?> cut = new Day2();

    public Day2Test() throws IOException {
    }

    @Test
    void part1() {
        var actual = cut.part1();
        assertEquals(2, actual);
    }

    @Test
    void part2() {
        var actual = cut.part2();
        assertEquals(4, actual);
    }
}
