package tech.corvin.aoc;

import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.List;

public class Main {

    static List<Part> partsToSolve = List.of(
            new tech.corvin.aoc.day1.Part1(),
            new tech.corvin.aoc.day1.Part2(),
            new tech.corvin.aoc.day2.Part1(),
            new tech.corvin.aoc.day2.Part2()
    );
    public static void main(String[] args) {
        partsToSolve.forEach(part -> {
            try {
                var answer = part.solve();
                System.out.println(answer.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}