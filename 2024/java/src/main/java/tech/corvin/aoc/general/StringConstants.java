package tech.corvin.aoc.general;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class StringConstants {

    public static final List<String> UPPERCASE = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
    public static final List<String> LOWERCASE = UPPERCASE.stream().map(String::toLowerCase).toList();
    public static final List<String> DIGITS = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");
    public static final List<String> ALL_CHARACTERS = Stream.of(UPPERCASE, LOWERCASE, DIGITS).flatMap(Collection::stream).toList();
}
