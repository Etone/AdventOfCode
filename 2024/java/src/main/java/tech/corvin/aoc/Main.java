package tech.corvin.aoc;

import tech.corvin.aoc.day7.Part1;
import tech.corvin.aoc.day7.Part2;
import tech.corvin.aoc.general.Part;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Part partToRun = new Part2();

        var result = partToRun.solve("day7/input1.txt");

        System.out.println(result.toString());
    }
}
