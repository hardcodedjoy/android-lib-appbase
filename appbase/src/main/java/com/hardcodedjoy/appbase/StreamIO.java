/*

MIT License

Copyright © 2026 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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

import com.hardcodedjoy.appbase.listeners.LongListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("unused")
public class StreamIO {

    public static long copyStream(InputStream is, OutputStream os) throws IOException, InterruptedException {
        byte[] buffer = new byte[65536];
        long totalBytesCopied = 0;

        int read = is.read(buffer, 0, buffer.length);
        while(read != -1) {
            os.write(buffer, 0, read);
            totalBytesCopied += read;
            read = is.read(buffer, 0, buffer.length);
            if (Thread.interrupted()) { throw new InterruptedException(); }
        }
        return totalBytesCopied;
    }

    public static long copyStream(InputStream is, OutputStream os, long len) throws IOException, InterruptedException {
        if(len <= 0) { return 0; }

        byte[] buffer = new byte[65536];
        long totalBytesCopied = 0;


        while(len > 0) {
            int maxToRead = (len < buffer.length) ? (int) len : buffer.length;
            int read = is.read(buffer, 0, maxToRead);
            if(read == -1) { break; }

            os.write(buffer, 0, read);
            totalBytesCopied += read;
            len -= read;

            if(Thread.interrupted()) { throw new InterruptedException(); }
        }
        return totalBytesCopied;
    }

    public static void copyStream(InputStream is,
                                  OutputStream os,
                                  int blockLen,
                                  AtomicBoolean cancel,
                                  LongListener progressBytes) throws IOException, InterruptedException {
        long bytesCopied = 0;
        byte[] buffer = new byte[blockLen];
        int read = is.read(buffer, 0, buffer.length);
        while(read != -1) {
            if(cancel.get()) { break; }
            os.write(buffer, 0, read);
            bytesCopied += read;
            progressBytes.onChanged(bytesCopied);
            read = is.read(buffer, 0, buffer.length);
            if (Thread.interrupted()) { throw new InterruptedException(); }
        }
    }

    public static void write8(long val, OutputStream os) throws IOException {
        os.write( (int)(val & 0xFF) );
    }

    public static void write16LE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[2];
        int i = 0;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte first
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    public static void write24LE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[3];
        int i = 0;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte first
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    public static void write32LE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[4];
        int i = 0;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte first
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i++] = (byte) (val & 0xFF); val = val >> 8;
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    public static void write64LE(long val, OutputStream os) throws IOException {
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

    public static void write16BE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[2];
        int i = ba.length-1;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte last
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    public static void write24BE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[3];
        int i = ba.length-1;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte last
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    public static void write32BE(long val, OutputStream os) throws IOException {
        byte[] ba = new byte[4];
        int i = ba.length-1;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8; // Least significant Byte last
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i--] = (byte) (val & 0xFF); val = val >> 8;
        ba[i  ] = (byte) (val & 0xFF);
        os.write(ba);
    }

    public static void write64BE(long val, OutputStream os) throws IOException {
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

    public static int read8(InputStream is) throws IOException {
        return is.read();
    }

    public static int read16BE(InputStream is) throws IOException {
        byte[] ba = new byte[2];
        int read = is.read(ba);
        if(read != ba.length) { return -1; }
        int res = 0;
        int i = 0;
        res = (res     ) + (ba[i++] & 0xFF); // Most significant Byte first
        res = (res << 8) + (ba[i  ] & 0xFF);
        return res;
    }

    public static int read24BE(InputStream is) throws IOException {
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

    public static long read32BE(InputStream is) throws IOException {
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

    public static long read64BE(InputStream is) throws IOException {
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

    public static int read16LE(InputStream is) throws IOException {
        byte[] ba = new byte[2];
        int read = is.read(ba);
        if(read != ba.length) { return -1; }
        int res = 0;
        int i = ba.length-1;
        res = (res     ) + (ba[i--] & 0xFF); // Most significant Byte last
        res = (res << 8) + (ba[i  ] & 0xFF);
        return res;
    }

    public static int read24LE(InputStream is) throws IOException {
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

    public static long read32LE(InputStream is) throws IOException {
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

    public static long read64LE(InputStream is) throws IOException {
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
