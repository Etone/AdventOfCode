package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.Day;
import tech.corvin.aoc.general.math.IntRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.util.function.Predicate.not;


public class Day5 extends Day2024<Integer, Integer> {

    List<IntRange> rules;
    List<List<Integer>> pageUpdates;

    public static void main(String[] args) throws IOException {
        new Day5().initialize().print();
    }

    public Day5() throws IOException {
        super(5);
    }


    @Override
    public Integer part1() {
        return pageUpdates
                .stream()
                .filter(this::matchesRules)
                .map((list) -> list.get(list.size() / 2))
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public Integer part2() {
        return pageUpdates
                .stream()
                .filter(not(this::matchesRules))
                .map((list) -> list.stream().sorted(this::compare).toList())
                .map((list) -> list.get(list.size() / 2))
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public Day<?, ?> initialize() throws IOException {
        var input = input();
        var rulesString = input.split("\n\n")[0];
        var pageUpdateString = input.split("\n\n")[1];

        parseRules(rulesString);
        parsePageUpdates(pageUpdateString);
        return this;
    }

    private void parseRules(String rulesString) {
        rules = Arrays.stream(rulesString.split(System.lineSeparator())).map((line) -> {
            var x = Integer.parseInt(line.split("\\|")[0]);
            var y = Integer.parseInt(line.split("\\|")[1]);

            return new IntRange(x, y);
        }).toList();
    }

    private void parsePageUpdates(String pageUpdateString) {
        pageUpdates = Arrays.stream(pageUpdateString.split(System.lineSeparator()))
                .map((line) -> Arrays.stream(line.split(",")).map(Integer::parseInt).toList())
                .toList();
    }

    private boolean matchesRules(List<Integer> updates) {
        var allRulesForUpdateList = generateAllRules(updates);

        var rulesToCheck = rules
                .stream()
                .filter((rule) -> updates.contains(rule.start()) && updates.contains(rule.end()))
                .toList();

        return new HashSet<>(allRulesForUpdateList).containsAll(rulesToCheck);
    }

    private List<IntRange> generateAllRules(List<Integer> updates) {
        var allRules = new ArrayList<IntRange>();
        for (int i = 0; i < updates.size(); i++) {
            for (int j = i + 1; j < updates.size(); j++) {
                allRules.add(new IntRange(updates.get(i), updates.get(j)));
            }
        }
        return allRules;
    }

    private int compare(int a, int b) {
        if (rules.contains(new IntRange(a, b))) return -1;
        else if (rules.contains(new IntRange(b, a))) return 1;
        return 0;
    }
}
