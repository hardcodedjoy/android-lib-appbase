/*

MIT License

Copyright © 2024 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unused")
public class FileSort {

    static public void newestFirstDirsFirst(File[] files) {
        Arrays.sort(files, (o1, o2) -> {
            if(o1.isDirectory() && (!o2.isDirectory())) { return -1; }
            if((!o1.isDirectory()) && o2.isDirectory()) { return 1; }
            long a = o1.lastModified();
            long b = o2.lastModified();
            return Long.compare(b, a);
        });
    }

    static public void newestFirstDirsFirst(List<File> files) {
        Collections.sort(files, (o1, o2) -> {
            if(o1.isDirectory() && (!o2.isDirectory())) { return -1; }
            if((!o1.isDirectory()) && o2.isDirectory()) { return 1; }
            long a = o1.lastModified();
            long b = o2.lastModified();
            return Long.compare(b, a);
        });
    }

    static public void alphabeticalDirsFirst(File[] files) { // case-insensitive
        Arrays.sort(files, (o1, o2) -> {
            if(o1.isDirectory() && (!o2.isDirectory())) { return -1; }
            if((!o1.isDirectory()) && o2.isDirectory()) { return 1; }
            String fn1 = o1.getName().toLowerCase(Locale.US);
            String fn2 = o2.getName().toLowerCase(Locale.US);
            return fn1.compareTo(fn2);
        });
    }

    static public void alphabeticalDirsFirst(List<File> files) { // case-insensitive
        Collections.sort(files, (o1, o2) -> {
            if(o1.isDirectory() && (!o2.isDirectory())) { return -1; }
            if((!o1.isDirectory()) && o2.isDirectory()) { return 1; }
            String fn1 = o1.getName().toLowerCase(Locale.US);
            String fn2 = o2.getName().toLowerCase(Locale.US);
            return fn1.compareTo(fn2);
        });
    }
}