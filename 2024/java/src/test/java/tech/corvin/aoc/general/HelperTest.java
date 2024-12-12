package tech.corvin.aoc.general;

import org.junit.jupiter.api.Test;
import tech.corvin.aoc.general.grid.ArrayGrid;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelperTest {

    @Test
    void stringToIntGridMapTest() {
        var stringGrid = new ArrayGrid<>(new String[][]{{"1", "3"}, {"2", "4"}});
        var intGrid = Helper.mapStringGridToIntGrid(stringGrid);

        assertEquals(1, intGrid.getCell(0,0));
        assertEquals(2, intGrid.getCell(1,0));
        assertEquals(3, intGrid.getCell(0,1));
        assertEquals(4, intGrid.getCell(1,1));
    }
}
