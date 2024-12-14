package tech.corvin.aoc.year2024.day7;

import org.junit.jupiter.api.Test;
import tech.corvin.aoc.year2024.day7.Part1;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part1Test {

     @Test
    public void test() throws IOException {
         var cut = new Part1();
         var answer = cut.solve();
         assertEquals(3749, answer);
    }
}
