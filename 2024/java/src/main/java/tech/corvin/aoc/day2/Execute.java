package tech.corvin.aoc.day2;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;

public class Execute {


    public static void main(String[] args) throws IOException {

        var input = Helper.getResourceFileAsString("day2/input1.txt");
        Part<String, Integer> part1 = new Part1();

        var solPart1 = part1.solve(input);

        System.out.println(solPart1.toString());

        Part<String, Integer> part2 = new Part2();

        var solPart2 = part2.solve(input);

        System.out.println(solPart2.toString());
    }
}
