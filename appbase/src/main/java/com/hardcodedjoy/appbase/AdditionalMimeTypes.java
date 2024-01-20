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
}