package tech.corvin.aoc.day14;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static tech.corvin.aoc.day14.Quadrant.*;

record Robot(
        int x, int y,
        int vx, int vy
) {
    private static final Pattern PATTERN = Pattern.compile("(-?\\d+)");


    public static Robot fromString(String s) {
        var matcher = PATTERN.matcher(s);
        var numbers = Arrays.stream(matcher.results()
                        .map(MatchResult::group)
                        .toArray(String[]::new))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        return new Robot(numbers[0], numbers[1], numbers[2], numbers[3]);
    }

    public Robot takeSteps(int times, int roomWidth, int roomHeight) {
        // Java % produces remainder, which can be negative. To get the real modulus, we do floorMod here
        var y = Math.floorMod(this.y + vy * times, roomHeight);
        var x = Math.floorMod(this.x + vx * times, roomWidth);

        return new Robot(x, y, 0, 0);
    }

    public Quadrant quadrant(int roomWidth, int roomHeight) {
        var left = x < roomWidth / 2;
        var right = x > roomWidth / 2;
        var upper = y < roomHeight / 2;
        var lower = y > roomHeight / 2;

        if (upper && left) return UPPER_LEFT;
        if (upper && right) return UPPER_RIGHT;
        if (lower && left) return LOWER_LEFT;
        if (lower && right) return LOWER_RIGHT;

        return Quadrant.NONE;
    }
}

enum Quadrant {
    UPPER_LEFT, LOWER_LEFT, UPPER_RIGHT, LOWER_RIGHT, NONE
}
