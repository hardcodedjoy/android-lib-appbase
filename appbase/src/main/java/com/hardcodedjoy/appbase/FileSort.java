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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unused")
public class FileSort {

    static private Comparator<File> getComparatorNewestFirstDirsFirst() {
        return (o1, o2) -> {
            if(o1.isDirectory() && (!o2.isDirectory())) { return -1; }
            if((!o1.isDirectory()) && o2.isDirectory()) { return 1; }
            long a = o1.lastModified();
            long b = o2.lastModified();
            return Long.compare(b, a);
        };
    }

    static private Comparator<File> getComparatorOldestFirstDirsFirst() {
        return (o1, o2) -> {
            if(o1.isDirectory() && (!o2.isDirectory())) { return -1; }
            if((!o1.isDirectory()) && o2.isDirectory()) { return 1; }
            long a = o1.lastModified();
            long b = o2.lastModified();
            return Long.compare(a, b);
        };
    }

    static private Comparator<File> getComparatorAlphabeticallyDirsFirst() {
        return (o1, o2) -> {
            if(o1.isDirectory() && (!o2.isDirectory())) { return -1; }
            if((!o1.isDirectory()) && o2.isDirectory()) { return 1; }
            String fn1 = o1.getName().toLowerCase(Locale.US);
            String fn2 = o2.getName().toLowerCase(Locale.US);
            return fn1.compareTo(fn2);
        };
    }

    static private Comparator<File> getComparatorReverseAlphabeticallyDirsFirst() {
        return (o1, o2) -> {
            if(o1.isDirectory() && (!o2.isDirectory())) { return -1; }
            if((!o1.isDirectory()) && o2.isDirectory()) { return 1; }
            String fn1 = o1.getName().toLowerCase(Locale.US);
            String fn2 = o2.getName().toLowerCase(Locale.US);
            return fn2.compareTo(fn1);
        };
    }

    static private Comparator<File> getComparatorLargestFirstDirsFirst() {
        return (o1, o2) -> {
            // dirs first
            if(o1.isDirectory() && (!o2.isDirectory())) { return -1; }
            if((!o1.isDirectory()) && o2.isDirectory()) { return 1; }
            // if both are directories: sort alphabetically, ignoring case
            if (o1.isDirectory() && o2.isDirectory()) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
            return Long.compare(o2.length(), o1.length());
        };
    }

    static private Comparator<File> getComparatorSmallestFirstDirsFirst() {
        return (o1, o2) -> {
            // dirs first
            if(o1.isDirectory() && (!o2.isDirectory())) { return -1; }
            if((!o1.isDirectory()) && o2.isDirectory()) { return 1; }
            // if both are directories: sort alphabetically, ignoring case
            if (o1.isDirectory() && o2.isDirectory()) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
            return Long.compare(o1.length(), o2.length());
        };
    }

    static public void newestFirstDirsFirst(File[] files) {
        Arrays.sort(files, getComparatorNewestFirstDirsFirst());
    }

    static public void newestFirstDirsFirst(List<File> files) {
        Collections.sort(files, getComparatorNewestFirstDirsFirst());
    }

    static public void oldestFirstDirsFirst(File[] files) {
        Arrays.sort(files, getComparatorOldestFirstDirsFirst());
    }

    static public void oldestFirstDirsFirst(List<File> files) {
        Collections.sort(files, getComparatorOldestFirstDirsFirst());
    }

    static public void alphabeticallyDirsFirst(File[] files) {
        // case-insensitive
        Arrays.sort(files, getComparatorAlphabeticallyDirsFirst());
    }

    static public void alphabeticallyDirsFirst(List<File> files) {
        // case-insensitive
        Collections.sort(files, getComparatorAlphabeticallyDirsFirst());
    }

    static public void reverseAlphabeticallyDirsFirst(File[] files) {
        // case-insensitive
        Arrays.sort(files, getComparatorReverseAlphabeticallyDirsFirst());
    }

    static public void reverseAlphabeticallyDirsFirst(List<File> files) {
        // case-insensitive
        Collections.sort(files, getComparatorReverseAlphabeticallyDirsFirst());
    }

    static public void largestFirstDirsFirst(File[] files) {
        Arrays.sort(files, getComparatorLargestFirstDirsFirst());
    }

    static public void largestFirstDirsFirst(List<File> files) {
        Collections.sort(files, getComparatorLargestFirstDirsFirst());
    }

    static public void smallestFirstDirsFirst(File[] files) {
        Arrays.sort(files, getComparatorSmallestFirstDirsFirst());
    }

    static public void smallestFirstDirsFirst(List<File> files) {
        Collections.sort(files, getComparatorSmallestFirstDirsFirst());
    }
}