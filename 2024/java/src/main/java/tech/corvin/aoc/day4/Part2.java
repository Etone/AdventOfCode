package tech.corvin.aoc.day4;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.grid.Coordinate;
import tech.corvin.aoc.general.grid.ArrayGrid;
import tech.corvin.aoc.general.grid.Grid;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static tech.corvin.aoc.general.grid.Coordinate.BOTTOM_RIGHT;
import static tech.corvin.aoc.general.grid.Coordinate.TOP_LEFT;

public class Part2 implements Part<Integer> {

    Grid<String> grid = new ArrayGrid<>(new String[][]{{}});

    @Override
    public Integer solve() throws IOException {
        grid = Helper.getInputAsGrid("day4.txt");
        return checkGrid();
    }


    private int checkGrid() {
        var xmas = 0;
        for (int row = 1; row < grid.length() - 1; row++) {
            for (int col = 1; col < grid.width() - 1; col++) {
                xmas += checkForXMAS(new Coordinate(row, col)) ? 1 : 0;
            }
        }
        return xmas;
    }

    private boolean checkForXMAS(Coordinate toCheck) {
        if (!grid.getCell(toCheck).equals("A")) return false;

        //get corners of Cell with A
        var corners = grid.getDiagonalValues(toCheck);
        return Collections.frequency(corners, "M") == 2 && Collections.frequency(corners, "S") == 2 && grid.getCellValues(toCheck, List.of(TOP_LEFT, BOTTOM_RIGHT)).stream().distinct().count() != 1;
    }
}
