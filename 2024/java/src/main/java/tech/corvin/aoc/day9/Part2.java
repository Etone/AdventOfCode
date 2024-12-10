package tech.corvin.aoc.day9;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;
import tech.corvin.aoc.general.math.IntPair;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;


public class Part2 implements Part<Long> {
    Map<Integer, IntPair> disk = new HashMap<>();
    Set<IntPair> freeSpaces = new TreeSet<>();

    @Override
    public Long solve() throws IOException {
        var diskmap = Helper.getResourceFileAsString("day9.txt");

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
                disk.put(fileId, new IntPair(nextStart, nextStart + count));
                fileId++;
            } else {
                freeSpaces.add(new IntPair(nextStart, nextStart + count));
            }
            nextStart += count;
        }
    }


    private void compressDisk() {
        var filesByIdReversed = disk.keySet().stream().sorted(Comparator.comparingInt(Integer::intValue).reversed()).toList();

        for (var fileId : filesByIdReversed) {
            var file = disk.get(fileId);
            var sizeNeeded = file.diff();
            var space = freeSpaces.stream().filter((free) -> free.diff() >= sizeNeeded).filter(free -> free.left() < file.left() ).findFirst();
            if (space.isEmpty()) continue;
            var newFile = new IntPair(space.get().left(), space.get().left() + sizeNeeded);
            disk.put(fileId, newFile);
            freeSpaces.remove(space.get());
            if (sizeNeeded < space.get().diff())
                freeSpaces.add(new IntPair(newFile.right(), space.get().right()));
        }
    }

    private long checksum() {
        var checksum = 0L;
        for (var file : disk.entrySet()) {

            checksum += IntStream.range(file.getValue().left(), file.getValue().right()).mapToLong((index) -> (long) index * file.getKey()).sum();
        }
        return checksum;
    }
}