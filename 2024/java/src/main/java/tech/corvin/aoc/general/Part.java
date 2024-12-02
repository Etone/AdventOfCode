package tech.corvin.aoc.general;

import java.io.IOException;

public interface Part<Output> {

    Output solve() throws IOException;
}
