package tech.corvin.aoc.year2024.day6;

import org.junit.jupiter.api.Test;
import tech.corvin.aoc.year2024.day6.Part2;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part2UnoptimizedTest {

     @Test
    public void test() throws IOException {
         var cut = new Part2();
         var answer = cut.solve();
         assertEquals(6, answer);
    }
}
