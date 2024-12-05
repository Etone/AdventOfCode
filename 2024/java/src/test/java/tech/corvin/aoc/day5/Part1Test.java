package tech.corvin.aoc.day5;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part1Test {

     @Test
    public void test() throws IOException {
         var cut = new Part1();
         var answer = cut.solve("day5.txt");
         assertEquals(143, answer);
    }
}
