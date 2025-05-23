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

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;

import com.hardcodedjoy.appbase.activity.ActivityResultTask;
import com.hardcodedjoy.appbase.activity.ActivityUtil;
import com.hardcodedjoy.appbase.handlers.UriArrayHandler;
import com.hardcodedjoy.appbase.handlers.UriHandler;

@SuppressWarnings("unused")
public class FileChooser {

    private final Activity activity;

    static private final String EXTRA_INITIAL_URI = "android.provider.extra.INITIAL_URI";
    static public final String URI_STRING_DOWNLOAD_DIR =
            "content://com.android.externalstorage.documents/document/primary:Download";

    private Uri initialUri;

    public FileChooser(Activity activity) { this.activity = activity; }

    public void setInitialUri(Uri initialUri) { this.initialUri = initialUri; }

    public void open(String mimeType, UriHandler uriHandler) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType(mimeType);
        if(initialUri != null) { intent.putExtra(EXTRA_INITIAL_URI, initialUri); }
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent = Intent.createChooser(intent, activity.getString(R.string.open_file));

        ActivityResultTask art = new ActivityResultTask();
        art.setRunnable(() -> {
            Uri uri = getFirstUri(art.getData());
            if(uri == null) { return; }
            uriHandler.handle(uri);
        });
        ActivityUtil.startActivityForResult(activity, intent, art);
    }

    public void openRW(String mimeType, UriHandler uriHandler) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType(mimeType);
        if(initialUri != null) { intent.putExtra(EXTRA_INITIAL_URI, initialUri); }
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent = Intent.createChooser(intent, activity.getString(R.string.open_file));

        ActivityResultTask art = new ActivityResultTask();
        art.setRunnable(() -> {
            Uri uri = getFirstUri(art.getData());
            if(uri == null) { return; }
            uriHandler.handle(uri);
        });
        ActivityUtil.startActivityForResult(activity, intent, art);
    }

    public void chooseFolder(UriHandler uriHandler) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        if(initialUri != null) { intent.putExtra(EXTRA_INITIAL_URI, initialUri); }
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent = Intent.createChooser(intent, activity.getString(R.string.select_folder));

        ActivityResultTask art = new ActivityResultTask();
        art.setRunnable(() -> {
            Uri uri = getFirstUri(art.getData());
            if(uri == null) { return; }
            uriHandler.handle(uri);
        });
        ActivityUtil.startActivityForResult(activity, intent, art);
    }

    public void chooseFolderRW(UriHandler uriHandler) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        if(initialUri != null) { intent.putExtra(EXTRA_INITIAL_URI, initialUri); }
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent = Intent.createChooser(intent, activity.getString(R.string.select_folder));

        ActivityResultTask art = new ActivityResultTask();
        art.setRunnable(() -> {
            Uri uri = getFirstUri(art.getData());
            if(uri == null) { return; }
            uriHandler.handle(uri);
        });
        ActivityUtil.startActivityForResult(activity, intent, art);
    }

    public void saveAs(String mimeType, String defaultFileName,
                       UriHandler uriHandler) {

        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.setType(mimeType);
        if(initialUri != null) { intent.putExtra(EXTRA_INITIAL_URI, initialUri); }
        intent.putExtra(Intent.EXTRA_TITLE, defaultFileName);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent = Intent.createChooser(intent, activity.getString(R.string.save_file_as));

        ActivityResultTask art = new ActivityResultTask();
        art.setRunnable(() -> {
            Uri uri = getFirstUri(art.getData());
            if(uri == null) { return; }
            uriHandler.handle(uri);
        });
        ActivityUtil.startActivityForResult(activity, intent, art);
    }

    public void openMultiple(String mimeType, UriArrayHandler uriArrayHandler) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType(mimeType);
        if(initialUri != null) { intent.putExtra(EXTRA_INITIAL_URI, initialUri); }
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent = Intent.createChooser(intent, activity.getString(R.string.open_file_s));

        ActivityResultTask art = new ActivityResultTask();
        art.setRunnable(() -> uriArrayHandler.handle(getAllUris(art.getData())));
        ActivityUtil.startActivityForResult(activity, intent, art);
    }

    public void openMultipleRW(String mimeType, UriArrayHandler uriArrayHandler) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType(mimeType);
        if(initialUri != null) { intent.putExtra(EXTRA_INITIAL_URI, initialUri); }
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent = Intent.createChooser(intent, activity.getString(R.string.open_file_s));

        ActivityResultTask art = new ActivityResultTask();
        art.setRunnable(() -> uriArrayHandler.handle(getAllUris(art.getData())));
        ActivityUtil.startActivityForResult(activity, intent, art);
    }

    public void open(UriHandler uriHandler) {
        open("*/*", uriHandler);
    }

    public void openRW(UriHandler uriHandler) {
        openRW("*/*", uriHandler);
    }

    public void saveAs(String defaultFileName, UriHandler uriHandler) {
        saveAs("*/*", defaultFileName, uriHandler);
    }

    public void openMultiple(UriArrayHandler uriArrayHandler) {
        openMultiple("*/*", uriArrayHandler);
    }

    public void openMultipleRW(UriArrayHandler uriArrayHandler) {
        openMultipleRW("*/*", uriArrayHandler);
    }

    static private Uri getFirstUri(Intent data) {
        if(data == null) { return null; }
        // clipData has priority
        // if contains uris -> return first from there:
        ClipData clipData = data.getClipData();
        if(clipData != null) {
            if(clipData.getItemCount() > 0) {
                return clipData.getItemAt(0).getUri();
            }
        }
        // else -> fallback to data.getData()
        return data.getData();
    }

    static private Uri[] getAllUris(Intent data) {
        if(data == null) { return new Uri[0]; }
        // clipData has priority
        ClipData clipData = data.getClipData();
        if(clipData != null) {
            int n = clipData.getItemCount();
            if(n > 0) {
                Uri[] uris = new Uri[n];
                for(int i=0; i<n; i++) { uris[i] = clipData.getItemAt(i).getUri(); }
                return uris;
            }
        }
        // else -> fallback to data.getData()
        Uri uri = data.getData();
        if(uri == null) { return new Uri[0]; }
        return new Uri[] { uri };
    }
}