/*

MIT License

Copyright © 2026 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/

package com.hardcodedjoy.appbase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileMetadata {
    public final File file;
    public final String fileName;
    public final String fileNameLowerCase;
    public final boolean isDirectory;
    public final long lastModified;
    public final long length;

    public FileMetadata(File file) {
        this.file = file;
        this.fileName = file.getName();
        this.fileNameLowerCase = this.fileName.toLowerCase(Locale.US);
        this.isDirectory = file.isDirectory();
        this.lastModified = file.lastModified();
        if(isDirectory) { this.length = 0; }
        else { this.length = file.length(); }
    }

    public static List<FileMetadata> list(File[] files) {
        if(files == null || files.length == 0) { return Collections.emptyList(); }

        List<FileMetadata> metas = new ArrayList<>(files.length);

        // Scale thread pool based on CPU cores
        // (great for parallelizing disk stat calls)
        int poolSize = Math.min(files.length,
                Math.max(2, Runtime.getRuntime().availableProcessors() * 2));
        // *2 -> saturates the storage controller's parallel capacity
        // without overwhelming the Android system scheduler.

        //noinspection resource (executor.close() not available in API19)
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        try {
            List<Future<FileMetadata>> futures = new ArrayList<>(files.length);
            for(File file : files) {
                futures.add(executor.submit(() -> new FileMetadata(file)));
            }
            for(Future<FileMetadata> future : futures) {
                // Collect results as they finish
                try { metas.add(future.get()); } catch (Exception ignored) {}
            }
        } finally {
            executor.shutdown();
        }

        return metas;
    }

    public static List<FileMetadata> list(List<File> files) {
        if(files == null) { return Collections.emptyList(); }
        return list(files.toArray(new File[0]));
    }

    public static void sortNewestFirstDirsFirst(List<FileMetadata> metas) {
        Collections.sort(metas, (m1, m2) -> {
            if(m1.isDirectory != m2.isDirectory) {
                return m1.isDirectory ? -1 : 1; // Dirs first
            }
            return Long.compare(m2.lastModified, m1.lastModified); // Newest first
        });
    }

    public static void sortOldestFirstDirsFirst(List<FileMetadata> metas) {
        Collections.sort(metas, (m1, m2) -> {
            if(m1.isDirectory != m2.isDirectory) {
                return m1.isDirectory ? -1 : 1; // Dirs first
            }
            return Long.compare(m1.lastModified, m2.lastModified); // Oldest first
        });
    }

    public static void sortAlphabeticallyDirsFirst(List<FileMetadata> metas) {
        Collections.sort(metas, (m1, m2) -> {
            if(m1.isDirectory != m2.isDirectory) {
                return m1.isDirectory ? -1 : 1; // Dirs first
            }
            // Alphabetically:
            return m1.fileNameLowerCase.compareTo(m2.fileNameLowerCase);
        });
    }

    public static void sortReverseAlphabeticallyDirsFirst(List<FileMetadata> metas) {
        Collections.sort(metas, (m1, m2) -> {
            if(m1.isDirectory != m2.isDirectory) {
                return m1.isDirectory ? -1 : 1; // Dirs first
            }
            // Reverse Alphabetically:
            return m2.fileNameLowerCase.compareTo(m1.fileNameLowerCase);
        });
    }

    public static void sortLargestFirstDirsFirst(List<FileMetadata> metas) {
        Collections.sort(metas, (m1, m2) -> {
            if(m1.isDirectory != m2.isDirectory) {
                return m1.isDirectory ? -1 : 1; // Dirs first
            }
            // if both are directories: sort alphabetically, ignoring case
            if(m1.isDirectory) {
                return m1.fileNameLowerCase.compareTo(m2.fileNameLowerCase);
            }
            // Largest First:
            return Long.compare(m2.length, m1.length);
        });
    }

    public static void sortSmallestFirstDirsFirst(List<FileMetadata> metas) {
        Collections.sort(metas, (m1, m2) -> {
            if(m1.isDirectory != m2.isDirectory) {
                return m1.isDirectory ? -1 : 1; // Dirs first
            }
            // if both are directories: sort alphabetically, ignoring case
            if(m1.isDirectory) {
                return m1.fileNameLowerCase.compareTo(m2.fileNameLowerCase);
            }
            // Smallest First:
            return Long.compare(m1.length, m2.length);
        });
    }
}