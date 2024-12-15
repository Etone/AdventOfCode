package tech.corvin.aoc.year2024;

import org.junit.jupiter.api.Test;
import tech.corvin.aoc.general.Day;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test {

    Day<?,?> cut = new Day15();

    public Day15Test() throws IOException {
    }

    @Test
    void part1() {
        var actual = cut.part1();
        assertEquals(10092, actual);
    }
}
