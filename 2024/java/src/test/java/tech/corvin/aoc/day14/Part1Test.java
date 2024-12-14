package tech.corvin.aoc.day14;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part1Test {

    @Test
    void test() throws IOException {
        var cut = new Part1();

        var result = cut.solve();
        assertEquals(12, result);
    }
}
