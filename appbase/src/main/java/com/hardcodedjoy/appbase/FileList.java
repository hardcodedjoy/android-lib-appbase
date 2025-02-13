/*

MIT License

Copyright © 2025 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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
import java.util.Arrays;
import java.util.List;

public class FileList {

    private final ArrayList<File> files;

    public FileList() { files = new ArrayList<>(); }
    public FileList(ArrayList<File> files) { this.files = files; }
    public FileList(File[] fileArray) {
        files = new ArrayList<>();
        files.addAll(Arrays.asList(fileArray));
    }

    public List<File> getFiles() { return files; }

    public void sortAlphabeticalDirsFirst() { FileSort.alphabeticalDirsFirst(files); }

    public boolean contains(File what) {
        if(what == null) { return false; }
        String path = what.getAbsolutePath();
        for(File file : files) {
            if(file.getAbsolutePath().equals(path)) { return true; }
        }
        return false;
    }

    public void add(File file) { if(!contains(file)) { files.add(file); } }
    public void remove(File file) { files.remove(indexOf(file)); }
    public int size() { return files.size(); }
    public File get(int index) { return files.get(index); }

    public int indexOf(File file) {
        int n = files.size();
        if(n == 0) { return -1; }
        for(int i=0; i<n; i++) {
            if(isSame(file, files.get(i))) { return i; }
        }
        return -1;
    }

    static private boolean isSame(File a, File b) {
        if(a == null && b == null) { return true; }
        if(a == null) { return false; }
        if(b == null) { return false; }

        return a.getAbsolutePath().equals(b.getAbsolutePath());
    }
}