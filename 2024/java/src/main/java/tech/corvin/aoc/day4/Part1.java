package tech.corvin.aoc.day4;

import tech.corvin.aoc.general.Grid;
import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.stream.IntStream;

public class Part1 implements Part<Integer> {

    Grid<String> grid = new Grid<>(new String[][]{{}});

    @Override
    public Integer solve(String inputPath) throws IOException {
        var wordSearch = Helper.getResourceFileAsString(inputPath);
        grid = Grid.fromString(wordSearch);
        return checkGrid();
    }

    private int checkGrid() {
        var xmas = 0;
        for (int row = 0; row < grid.length(); row ++) {
            for (int col = 0; col < grid.width(); col++) {
                xmas += checkForXMAS(row, col);
            }
        }
        return xmas;
    }

    private int checkForXMAS(int row, int column) {
        if (!grid.getCell(row, column).equals("X")) return 0;


        var count = 0;
        //Check for MAS in each direction
        for (var dr = -1; dr <= 1; dr++) {

            if (row + 3 * dr < 0 || row + 3* dr >= grid.length()) continue;

            for (var dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                if (column + 3 * dc < 0 || column + 3* dc >= grid.width()) continue;

                if (grid.getCell(row + dr, column + dc).equals("M")
                        && grid.getCell(row + 2 * dr, column + 2 * dc).equals("A")
                        && grid.getCell(row + 3 * dr, column + 3 * dc).equals("S")) count++;
            }
        }
        return count;
    }
}
