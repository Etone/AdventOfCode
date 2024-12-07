package tech.corvin.aoc.day7;

import org.junit.jupiter.api.Test;

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
