package tech.corvin.aoc;

import tech.corvin.aoc.day1.Part1;
import tech.corvin.aoc.general.Part;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Part partToTun = new Part1();

        var result = partToTun.solve("day1/input1.txt");

        System.out.println(result.toString());
    }
}
