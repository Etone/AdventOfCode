package tech.corvin.aoc.year2024.day9;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part1 implements Part<Long> {

    private static final Integer FREE_DISK = -1;

    @Override
    public Long solve() throws IOException {
        var diskmap = Helper.getResourceFileAsString("2024/day9.txt");

        var uncompressedDisk = buildDisk(diskmap);
        var compressedDisk = compressDisk(uncompressedDisk);

        return checksum(compressedDisk);
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


    private List<Integer> compressDisk(List<Integer> uncompressedDisk) {
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

    private long checksum(List<Integer> disk) {
        long result = 0;
        for (int i = 0; i < disk.size(); i++) {
            if (disk.get(i) == -1) continue;
            result += (long) i * disk.get(i);
        }

        return result;
    }
}