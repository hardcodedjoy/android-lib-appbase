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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("unused")
public class StreamIO {

    static public void copyStream(InputStream is, OutputStream os) throws IOException, InterruptedException {
        byte[] buffer = new byte[65536];
        int read = is.read(buffer, 0, buffer.length);
        while (read != -1) {
            os.write(buffer, 0, read);
            read = is.read(buffer, 0, buffer.length);
            if (Thread.interrupted()) { throw new InterruptedException(); }
        }
    }

    static public void copyStream(InputStream is, OutputStream os, long len) throws IOException, InterruptedException {
        byte[] buffer = new byte[65536];
        int read = is.read(buffer, 0, buffer.length < len ? buffer.length : (int)len);
        len -= read;
        while(read != -1) {
            os.write(buffer, 0, read);
            if(len <= 0) { break; }
            read = is.read(buffer, 0, buffer.length < len ? buffer.length : (int)len);
            len -= read;
            if (Thread.interrupted()) { throw new InterruptedException(); }
        }
    }

    static public void write8(long val, OutputStream os) throws IOException {
        os.write( (int)(val & 0xFF) );
    }

    static public void write16LE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[2];
        int i = 0;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte first
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    static public void write24LE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[3];
        int i = 0;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte first
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    static public void write32LE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[4];
        int i = 0;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte first
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    static public void write64LE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[8];
        int i = 0;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte first
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    static public void write16BE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[2];
        int i = ba.length-1;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte last
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    static public void write24BE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[3];
        int i = ba.length-1;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte last
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    static public void write32BE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[4];
        int i = ba.length-1;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte last
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    static public void write64BE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[8];
        int i = ba.length-1;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte last
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    static public int read8(InputStream is) throws IOException {
        return is.read();
    }

    static public int read16BE(InputStream is) throws IOException {
        byte[] ba = new byte[2];
        int read = is.read(ba);
        if(read != ba.length) { return -1; }
        int res = 0;
        int i = 0;
        res = (res     ) + (ba[i++] & 0xFF); // Most significant Byte first
        res = (res << 8) + (ba[i  ] & 0xFF);
        return res;
    }

    static public int read24BE(InputStream is) throws IOException {
        byte[] ba = new byte[3];
        int read = is.read(ba);
        if(read != ba.length) { return -1; }
        int res = 0;
        int i = 0;
        res = (res     ) + (ba[i++] & 0xFF); // Most significant Byte first
        res = (res << 8) + (ba[i++] & 0xFF);
        res = (res << 8) + (ba[i  ] & 0xFF);
        return res;
    }

    static public long read32BE(InputStream is) throws IOException {
        byte[] ba = new byte[4];
        int read = is.read(ba);
        if(read != ba.length) { return -1; }
        long res = 0;
        int i = 0;
        res = (res     ) + (ba[i++] & 0xFF); // Most significant Byte first
        res = (res << 8) + (ba[i++] & 0xFF);
        res = (res << 8) + (ba[i++] & 0xFF);
        res = (res << 8) + (ba[i  ] & 0xFF);
        return res;
    }

    static public long read64BE(InputStream is) throws IOException {
        byte[] ba = new byte[8];
        int read = is.read(ba);
        if(read != ba.length) { return -1; }
        long res = 0;
        int i = 0;
        res = (res     ) + (ba[i++] & 0xFF); // Most significant Byte first
        res = (res << 8) + (ba[i++] & 0xFF);
        res = (res << 8) + (ba[i++] & 0xFF);
        res = (res << 8) + (ba[i++] & 0xFF);
        res = (res << 8) + (ba[i++] & 0xFF);
        res = (res << 8) + (ba[i++] & 0xFF);
        res = (res << 8) + (ba[i++] & 0xFF);
        res = (res << 8) + (ba[i  ] & 0xFF);
        return res;
    }

    static public int read16LE(InputStream is) throws IOException {
        byte[] ba = new byte[2];
        int read = is.read(ba);
        if(read != ba.length) { return -1; }
        int res = 0;
        int i = ba.length-1;
        res = (res     ) + (ba[i--] & 0xFF); // Most significant Byte last
        res = (res << 8) + (ba[i  ] & 0xFF);
        return res;
    }

    static public int read24LE(InputStream is) throws IOException {
        byte[] ba = new byte[3];
        int read = is.read(ba);
        if(read != ba.length) { return -1; }
        int res = 0;
        int i = ba.length-1;
        res = (res     ) + (ba[i--] & 0xFF); // Most significant Byte last
        res = (res << 8) + (ba[i--] & 0xFF);
        res = (res << 8) + (ba[i  ] & 0xFF);
        return res;
    }

    static public long read32LE(InputStream is) throws IOException {
        byte[] ba = new byte[4];
        int read = is.read(ba);
        if(read != ba.length) { return -1; }
        long res = 0;
        int i = ba.length-1;
        res = (res     ) + (ba[i--] & 0xFF); // Most significant Byte last
        res = (res << 8) + (ba[i--] & 0xFF);
        res = (res << 8) + (ba[i--] & 0xFF);
        res = (res << 8) + (ba[i  ] & 0xFF);
        return res;
    }

    static public long read64LE(InputStream is) throws IOException {
        byte[] ba = new byte[8];
        int read = is.read(ba);
        if(read != ba.length) { return -1; }
        long res = 0;
        int i = ba.length-1;
        res = (res     ) + (ba[i--] & 0xFF); // Most significant Byte last
        res = (res << 8) + (ba[i--] & 0xFF);
        res = (res << 8) + (ba[i--] & 0xFF);
        res = (res << 8) + (ba[i--] & 0xFF);
        res = (res << 8) + (ba[i--] & 0xFF);
        res = (res << 8) + (ba[i--] & 0xFF);
        res = (res << 8) + (ba[i--] & 0xFF);
        res = (res << 8) + (ba[i  ] & 0xFF);
        return res;
    }
}
