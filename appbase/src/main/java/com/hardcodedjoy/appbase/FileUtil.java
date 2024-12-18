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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unused")
public class FileUtil {

    @SuppressLint("StaticFieldLeak")
    static private Activity activity;

    static private long diskAvailableBytes;
    static private long diskTotalBytes;

    static public void setActivity(Activity activity) { FileUtil.activity = activity; }
    static public Activity getActivity() { return activity; }

    static public void setFileContent(Uri uri, byte[] ba, int offset, int len) {
        try {
            OutputStream os = activity.getContentResolver().openOutputStream(uri, "wt");
            //noinspection DataFlowIssue
            os.write(ba, offset, len);
            os.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    static public void setFileContent(Uri uri, byte[] ba) {
        setFileContent(uri, ba, 0, ba.length);
    }

    static public void setFileContent(Uri uri, String content) {
        byte[] ba;
        try {
            //noinspection CharsetObjectCanBeUsed
            ba = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return;
        }
        setFileContent(uri, ba);
    }

    static public void setFileContent(File file, byte[] ba) {
        setFileContent(Uri.fromFile(file), ba);
    }

    static public void setFileContent(File file, byte[] ba, int offset, int len) {
        setFileContent(Uri.fromFile(file), ba, offset, len);
    }

    static public void setFileContent(File file, String content) {
        setFileContent(Uri.fromFile(file), content);
    }

    static public int readFileContent(Uri uri, byte[] dest) {
        InputStream is;

        try {
            is = activity.getContentResolver().openInputStream(uri);
            if(is == null) { return 0; }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return 0;
        }

        byte[] block = new byte[1024];
        int destOffset = 0;
        int bytesRead;
        try {
            while((bytesRead = is.read(block)) != -1) {
                System.arraycopy(block, 0, dest, destOffset, bytesRead);
                destOffset += bytesRead;
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return 0;
        }

        return destOffset;
    }

    static public byte[] getFileContent(Uri uri) {
        // NOTE: intended for small files
        // this will get the InputStream
        // and read the entire stream to baos (to RAM)

        InputStream is;

        try {
            is = activity.getContentResolver().openInputStream(uri);
            if(is == null) { return new byte[0]; }
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return new byte[0];
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] block = new byte[1024];
        int bytesRead;
        try {
            while((bytesRead = is.read(block)) != -1) {
                baos.write(block, 0, bytesRead);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return new byte[0];
        }

        return baos.toByteArray();
    }

    static public String getFileContentAsString(Uri uri) {

        // NOTE: intended for small files
        // this will get the InputStream
        // and read the entire stream to baos (to RAM)

        byte[] ba = getFileContent(uri);
        try {
            //noinspection CharsetObjectCanBeUsed
            return new String(ba, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return "";
        }
    }

    static public int readFileContent(File file, byte[] dest) {
        return readFileContent(Uri.fromFile(file), dest);
    }

    static public byte[] getFileContent(File file) { return getFileContent(Uri.fromFile(file)); }

    static public String getFileContentAsString(File file) {
        return getFileContentAsString(Uri.fromFile(file));
    }

    static public String getFileName(Uri uri) {
        String result = getFileNameFromContentURI(uri);
        if(result == null) { result = getFileNameFromFileURI(uri); }
        return result;
    }

    static private String getFileNameFromContentURI(Uri uri) {
        if(!"content".equals(uri.getScheme())) { return null; }

        Cursor cursor = activity.getContentResolver().query(uri,
                null, null, null, null);
        if(cursor == null) { return null; }
        if(!cursor.moveToFirst()) { cursor.close(); return null; }
        int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        if(columnIndex == -1) { cursor.close(); return null; }
        String result = cursor.getString(columnIndex);
        cursor.close();

        String authority = uri.getAuthority();
        if("com.android.providers.media.documents".equals(authority) ||
                "media".equals(authority)) {
            // file managed by the Android MediaStore -> manually add file extension:

            String mimeType = activity.getContentResolver().getType(uri);
            if(mimeType == null) { return result; }

            // else:

            // first try from AdditionalMimeTypes
            // as MimeTypeMap.getSingleton() returns wrong file extension for some mimes

            String extensionLowerCase = AdditionalMimeTypes
                    .getExtensionFromMimeType(mimeType);
            if(extensionLowerCase == null) {
                extensionLowerCase = MimeTypeMap.getSingleton()
                        .getExtensionFromMimeType(mimeType);
            }
            if(extensionLowerCase != null && result != null) {
                // if did not have the extension:
                if(!result.toLowerCase(Locale.US).endsWith("." + extensionLowerCase)) {
                    result += "." + extensionLowerCase; // append it
                }
            }
        }
        return result;
    }

    static private String getFileNameFromFileURI(Uri uri) {
        String result = uri.getPath();
        if(result == null) { return null; }
        if(result.contains("/")) {
            result = result.substring(result.lastIndexOf("/") + 1);
        }
        return result;
    }

    static public String getMimeType(String extensionLowerCase) {
        if(extensionLowerCase == null) { return "*/*"; }
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extensionLowerCase);
        if(mimeType == null) {
            mimeType = AdditionalMimeTypes.getMimeTypeFromExtension(extensionLowerCase);
        }
        if(mimeType == null) { mimeType = "*/*"; }
        return mimeType;
    }

    static public String getExtension(String fileName) {
        if(fileName == null) { return null; }
        int a = fileName.lastIndexOf('.');
        if(a == -1) { return null; }
        String ext = fileName.substring(a+1);
        if(ext.isEmpty()) { return null; }
        return ext;
    }

    static public String getExtensionLowerCase(String fileName) {
        String ext = getExtension(fileName);
        if(ext == null) { return null; }
        return ext.toLowerCase(Locale.US);
    }

    static public String getNameBase(String fileName) {
        if(fileName == null) { return null; }
        String ext = getExtension(fileName);
        if(ext == null) { return fileName; }
        int len = fileName.length() - (ext.length()+1);
        if(len < 0) { return null; }
        return fileName.substring(0, len);
    }

    static public boolean deleteNonEmptyDir(File dir) {
        if(!emptyDir(dir)) { return false; } // could not empty
        return dir.delete(); // true if emptied OK and deleted OK
    }

    static public boolean emptyDir(File dir) {
        File[] files = dir.listFiles();
        if(files == null) { files = new File[0]; }

        for(File file : files) {
            boolean deletedOK = file.isDirectory() ? deleteNonEmptyDir(file) : file.delete();
            if(!deletedOK) { return false; }
        }
        return true; // success
    }

    @SuppressLint("UsableSpace")
    static synchronized public void updateDiskAvailableBytes(File dir) {
        if(dir == null) { diskAvailableBytes = 0; return; }
        // folder may not exist yet
        // but we need to know the disk available size, so we check its parent
        while(!dir.exists()) {
            dir = dir.getParentFile();
            if(dir == null) { diskAvailableBytes = 0; return; }
        }
        diskAvailableBytes = dir.getUsableSpace();
    }


    static synchronized public void updateDiskTotalBytes(File dir) {
        if(dir == null) { diskTotalBytes = 0; return; }
        // folder may not exist yet
        // but we need to know the disk available size, so we check its parent
        while(!dir.exists()) {
            dir = dir.getParentFile();
            if(dir == null) { diskTotalBytes = 0; return; }
        }
        diskTotalBytes = dir.getTotalSpace();
    }

    static synchronized public long getDiskAvailableBytes() { return diskAvailableBytes; }
    static synchronized public long getDiskTotalBytes() { return diskTotalBytes; }


    static public File[] filterFiles(File[] files, FileFilter filter) {
        if(files == null) { return new File[0]; }
        if(filter == null) { return files; }
        ArrayList<File> filteredFiles = new ArrayList<>();
        for (File f : files) { if (filter.accept(f)) { filteredFiles.add(f); } }
        return filteredFiles.toArray(new File[0]);
    }

    static public List<File> filterFiles(List<File> files, FileFilter filter) {
        if(files == null) { files = new ArrayList<>(); }
        if(filter == null) { return files; }
        ArrayList<File> filteredFiles = new ArrayList<>();
        for (File f : files) { if (filter.accept(f)) { filteredFiles.add(f); } }
        return filteredFiles;
    }

    static public File getFilesDir() { return activity.getFilesDir(); }
    static public String getFilesDirPath() { return getFilesDir().getAbsolutePath(); }

    static public String getIncrementedFileName(String orig, String current) {
        // orig -> "file.txt"
        // current -> "file_1.txt"
        // return -> "file_2.txt"

        // orig -> "file.txt"
        // current -> "file.txt"
        // return -> "file_1.txt"

        if(orig == null) { return null; }
        if(current == null) { return null; }

        if(orig.equals(current)) {
            String s = getNameBase(orig) + "_1";
            String ext = getExtension(orig);
            if(ext != null) { s += "." + ext; }
            return s;
        }

        // else:
        String s = getNameBase(orig);
        String currentBase = getNameBase(current);
        try {
            String num = currentBase.substring(s.length()+1);
            int n = Integer.parseInt(num);
            n++;
            s += "_" + n;
        } catch (Exception e) {
            return null;
        }

        String ext = getExtension(orig);
        if(ext != null) { s += "." + ext; }
        return s;
    }

    static public File newFileUniqueName(File dir, String fileName) {
        String base = getNameBase(fileName);
        String ext = getExtension(fileName);
        File file = new File(dir, fileName);
        if(file.exists()) {
            int n = 1;
            while(file.exists()) {
                fileName = base + " (" + n + ")";
                if(ext != null) { fileName += "." + ext; }
                file = new File(dir, fileName);
                n++;
            }
        }
        return file;
    }

    static public File[] listFilesSorted(File dir) {
        if(dir == null) { return new File[0]; }
        File[] files = dir.listFiles();
        if(files == null) { return new File[0]; }
        Arrays.sort(files);
        return files;
    }

    static public File[] listFilesOldestFirst(File dir) {
        if(dir == null) { return new File[0]; }
        File[] files = dir.listFiles();
        if(files == null) { return new File[0]; }
        //noinspection ComparatorCombinators
        Arrays.sort(files, (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
        return files;
    }

    static public File[] listFilesNewestFirst(File dir) {
        if(dir == null) { return new File[0]; }
        File[] files = dir.listFiles();
        if(files == null) { return new File[0]; }
        Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
        return files;
    }

    static public boolean mkEmptyDir(File dir) {
        if(dir.exists()) { return emptyDir(dir); }
        else { return dir.mkdir(); }
    }

    static public boolean canRead(Uri uri) {
        if(uri == null) { return false; }
        if("content".equals(uri.getScheme())) {
            Cursor cursor = activity.getContentResolver().query(uri,
                    null, null, null, null);
            if(cursor == null) { return false; }
            if(!cursor.moveToFirst()) { cursor.close(); return false; }
            // if reached here, it means the file or directory exists
            cursor.close();
            return true;
        } else if("file".equalsIgnoreCase(uri.getScheme())) {
            File file = new File(uri.getPath());
            if(!file.exists()) { return false; }
            return file.canRead();
        } else {
            return false;
        }
    }

    static public boolean canWrite(Uri uri) {
        if(uri == null) { return false; }
        if("content".equals(uri.getScheme())) {
            Cursor cursor = activity.getContentResolver().query(uri,
                    null, null, null, null);
            if(cursor == null) { return false; }
            if(!cursor.moveToFirst()) { cursor.close(); return false; }
            // if reached here, it means the file or directory exists
            int flagsIndex = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_FLAGS);
            if(flagsIndex == -1) { cursor.close(); return false; }
            int flags = cursor.getInt(flagsIndex);
            cursor.close();
            return (flags & DocumentsContract.Document.FLAG_SUPPORTS_WRITE) != 0;
        } else if("file".equalsIgnoreCase(uri.getScheme())) {
            File file = new File(uri.getPath());
            if(!file.exists()) { return false; }
            return file.canWrite();
        } else {
            return false;
        }
    }

    static public String getReadablePath(Uri uri) { // for displaying only, not for accessing the file
        if(uri == null) { return ""; }

        if("file".equalsIgnoreCase(uri.getScheme())) { return uri.getPath(); }

        String s = uri.toString();

        String prefix = "content://com.android.externalstorage.documents/tree/";

        int a = s.indexOf(prefix);
        if(a != -1) {
            s = s.substring(a + prefix.length());
            // remains only what is after prefix
        }

        if(s.startsWith("raw%3A")) {
            // remove "raw%3A" from raw URIs:
            s = s.substring(6);
        } else if(s.startsWith("msd%3A") || s.startsWith("msf%3A")) {
            // msd / msf says nothing to user, return file name instead:
            //if(isVolumeRoot()) { return "/storage/" + getName(); }
            return getFileName(uri);
        }

        s = s.replace("%3A", "/"); // show "/" instead of ":"
        s = Uri.decode(s);

        if(isDirectory(uri)) { if(!s.endsWith("/")) { s = s + "/"; } }

        //if(isVolumeRoot()) { return "/storage/" + s; }
        return s;
    }

    static public boolean isDirectory(Uri uri) {
        // Check if the URI is using the DocumentsProvider
        if (DocumentsContract.isDocumentUri(activity, uri)) {
            // Check the type of the URI
            try {
                String mimeType = activity.getContentResolver().getType(uri);
                return mimeType != null && mimeType.startsWith("vnd.android.document/directory");
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }

        // Fallback: query the content URI to see
        // if it provides information on children (only for directories)
        try (Cursor cursor = activity.getContentResolver()
                .query(uri, null, null, null, null)) {
            return cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return false;
    }
}