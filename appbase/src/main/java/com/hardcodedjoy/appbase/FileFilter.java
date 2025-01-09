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
import java.util.Locale;

abstract public class FileFilter implements java.io.FileFilter {

    abstract public boolean accept(File file);

    static public boolean isExt(String fileNameLowerCase, String[] ext) {
        if(fileNameLowerCase == null) { return false; }
        for(String e : ext) { if(fileNameLowerCase.endsWith(e)) { return true; } }
        return false;
    }

    static public boolean isImage(String fileNameLowerCase) {
        return isExt(fileNameLowerCase, new String[] {
                ".jpg", ".jpeg", ".png", ".bmp", ".gif", ".ico", ".svg", ".eps", ".heic",
                ".tif", ".tiff", ".webp", ".avif"
        });
    }

    static public boolean isAudio(String fileNameLowerCase) {
        return isExt(fileNameLowerCase, new String[] {
                ".wav", ".mp3", ".m4a", ".wma", ".ogg", ".oga", ".mid", ".midi", ".3gpp",
                ".aac", ".flac", ".opus", ".weba"
        });
    }

    static public boolean isVideo(String fileNameLowerCase) {
        return isExt(fileNameLowerCase, new String[] {
                ".mp4", ".avi", ".mov", ".flv", ".ogv", ".mkv", ".mpeg", ".3gp", ".webm",
                ".ts", ".3g2"
        });
    }

    static public boolean isArchive(String fileNameLowerCase) {
        return isExt(fileNameLowerCase, new String[] {
                ".zip", ".rar", ".cab", ".7z", ".tar", ".tar.gz", ".tar.xz", ".tar.bz2",
                ".jar", ".aar", ".apk", ".aab"
        });
    }

    static public boolean isPdf(String fileNameLowerCase) {
        return isExt(fileNameLowerCase, new String[] {".pdf"});
    }

    static public boolean isText(String fileNameLowerCase) {
        return isExt(fileNameLowerCase, new String[] {
                ".txt", ".md", ".ini", ".cfg", ".conf", ".config",
                ".sh",
                ".asm", ".c", ".h", ".cpp", ".ino", ".m",
                ".java", ".xml",
                ".htm", ".html", ".css",
                ".js", ".py", ".lua",
                ".csv", ".hex"
        });
    }

    static public boolean isFont(String fileNameLowerCase) {
        return isExt(fileNameLowerCase, new String[] {".otf", ".ttf"});
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    static public boolean isExt(File f, String[] ext) { return isExt(f.getName().toLowerCase(), ext); }
    static public boolean isImage(File f) { return isImage(f.getName().toLowerCase()); }
    static public boolean isAudio(File f) { return isAudio(f.getName().toLowerCase()); }
    static public boolean isVideo(File f) { return isVideo(f.getName().toLowerCase()); }
    static public boolean isArchive(File f) { return isArchive(f.getName().toLowerCase()); }
    static public boolean isPdf(File f) { return isPdf(f.getName().toLowerCase()); }
    static public boolean isText(File f) { return isText(f.getName().toLowerCase()); }
    static public boolean isFont(File f) { return isFont(f.getName().toLowerCase()); }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    static public FileFilter filterImages() {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {
                return (f.isDirectory() || isImage(f));
            }
        };
    }

    static public FileFilter filterImagesNoDirs() {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) { return false; }
                return isImage(f);
            }
        };
    }

    static public FileFilter filterDirsOnly() {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }
        };
    }

    static public FileFilter filterExtensions(final String[] extensions) {

        return new FileFilter() {
            @Override
            public boolean accept(File f) {

                if(f.isDirectory()) { return true; }

                String name = f.getName().toLowerCase(Locale.US);

                String e;
                for(String s : extensions) {
                    e = s.toLowerCase(Locale.US);
                    if(name.endsWith("." + e)) { return true; }
                }
                return false;
            }
        };
    }

    static public FileFilter filterExtensionsNoDirs(final String[] extensions) {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {

                if(f.isDirectory()) { return false; }

                String name = f.getName().toLowerCase(Locale.US);

                String e;
                for(String s : extensions) {
                    e = s.toLowerCase(Locale.US);
                    if(name.endsWith("." + e)) { return true; }
                }
                return false;
            }
        };
    }

    static public FileFilter filterExtension(String s) {
        return filterExtensions(new String[] {s});
    }

    static public FileFilter filterExtensionNoDirs(String s) {
        return filterExtensionsNoDirs(new String[] {s});
    }
}