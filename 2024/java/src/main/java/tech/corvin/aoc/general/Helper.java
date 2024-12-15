package tech.corvin.aoc.general;

import tech.corvin.aoc.general.grid.ArrayGrid;
import tech.corvin.aoc.general.grid.Grid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Helper {

    public static String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) throw new IllegalArgumentException("Input must be present");
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    public static ArrayGrid<String> getInputAsGrid(String fileName) throws IOException {
        var input = Helper.getResourceFileAsString(fileName);
        return arrayGridFromString(input);
    }

    public static Grid<Integer> mapStringGridToIntGrid(ArrayGrid<String> arrayGrid) {
        return arrayGrid.map(Integer::parseInt);
    }

    private static ArrayGrid<String> arrayGridFromString(String gridAsText) {
        var rows = gridAsText.split(System.lineSeparator());
        var result = new String[rows.length][rows[0].length()];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].split("");
        }
        return new ArrayGrid<>(result);
    }
}
