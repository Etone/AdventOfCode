package tech.corvin.aoc.year2024.day13;


import org.junit.jupiter.api.Test;
import tech.corvin.aoc.year2024.day13.Part1;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part1Test {

    @Test
    void test() throws IOException {
        var cut = new Part1();

        var result = cut.solve();
        assertEquals(480, result);
    }
}
