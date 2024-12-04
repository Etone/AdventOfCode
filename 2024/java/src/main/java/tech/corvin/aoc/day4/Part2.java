package tech.corvin.aoc.day4;

import tech.corvin.aoc.general.Coordinate;
import tech.corvin.aoc.general.Grid;
import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Part2 implements Part<Integer> {

    Grid<String> grid = new Grid<>(new String[][]{{}});

    @Override
    public Integer solve(String inputPath) throws IOException {
        var wordSearch = Helper.getResourceFileAsString(inputPath);
        grid = Grid.fromString(wordSearch);
        return checkGrid();
    }


    private int checkGrid() {
        var xmas = 0;
        for (int row = 1; row < grid.length() - 1; row++) {
            for (int col = 1; col < grid.width() - 1; col++) {
                xmas += checkForXMAS(row, col) ? 1 : 0;
            }
        }
        return xmas;
    }

    private boolean checkForXMAS(int row, int column) {
        if (!grid.getCell(row, column).equals("A")) return false;

        //get corners of Cell with A
        var corners = grid.getDiagonal(new Coordinate(row, column));
        return Collections.frequency(corners, "M") == 2 && Collections.frequency(corners, "S") == 2 && !corners.get(0).equals(corners.get(3));
    }
}
