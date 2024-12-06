package tech.corvin.aoc.day6;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part2Test {

     @Test
    public void test() throws IOException {
         var cut = new Part2();
         var answer = cut.solve("day6.txt");
         assertEquals(6, answer);
    }
}
