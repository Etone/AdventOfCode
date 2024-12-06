package tech.corvin.aoc.day5;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.IntPair;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Part1 implements Part<Integer> {

    List<IntPair> rules;

    List<List<Integer>> pageUpdates;

    @Override
    public Integer solve(String inputPath) throws IOException {
        var input = Helper.getResourceFileAsString(inputPath);
        parseInput(input);

        return pageUpdates
                .stream()
                .filter(this::matchesRules)
                .map((list) -> list.get(list.size() / 2))
                .mapToInt(Integer::intValue)
                .sum();
    }

    private void parseInput(String input) {
        var rulesString = input.split("\n\n")[0];
        var pageUpdateString = input.split("\n\n")[1];

        parseRules(rulesString);
        parsePageUpdates(pageUpdateString);
    }

    private void parseRules(String rulesString) {
        rules = Arrays.stream(rulesString.split(System.lineSeparator())).map((line) -> {
            var x = Integer.parseInt(line.split("\\|")[0]);
            var y = Integer.parseInt(line.split("\\|")[1]);

            return new IntPair(x, y);
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
                .filter((rule) -> updates.contains(rule.left()) && updates.contains(rule.right()))
                .toList();

        return new HashSet<>(allRulesForUpdateList).containsAll(rulesToCheck);
    }

    private List<IntPair> generateAllRules(List<Integer> updates) {
        var allRules = new ArrayList<IntPair>();
        for (int i = 0; i < updates.size(); i++) {
            for (int j = i + 1; j < updates.size(); j++) {
                allRules.add(new IntPair(updates.get(i), updates.get(j)));
            }
        }
        return allRules;
    }
}
