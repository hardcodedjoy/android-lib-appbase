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

import android.net.Uri;

import java.util.Vector;

@SuppressWarnings("unused")
public class FileNameCache {

    private Vector<Uri> uris;
    private Vector<String> fileNames;

    public FileNameCache() {
        this.uris = new Vector<>();
        this.fileNames = new Vector<>();
    }

    public void add(Uri uri) {
        if(uri == null) { return; }
        int n = uris.size();
        for(int i=0; i<n; i++) {
            if(uris.elementAt(i).equals(uri)) { return; } // already exists
        }
        uris.add(uri);
        fileNames.add(FileUtil.getFileName(uri));
    }

    public String get(Uri uri) {
        if(uri == null) { return null; }
        int n = uris.size();
        for(int i=0; i<n; i++) {
            if(uris.elementAt(i).equals(uri)) { return fileNames.elementAt(i); }
        }
        return null;
    }

    public void reset() {
        this.uris = new Vector<>();
        this.fileNames = new Vector<>();
    }
}