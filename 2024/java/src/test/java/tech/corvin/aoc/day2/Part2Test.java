package tech.corvin.aoc.day2;


import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part2Test {

    @Test
    void test() throws IOException {
        var cut = new Part2();

        var result = cut.solve("day2.txt");
        assertEquals(4, result);
    }
}
