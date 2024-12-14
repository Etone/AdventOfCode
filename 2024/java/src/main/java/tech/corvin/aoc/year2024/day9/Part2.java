package tech.corvin.aoc.year2024.day9;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.math.IntRange;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;


public class Part2 implements Part<Long> {
    Map<Integer, IntRange> disk = new HashMap<>();
    Set<IntRange> freeSpaces = new TreeSet<>();

    @Override
    public Long solve() throws IOException {
        var diskmap = Helper.getResourceFileAsString("2024/day9.txt");

        buildDisk(diskmap);
        compressDisk();

        return checksum();
    }

    private void buildDisk(String diskmap) {
        var chars = diskmap.split("");
        var fileId = 0;
        var nextStart = 0;
        for (int i = 0; i < chars.length; i++) {
            var count = Integer.parseInt(chars[i]);
            if (count == 0) continue;
            if (i % 2 == 0) {
                disk.put(fileId, new IntRange(nextStart, nextStart + count));
                fileId++;
            } else {
                freeSpaces.add(new IntRange(nextStart, nextStart + count));
            }
            nextStart += count;
        }
    }


    private void compressDisk() {
        var filesByIdReversed = disk.keySet().stream().sorted(Comparator.comparingInt(Integer::intValue).reversed()).toList();

        for (var fileId : filesByIdReversed) {
            var file = disk.get(fileId);
            var sizeNeeded = file.size();
            var space = freeSpaces.stream().filter((free) -> free.size() >= sizeNeeded).filter(free -> free.start() < file.start()).findFirst();
            if (space.isEmpty()) continue;
            var newFile = new IntRange(space.get().start(), space.get().start() + sizeNeeded);
            disk.put(fileId, newFile);
            freeSpaces.remove(space.get());
            if (sizeNeeded < space.get().size())
                freeSpaces.add(new IntRange(newFile.end(), space.get().end()));
        }
    }

    private long checksum() {
        var checksum = 0L;
        for (var file : disk.entrySet()) {

            checksum += IntStream.range(file.getValue().start(), file.getValue().end()).mapToLong((index) -> (long) index * file.getKey()).sum();
        }
        return checksum;
    }
}