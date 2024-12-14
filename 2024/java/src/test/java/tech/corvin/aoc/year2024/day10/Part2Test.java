package tech.corvin.aoc.year2024.day10;


import org.junit.jupiter.api.Test;
import tech.corvin.aoc.year2024.day10.Part2;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part2Test {

    @Test
    void test() throws IOException {
        var cut = new Part2();

        var result = cut.solve();
        assertEquals(81, result);
    }
}
