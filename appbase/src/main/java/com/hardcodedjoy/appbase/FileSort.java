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
import java.util.List;

@SuppressWarnings("unused")
public class FileSort {

    public static final String BY_NAME = "by_name";
    public static final String BY_SIZE = "by_size";
    public static final String BY_DATE = "by_date";
    public static final String BY_NAME_REVERSED = "by_name_reversed";
    public static final String BY_SIZE_REVERSED = "by_size_reversed";
    public static final String BY_DATE_REVERSED = "by_date_reversed";

    public static void newestFirstDirsFirst(File[] files) {
        if(files == null || files.length <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortNewestFirstDirsFirst(metas);
        for(int i=0; i<files.length; i++) { files[i] = metas.get(i).file; }
    }

    public static void newestFirstDirsFirst(List<File> files) {
        if(files == null || files.size() <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortNewestFirstDirsFirst(metas);
        for(int i=0; i<files.size(); i++) { files.set(i, metas.get(i).file); }
    }

    public static void oldestFirstDirsFirst(File[] files) {
        if(files == null || files.length <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortOldestFirstDirsFirst(metas);
        for(int i=0; i<files.length; i++) { files[i] = metas.get(i).file; }
    }

    public static void oldestFirstDirsFirst(List<File> files) {
        if(files == null || files.size() <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortOldestFirstDirsFirst(metas);
        for(int i=0; i<files.size(); i++) { files.set(i, metas.get(i).file); }
    }

    public static void alphabeticallyDirsFirst(File[] files) {
        if(files == null || files.length <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortAlphabeticallyDirsFirst(metas);
        for(int i=0; i<files.length; i++) { files[i] = metas.get(i).file; }
    }

    public static void alphabeticallyDirsFirst(List<File> files) {
        if(files == null || files.size() <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortAlphabeticallyDirsFirst(metas);
        for(int i=0; i<files.size(); i++) { files.set(i, metas.get(i).file); }
    }

    public static void reverseAlphabeticallyDirsFirst(File[] files) {
        if(files == null || files.length <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortReverseAlphabeticallyDirsFirst(metas);
        for(int i=0; i<files.length; i++) { files[i] = metas.get(i).file; }
    }

    public static void reverseAlphabeticallyDirsFirst(List<File> files) {
        if(files == null || files.size() <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortReverseAlphabeticallyDirsFirst(metas);
        for(int i=0; i<files.size(); i++) { files.set(i, metas.get(i).file); }
    }

    public static void largestFirstDirsFirst(File[] files) {
        if(files == null || files.length <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortLargestFirstDirsFirst(metas);
        for(int i=0; i<files.length; i++) { files[i] = metas.get(i).file; }
    }

    public static void largestFirstDirsFirst(List<File> files) {
        if(files == null || files.size() <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortLargestFirstDirsFirst(metas);
        for(int i=0; i<files.size(); i++) { files.set(i, metas.get(i).file); }
    }

    public static void smallestFirstDirsFirst(File[] files) {
        if(files == null || files.length <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortSmallestFirstDirsFirst(metas);
        for(int i=0; i<files.length; i++) { files[i] = metas.get(i).file; }
    }

    public static void smallestFirstDirsFirst(List<File> files) {
        if(files == null || files.size() <= 1) { return; }
        List<FileMetadata> metas = FileMetadata.list(files);
        FileMetadata.sortSmallestFirstDirsFirst(metas);
        for(int i=0; i<files.size(); i++) { files.set(i, metas.get(i).file); }
    }

    public static void sortBy(File[] files, String byWhat) {
        switch(byWhat) {
            case BY_NAME_REVERSED: reverseAlphabeticallyDirsFirst(files); break;
            case BY_SIZE:          smallestFirstDirsFirst(files); break;
            case BY_SIZE_REVERSED: largestFirstDirsFirst(files); break;
            case BY_DATE:          oldestFirstDirsFirst(files); break;
            case BY_DATE_REVERSED: newestFirstDirsFirst(files); break;
            default:               alphabeticallyDirsFirst(files); break;
        }
    }

    public static void sortBy(List<File> files, String byWhat) {
        switch(byWhat) {
            case BY_NAME_REVERSED: reverseAlphabeticallyDirsFirst(files); break;
            case BY_SIZE:          smallestFirstDirsFirst(files); break;
            case BY_SIZE_REVERSED: largestFirstDirsFirst(files); break;
            case BY_DATE:          oldestFirstDirsFirst(files); break;
            case BY_DATE_REVERSED: newestFirstDirsFirst(files); break;
            default:               alphabeticallyDirsFirst(files); break;
        }
    }
}