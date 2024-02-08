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

package com.hardcodedjoy.appbase.gui;

import com.hardcodedjoy.appbase.FileFilter;
import com.hardcodedjoy.appbase.R;

import java.io.File;

public class FileTypeIcons {

    static public int get(File file) {

        if(file == null) { return R.drawable.ic_file_type_file_3; }
        if(file.isDirectory()) { return R.drawable.ic_file_type_folder_3; }
        if(FileFilter.isText(file)) { return R.drawable.ic_file_type_text_3; }
        if(FileFilter.isImage(file)) { return R.drawable.ic_file_type_image_3; }
        if(FileFilter.isArchive(file)) { return R.drawable.ic_file_type_archive_3; }
        if(FileFilter.isAudio(file)) { return R.drawable.ic_file_type_audio_3; }
        if(FileFilter.isVideo(file)) { return R.drawable.ic_file_type_video_3; }
        if(FileFilter.isPdf(file)) { return R.drawable.ic_file_type_pdf_3; }
        return R.drawable.ic_file_type_file_3;
    }
}
