package tech.corvin.aoc.year2024.day9;

import tech.corvin.aoc.general.Helper;
import tech.corvin.aoc.general.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class Part2Unoptimized implements Part<Long> {

    private static final Integer FREE_DISK = -1;

    @Override
    public Long solve() throws IOException {
        var diskmap = Helper.getResourceFileAsString("2024/day9.txt");

        var uncompressedDisk = buildDisk(diskmap);
        var compressedDisk = compressDisk(uncompressedDisk);

        return checksum(compressedDisk);
    }


    private List<FileOnDisk> buildDisk(String diskmap) {
        var disk = new ArrayList<FileOnDisk>();

        var chars = diskmap.split("");
        var fileId = 0;

        var nextStart = 0;

        for (int i = 0; i < chars.length; i++) {
            var count = Integer.parseInt(chars[i]);

            if (count == 0) continue;

            if (i % 2 == 0) {
                disk.add(new FileOnDisk(nextStart, nextStart + count, fileId));
                fileId++;
            } else {
                disk.add(new FileOnDisk(nextStart, nextStart + count, FREE_DISK));
            }
            nextStart += count;

        }
        return disk;
    }


    private List<FileOnDisk> compressDisk(List<FileOnDisk> uncompressedDisk) {
        var sortedByFileId = uncompressedDisk.stream().sorted((file1, file2) -> file2.fileId() - file1.fileId()).filter((file) -> file.fileId() != FREE_DISK).toList();
        AtomicReference<List<FileOnDisk>> freeSpaces = new AtomicReference<>(uncompressedDisk.stream().filter((file) -> file.fileId() == FREE_DISK).toList());

        var compressedDisk = new ArrayList<>(uncompressedDisk);

        sortedByFileId.forEach((file) -> {
            var freeSpace = freeSpaces.get().stream().filter((freeOnDisk) -> freeOnDisk.size() >= file.size() && freeOnDisk.start() < file.start()).min(Comparator.comparingInt(FileOnDisk::start));
            if (freeSpace.isPresent()) {
                var movedFile = new FileOnDisk(freeSpace.get().start(), freeSpace.get().start() + file.size(), file.fileId());
                var replacedEmptySpace = new FileOnDisk(file.start(), file.end(), FREE_DISK);
                compressedDisk.remove(file);
                compressedDisk.add(movedFile);
                compressedDisk.add(replacedEmptySpace);

                //shrink free space
                var newFreeSpace = new FileOnDisk(movedFile.end(), freeSpace.get().end(), FREE_DISK);
                compressedDisk.remove(freeSpace.get());
                compressedDisk.add(newFreeSpace);

                freeSpaces.set(compressedDisk.stream().filter((f) -> f.fileId() == FREE_DISK).toList());
            }
        });
        return compressedDisk;
    }

    private long checksum(List<FileOnDisk> disk) {
        return disk.stream().filter((file) -> file.fileId() != FREE_DISK).mapToLong(FileOnDisk::checksum).sum();
    }
}