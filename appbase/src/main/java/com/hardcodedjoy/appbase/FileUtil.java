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
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class FileUtil {

    @SuppressLint("StaticFieldLeak")
    static private Activity activity;

    static public void setActivity(Activity activity) { FileUtil.activity = activity; }

    static public void setFileContent(Uri uri, byte[] ba) {
        try {
            OutputStream os = activity.getContentResolver().openOutputStream(uri, "wt");
            os.write(ba);
            os.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    static public void setFileContent(Uri uri, String content) {
        byte[] ba;
        try {
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

    static public void setFileContent(File file, String content) {
        setFileContent(Uri.fromFile(file), content);
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
            return new String(ba, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return "";
        }
    }

    static public byte[] getFileContent(File file) {
        return getFileContent(Uri.fromFile(file));
    }

    static public String getFileContentAsString(File file) {
        return getFileContentAsString(Uri.fromFile(file));
    }

    static public String getFileName(Uri uri) {
        String result = getFileNameFromContentURI(uri);
        if(result == null) { result = getFileNameFromFileURI(uri); }
        return result;
    }

    static private String getFileNameFromContentURI(Uri uri) {
        if(!uri.getScheme().equals("content")) { return null; }
        Cursor cursor = activity.getContentResolver().query(uri,
                null, null, null, null);
        if(cursor == null) { return null; }
        if(!cursor.moveToFirst()) { cursor.close(); return null; }
        int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        if(columnIndex == -1) { cursor.close(); return null; }
        String result = cursor.getString(columnIndex);
        cursor.close();
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
        if(ext.length() == 0) { return null; }
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
        File[] files = dir.listFiles();
        if(files == null) { files = new File[0]; }
        boolean deletedOK = true;
        for(File file : files) {
            if(file.isDirectory()) {
                deleteNonEmptyDir(file);
            } else {
                deletedOK = file.delete();
                if(!deletedOK) {
                    break;
                }
            }
        }
        if(deletedOK) { // all contents deleted
            // now delete this dir:
            deletedOK = dir.delete();
        }
        return deletedOK; // true if all deleted OK, false if (some) could not be deleted
    }
}