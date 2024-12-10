package tech.corvin.aoc.day4;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;

public class Part1 implements Part<Integer> {

    Grid<String> grid = new Grid<>(new String[][]{{}});

    @Override
    public Integer solve() throws IOException {
        grid = Helper.getInputAsGrid("day4.txt");
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
        var coordinate = new Coordinate(row, column);
        var value = grid.getCell(coordinate);
        if (!value.equals("X")) return 0;

        var count = 0;
        //Check for MAS in each direction
        for (var dr = -1; dr <= 1; dr++) {

            if (row + 3 * dr < 0 || row + 3* dr >= grid.length()) continue;

            for (var dc = -1; dc <= 1; dc++) {

                if (dr == 0 && dc == 0) continue;

                if (column + 3 * dc < 0 || column + 3* dc >= grid.width()) continue;

                if (grid.getCell(coordinate.offset(dr, dc)).equals("M")
                        && grid.getCell(coordinate.offset(2 * dr, 2 * dc)).equals("A")
                        && grid.getCell(coordinate.offset(3 * dr, 3 * dc)).equals("S")) count++;
            }
        }
        return count;
    }
}
