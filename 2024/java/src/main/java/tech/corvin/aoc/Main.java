package tech.corvin.aoc;

import tech.corvin.aoc.day5.Part2;
import tech.corvin.aoc.general.Part;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Part partToTun = new Part2();

        var result = partToTun.solve("day5/input1.txt");

        System.out.println(result.toString());
    }
}
