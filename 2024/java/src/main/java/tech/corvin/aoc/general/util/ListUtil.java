package tech.corvin.aoc.general.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListUtil {

    /**
     * Generate a List of Entries, where each Entry is a Pair of the List.
     * Generates all possible Pairs in the provided List
     *
     * @param list List to pair up
     * @return a List of Map.Entries with each possible pairing
     * @param <T> Type of the list
     */
    public static <T> List<Map.Entry<T, T>> pairUpList(List<T> list) {
        var pairs = new ArrayList<Map.Entry<T, T>>();
        for (int first = 0; first < list.size(); first++) {
            for (int second = first + 1; second < list.size(); second++) {
                pairs.add(Map.entry(list.get(first), list.get(second)));
            }
        }
        return pairs;
    }
}
