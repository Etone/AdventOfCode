package tech.corvin.aoc.day3;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part1Test {

     @Test
    public void test() throws IOException {
         var cut = new Part1();
         var answer = cut.solve("day3.txt");
         assertEquals(161, answer);
    }
}
