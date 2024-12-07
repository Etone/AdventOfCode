package tech.corvin.aoc.day7;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part2Test {

     @Test
    public void test() throws IOException {
         var cut = new Part2();
         var answer = cut.solve("day7.txt");
         assertEquals(11387, answer);
    }
}
