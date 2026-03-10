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

public class Base64 {

    static public String encode(byte[] bytes) {
        byte[] output = android.util.Base64.encode(
                bytes, android.util.Base64.NO_WRAP);
        return StringUtil.fromBytesUTF8(output);
    }

    static public String[] encode(byte[][] bytes) {
        if(bytes == null) { return null; }
        int n = bytes.length;
        String[] output = new String[n];
        for(int i=0; i<n; i++) { output[i] = encode(bytes[i]); }
        return output;
    }

    static public String encode(String s) {
        byte[] input = StringUtil.getBytesUTF8(s);
        return encode(input);
    }

    static public String[] encode(String[] s) {
        byte[][] input = StringUtil.getBytesUTF8(s);
        return encode(input);
    }

    static public byte[] decodeToBytes(String s) {
        byte[] input = StringUtil.getBytesUTF8(s);
        return android.util.Base64.decode(
                input, android.util.Base64.NO_WRAP);
    }

    static public byte[][] decodeToBytes(String[] s) {
        if(s == null) { return null; }
        int n = s.length;
        byte[][] output = new byte[n][];
        for(int i=0; i<n; i++) { output[i] = decodeToBytes(s[i]); }
        return output;
    }

    static public String decode(String s) {
        byte[] output = decodeToBytes(s);
        return StringUtil.fromBytesUTF8(output);
    }

    static public String[] decode(String[] s) {
        byte[][] output = decodeToBytes(s);
        return StringUtil.fromBytesUTF8(output);
    }
}