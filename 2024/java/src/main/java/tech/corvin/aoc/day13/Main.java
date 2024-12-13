package tech.corvin.aoc.day13;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        var result = new Part1().solve();
        System.out.println(result.toString());

        var result2 = new Part2().solve();
        System.out.println(result2.toString());
    }
}
