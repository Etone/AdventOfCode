package tech.corvin.aoc.year2024;

import org.junit.jupiter.api.Test;
import tech.corvin.aoc.general.Day;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test {

    Day<?,?> cut = new Day17().initialize();

    public Day17Test() throws IOException {
    }

    @Test
    void part1() {
        var actual = cut.part1();
        assertEquals("4,6,3,5,6,3,5,2,1,0", actual);
    }
}
