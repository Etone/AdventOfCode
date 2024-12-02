package tech.corvin.aoc.day1;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;

public class Execute {


    public static void main(String[] args) throws IOException {
        Part<String, Integer> day1 = new Part1();
        var solutionDay1 = day1.solve(Helper.getResourceFileAsString("day1/input1.txt"));

        System.out.println(solutionDay1.toString());

        Part<String, Integer> day2 = new Part2();
        var solutionDay2 = day2.solve(Helper.getResourceFileAsString("day1/input1.txt"));

        System.out.println(solutionDay2.toString());
    }
}
