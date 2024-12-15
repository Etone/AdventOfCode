package tech.corvin.aoc.year2024;

import tech.corvin.aoc.general.Day;
import tech.corvin.aoc.general.math.IntRange;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day9 extends Day2024<Long, Long> {

    private static final Integer FREE_DISK = -1;

    private List<Integer> uncompressedDisk;
    private List<Integer> compressedDisk;

    private final Map<Integer, IntRange> disk = new HashMap<>();
    private final Set<IntRange> freeSpaces = new TreeSet<>();

    public static void main(String[] args) throws IOException {
        new Day9().initialize().print();
    }

    public Day9() throws IOException {
        super(9);
    }


    @Override
    public Long part1() {
        return checksum(compressedDisk);
    }

    @Override
    public Long part2() {
       return checksumV2();
    }

    @Override
    public Day<?, ?> initialize() throws IOException {
        uncompressedDisk = buildDisk(input());
        compressedDisk = compressDisk();

        buildDiskV2(input());
        compressDiskV2();
        return this;
    }

    private List<Integer> buildDisk(String diskmap) {
        var disk = new ArrayList<Integer>();

        var chars = diskmap.split("");
        var fileId = 0;

        for (int i = 0; i < chars.length; i++) {
            var count = Integer.parseInt(chars[i]);

            if (i % 2 == 0) {
                disk.addAll(Collections.nCopies(count, fileId));
                fileId++;
            } else {
                disk.addAll(Collections.nCopies(count, FREE_DISK));
            }
        }

        return disk;
    }

    private void buildDiskV2(String diskmap) {
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


    private List<Integer> compressDisk() {
        var compressed = new ArrayList<>(uncompressedDisk);

        var freeSpaceId = uncompressedDisk.indexOf(FREE_DISK);

        for (var i = uncompressedDisk.size() - 1; i >= 0; i--) {
            var fileId = uncompressedDisk.get(i);
            if (fileId.equals(FREE_DISK)) continue;

            compressed.set(i, FREE_DISK);
            freeSpaceId = compressed.indexOf(FREE_DISK);
            compressed.set(freeSpaceId, fileId);
        }
        return compressed;
    }

    private void compressDiskV2() {
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

    private long checksum(List<Integer> disk) {
        long result = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) == -1) continue;
            result += (long) i * disk.get(i);
        }

        return result;
    }

    private long checksumV2() {
        var checksum = 0L;
        for (var file : disk.entrySet()) {

            checksum += IntStream.range(file.getValue().start(), file.getValue().end()).mapToLong((index) -> (long) index * file.getKey()).sum();
        }
        return checksum;
    }
}
