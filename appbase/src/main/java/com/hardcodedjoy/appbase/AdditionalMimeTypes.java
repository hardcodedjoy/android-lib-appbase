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

public class AdditionalMimeTypes {

    static public String getMimeTypeFromExtension(String extension) {
        if(extension == null) { return null; }
        if(extension.length() == 0) { return null; }
        switch (extension) {
            case "aac": return "audio/aac";
            case "aar": return "application/android-library";
            case "abw": return "application/x-abiword";
            case "arc": return "application/x-freearc";
            case "asm": return "text/x-asm";
            case "avif": return "image/avif";
            case "avi": return "video/x-msvideo";
            case "azw": return "application/vnd.amazon.ebook";
            case "bin": return "application/octet-stream";
            case "blend": return "application/x-blender";
            case "bmp": return "image/bmp";
            case "brd": return "application/brd+xml";
            case "bz": return "application/x-bzip";
            case "bz2": return "application/x-bzip2";
            case "cda": return "application/x-cdf";
            case "cfg": return "text/cfg";
            case "conf": return "text/conf";
            case "config": return "text/config";
            case "csh": return "application/x-csh";
            case "css": return "text/css";
            case "csv": return "text/csv";
            case "doc": return "application/msword";
            case "docx": return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "eot": return "application/vnd.ms-fontobject";
            case "epub": return "application/epub+zip";
            case "gcode": return "text/x-gcode";
            case "gz": return "application/gzip";
            case "gif": return "image/gif";
            case "hex": return "text/hex";
            case "htm": return "text/html";
            case "html": return "text/html";
            case "ico": return "image/vnd.microsoft.icon";
            case "ics": return "text/calendar";
            case "ini": return "text/ini";
            case "jar": return "application/java-archive";
            case "jpeg": return "image/jpeg";
            case "jpg": return "image/jpeg";
            case "js": return "text/javascript";
            case "json": return "application/json";
            case "jsonld": return "application/ld+json";
            case "kt": return "text/x-kotlin";
            case "m": return "text/x-matlab";
            case "mid": return "audio/midi";
            case "midi": return "audio/midi";
            case "mjs": return "text/javascript";
            case "mov": return "video/quicktime";
            case "mp3": return "audio/mpeg";
            case "mp4": return "video/mp4";
            case "mpeg": return "video/mpeg";
            case "mpkg": return "application/vnd.apple.installer+xml";
            case "odp": return "application/vnd.oasis.opendocument.presentation";
            case "ods": return "application/vnd.oasis.opendocument.spreadsheet";
            case "odt": return "application/vnd.oasis.opendocument.text";
            case "oga": return "audio/ogg";
            case "ogg": return "audio/ogg";
            case "ogv": return "video/ogg";
            case "ogx": return "application/ogg";
            case "opus": return "audio/opus";
            case "otf": return "font/otf";
            case "png": return "image/png";
            case "pdf": return "application/pdf";
            case "php": return "application/x-httpd-php";
            case "ppt": return "application/vnd.ms-powerpoint";
            case "pptx": return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "rar": return "application/vnd.rar";
            case "rtf": return "application/rtf";
            case "sch": return "application/sch+xml";
            case "sh": return "application/x-sh";
            case "svg": return "image/svg+xml";
            case "swf": return "application/x-shockwave-flash";
            case "tar": return "application/x-tar";
            case "tif": return "image/tiff";
            case "tiff": return "image/tiff";
            case "ts": return "video/mp2t";
            case "ttf": return "font/ttf";
            case "txt": return "text/plain";
            case "vsd": return "application/vnd.visio";
            case "wav": return "audio/wav";
            case "weba": return "audio/webm";
            case "webm": return "video/webm";
            case "webp": return "image/webp";
            case "woff": return "font/woff";
            case "woff2": return "font/woff2";
            case "xhtml": return "application/xhtml+xml";
            case "xls": return "application/vnd.ms-excel";
            case "xlsx": return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "xml": return "application/xml";
            case "xul": return "application/vnd.mozilla.xul+xml";
            case "zip": return "application/zip";
            case "3gp": return "video/3gpp";
            case "3g2": return "video/3gpp2";
            case "7z": return "application/x-7z-compressed";
            default: return null;
        }
    }

    static public String getExtensionFromMimeType(String mimeType) {
        if(mimeType == null) { return null; }
        if(mimeType.length() == 0) { return null; }
        switch(mimeType) {
            case "audio/aac": return "aac";
            case "application/android-library": return "aar";
            case "application/x-abiword": return "abw";
            case "application/x-freearc": return "arc";
            case "text/x-asm": return "asm";
            case "image/avif": return "avif";
            case "video/x-msvideo": return "avi";
            case "application/vnd.amazon.ebook": return "azw";
            case "application/octet-stream": return "bin";
            case "application/x-blender": return "blend";
            case "image/bmp": return "bmp";
            case "application/brd+xml": return "brd";
            case "application/x-bzip": return "bz";
            case "application/x-bzip2": return "bz2";
            case "application/x-cdf": return "cda";
            case "text/cfg": return "cfg";
            case "text/conf": return "conf";
            case "text/config": return "config";
            case "application/x-csh": return "csh";
            case "text/css": return "css";
            case "text/csv": return "csv";
            case "application/msword": return "doc";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document": return "docx";
            case "application/vnd.ms-fontobject": return "eot";
            case "application/epub+zip": return "epub";
            case "text/x-gcode": return "gcode";
            case "application/gzip": return "gz";
            case "image/gif": return "gif";
            case "text/hex": return "hex";
            // case "text/html": return "htm";
            case "text/html": return "html";
            case "image/vnd.microsoft.icon": return "ico";
            case "text/calendar": return "ics";
            case "text/ini": return "ini";
            case "application/java-archive": return "jar";
            // case "image/jpeg": return "jpeg";
            case "image/jpeg": return "jpg";
            case "text/javascript": return "js";
            case "application/json": return "json";
            case "application/ld+json": return "jsonld";
            case "text/x-kotlin": return "kt";
            case "text/x-matlab": return "m";
            case "audio/midi": return "mid";
            // case "audio/midi": return "midi"
            // case "text/javascript": return "mjs";
            case "video/quicktime": return "mov";
            case "audio/mpeg": return "mp3";
            case "video/mp4": return "mp4";
            case "video/mpeg": return "mpeg";
            case "application/vnd.apple.installer+xml": return "mpkg";
            case "application/vnd.oasis.opendocument.presentation": return "odp";
            case "application/vnd.oasis.opendocument.spreadsheet": return "ods";
            case "application/vnd.oasis.opendocument.text": return "odt";
            // case "audio/ogg": return "oga";
            case "audio/ogg": return "ogg";
            case "video/ogg": return "ogv";
            case "application/ogg": return "ogx";
            case "audio/opus": return "opus";
            case "font/otf": return "otf";
            case "image/png": return "png";
            case "application/pdf": return "pdf";
            case "application/x-httpd-php": return "php";
            case "application/vnd.ms-powerpoint": return "ppt";
            case "application/vnd.openxmlformats-officedocument.presentationml.presentation": return "pptx";
            case "application/vnd.rar": return "rar";
            case "application/rtf": return "rtf";
            case "application/sch+xml": return "sch";
            case "application/x-sh": return "sh";
            case "image/svg+xml": return "svg";
            case "application/x-shockwave-flash": return "swf";
            case "application/x-tar": return "tar";
            // case "image/tiff": return "tif";
            case "image/tiff": return "tiff";
            case "video/mp2t": return "ts";
            case "font/ttf": return "ttf";
            case "text/plain": return "txt";
            case "application/vnd.visio": return "vsd";
            case "audio/wav": return "wav";
            case "audio/webm": return "weba";
            case "video/webm": return "webm";
            case "image/webp": return "webp";
            case "font/woff": return "woff";
            case "font/woff2": return "woff2";
            case "application/xhtml+xml": return "xhtml";
            case "application/vnd.ms-excel": return "xls";
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet": return "xlsx";
            case "application/xml": return "xml";
            case "application/vnd.mozilla.xul+xml": return "xul";
            case "application/zip": return "zip";
            case "video/3gpp": return "3gp";
            case "video/3gpp2": return "3g2";
            case "application/x-7z-compressed": return "7z";
            default: return null;
        }
    }
}