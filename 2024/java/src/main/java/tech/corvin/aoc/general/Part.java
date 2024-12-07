package tech.corvin.aoc.general;

import java.io.IOException;

public interface Part<Result> {

    Result solve() throws IOException;

}