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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@SuppressWarnings("unused")
public class ZipIO {

    static public void writeFile(ZipOutputStream zos, String fileName, FileInputStream fis, String dirPath) throws IOException, InterruptedException {
        if(dirPath != null) { fileName = dirPath + "/" + fileName; }
        ZipEntry e = new ZipEntry(fileName);
        zos.putNextEntry(e);
        StreamIO.copyStream(fis, zos);
        fis.close();
        zos.closeEntry();
    }

    static public void writeFile(ZipOutputStream zos, File file, String dirPath) throws IOException, InterruptedException {
        FileInputStream fis = new FileInputStream(file);
        String fileName = file.getName();
        writeFile(zos, fileName, fis, dirPath);
    }

    static public void writeFile(ZipOutputStream zos, File file) throws IOException, InterruptedException {
        writeFile(zos, file, null);
    }

    static public void writeFile(ZipOutputStream zos, String fileName, FileInputStream fis) throws IOException, InterruptedException {
        writeFile(zos, fileName, fis, null);
    }

    static public void writeFolder(ZipOutputStream zos, File file, String dirPath) throws IOException, InterruptedException {
        File[] files = file.listFiles();
        if(files == null) { files = new File[0]; }

        if(dirPath == null) {
            dirPath = file.getName();
        } else {
            dirPath = dirPath + "/" + file.getName();
        }

        // add the directory:
        ZipEntry e = new ZipEntry(dirPath + "/");
        zos.putNextEntry(e);
        zos.closeEntry();

        // add the files, if any:
        for(File childFile : files) { write(zos, childFile, dirPath); }
    }

    static public void writeFolder(ZipOutputStream zos, File file) throws IOException, InterruptedException {
        writeFolder(zos, file, null);
    }

    static public void write(ZipOutputStream zos, File file, String dirPath) throws IOException, InterruptedException {
        if(file.isDirectory()) {
            writeFolder(zos, file, dirPath);
        } else {
            writeFile(zos, file, dirPath);
        }
    }

    static public void write(ZipOutputStream zos, File file) throws IOException, InterruptedException {
        write(zos, file, null);
    }


    static public RamFile[] getFiles(ZipInputStream zis) throws IOException, InterruptedException {

        Vector<RamFile> vec = new Vector<>();

        ZipEntry e;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        while(true) {
            e = zis.getNextEntry();
            if(e == null) { break; }
            // else:

            if(e.isDirectory()) { zis.closeEntry(); continue; }

            // else:
            baos.reset();
            StreamIO.copyStream(zis, baos);
            vec.add(new RamFile(e.getName(), baos.toByteArray()));
            zis.closeEntry();
        }

        RamFile[] files = new RamFile[vec.size()];
        for(int i=0; i<files.length; i++) {
            files[i] = vec.elementAt(i);
        }

        return files;
    }

    static public int countEntries(ZipInputStream zis) throws IOException {
        ZipEntry e;
        int res = 0;
        while (true) {
            e = zis.getNextEntry();
            if(e == null) { return res; }
            zis.closeEntry();
            res++;
        }
    }
}