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

public class ByteUtil {
    static public final String BIG_ENDIAN = "be";
    static public final String LITTLE_ENDIAN = "le";

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    static public String[] getEndiannessKeys() {
        return new String[] {
                BIG_ENDIAN,
                LITTLE_ENDIAN
        };
    }

    static public String[] getEndiannessValues() {
        return new String[] {
                "Big-endian",
                "Little-endian"
        };
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    static public byte[] getBytesBE(char value) {
        byte[] out = new byte[2];
        out[1] = (byte) (value & 0xFF); value = (char) (value >> 8);
        out[0] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesBE(char[] value) {
        int n = value.length;
        byte[] out = new byte[2 * n];
        char v;
        for(int i=0, j=0; i<n; i++, j+=2) {
            v = value[i];
            out[j+1] = (byte) (v & 0xFF); v = (char) (v >> 8);
            out[j] = (byte) (v & 0xFF);
        }
        return out;
    }

    static public byte[] getBytesBE(short value) {
        byte[] out = new byte[2];
        out[1] = (byte) (value & 0xFF); value = (short) (value >> 8);
        out[0] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesBE(short[] value) {
        int n = value.length;
        byte[] out = new byte[2 * n];
        short v;
        for(int i=0, j=0; i<n; i++, j+=2) {
            v = value[i];
            out[j+1] = (byte) (v & 0xFF); v = (short) (v >> 8);
            out[j] = (byte) (v & 0xFF);
        }
        return out;
    }

    static public byte[] getBytesBE(int value) {
        byte[] out = new byte[4];
        out[3] = (byte) (value & 0xFF); value = value >> 8;
        out[2] = (byte) (value & 0xFF); value = value >> 8;
        out[1] = (byte) (value & 0xFF); value = value >> 8;
        out[0] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesBE(int[] value) {
        int n = value.length;
        byte[] out = new byte[4 * n];
        int v;
        for(int i=0, j=0; i<n; i++, j+=4) {
            v = value[i];
            out[j+3] = (byte) (v & 0xFF); v = v >> 8;
            out[j+2] = (byte) (v & 0xFF); v = v >> 8;
            out[j+1] = (byte) (v & 0xFF); v = v >> 8;
            out[j]   = (byte) (v & 0xFF);
        }
        return out;
    }

    static public byte[] getBytesBE(long value) {
        byte[] out = new byte[8];
        out[7] = (byte) (value & 0xFF); value = value >> 8;
        out[6] = (byte) (value & 0xFF); value = value >> 8;
        out[5] = (byte) (value & 0xFF); value = value >> 8;
        out[4] = (byte) (value & 0xFF); value = value >> 8;
        out[3] = (byte) (value & 0xFF); value = value >> 8;
        out[2] = (byte) (value & 0xFF); value = value >> 8;
        out[1] = (byte) (value & 0xFF); value = value >> 8;
        out[0] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesBE(long[] value) {
        int n = value.length;
        byte[] out = new byte[8 * n];
        long v;
        for(int i=0, j=0; i<n; i++, j+=8) {
            v = value[i];
            out[j+7] = (byte) (v & 0xFF); v = v >> 8;
            out[j+6] = (byte) (v & 0xFF); v = v >> 8;
            out[j+5] = (byte) (v & 0xFF); v = v >> 8;
            out[j+4] = (byte) (v & 0xFF); v = v >> 8;
            out[j+3] = (byte) (v & 0xFF); v = v >> 8;
            out[j+2] = (byte) (v & 0xFF); v = v >> 8;
            out[j+1] = (byte) (v & 0xFF); v = v >> 8;
            out[j]   = (byte) (v & 0xFF);
        }
        return out;
    }

    static public byte[] getBytesBE(float floatValue) {
        int value = Float.floatToRawIntBits(floatValue);
        byte[] out = new byte[4];
        out[3] = (byte) (value & 0xFF); value = value >> 8;
        out[2] = (byte) (value & 0xFF); value = value >> 8;
        out[1] = (byte) (value & 0xFF); value = value >> 8;
        out[0] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesBE(float[] value) {
        int n = value.length;
        byte[] out = new byte[4 * n];
        int v;
        for(int i=0, j=0; i<n; i++, j+=4) {
            v = Float.floatToRawIntBits(value[i]);
            out[j+3] = (byte) (v & 0xFF); v = v >> 8;
            out[j+2] = (byte) (v & 0xFF); v = v >> 8;
            out[j+1] = (byte) (v & 0xFF); v = v >> 8;
            out[j]   = (byte) (v & 0xFF);
        }
        return out;
    }

    static public byte[] getBytesBE(double doubleValue) {
        long value = Double.doubleToRawLongBits(doubleValue);
        byte[] out = new byte[8];
        out[7] = (byte) (value & 0xFF); value = value >> 8;
        out[6] = (byte) (value & 0xFF); value = value >> 8;
        out[5] = (byte) (value & 0xFF); value = value >> 8;
        out[4] = (byte) (value & 0xFF); value = value >> 8;
        out[3] = (byte) (value & 0xFF); value = value >> 8;
        out[2] = (byte) (value & 0xFF); value = value >> 8;
        out[1] = (byte) (value & 0xFF); value = value >> 8;
        out[0] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesBE(double[] value) {
        int n = value.length;
        byte[] out = new byte[8 * n];
        long v;
        for(int i=0, j=0; i<n; i++, j+=8) {
            v = Double.doubleToRawLongBits(value[i]);
            out[j+7] = (byte) (v & 0xFF); v = v >> 8;
            out[j+6] = (byte) (v & 0xFF); v = v >> 8;
            out[j+5] = (byte) (v & 0xFF); v = v >> 8;
            out[j+4] = (byte) (v & 0xFF); v = v >> 8;
            out[j+3] = (byte) (v & 0xFF); v = v >> 8;
            out[j+2] = (byte) (v & 0xFF); v = v >> 8;
            out[j+1] = (byte) (v & 0xFF); v = v >> 8;
            out[j]   = (byte) (v & 0xFF);
        }
        return out;
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    static public byte[] getBytesLE(char value) {
        byte[] out = new byte[2];
        out[0] = (byte) (value & 0xFF); value = (char) (value >> 8);
        out[1] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesLE(char[] value) {
        int n = value.length;
        byte[] out = new byte[2 * n];
        char v;
        for(int i=0, j=0; i<n; i++, j+=2) {
            v = value[i];
            out[j] = (byte) (v & 0xFF); v = (char) (v >> 8);
            out[j+1] = (byte) (v & 0xFF);
        }
        return out;
    }

    static public byte[] getBytesLE(short value) {
        byte[] out = new byte[2];
        out[0] = (byte) (value & 0xFF); value = (short) (value >> 8);
        out[1] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesLE(short[] value) {
        int n = value.length;
        byte[] out = new byte[2 * n];
        short v;
        for(int i=0, j=0; i<n; i++, j+=2) {
            v = value[i];
            out[j] = (byte) (v & 0xFF); v = (short) (v >> 8);
            out[j+1] = (byte) (v & 0xFF);
        }
        return out;
    }

    static public byte[] getBytesLE(int value) {
        byte[] out = new byte[4];
        out[0] = (byte) (value & 0xFF); value = value >> 8;
        out[1] = (byte) (value & 0xFF); value = value >> 8;
        out[2] = (byte) (value & 0xFF); value = value >> 8;
        out[3] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesLE(int[] value) {
        int n = value.length;
        byte[] out = new byte[4 * n];
        int v;
        for(int i=0, j=0; i<n; i++, j+=4) {
            v = value[i];
            out[j]   = (byte) (v & 0xFF); v = v >> 8;
            out[j+1] = (byte) (v & 0xFF); v = v >> 8;
            out[j+2] = (byte) (v & 0xFF); v = v >> 8;
            out[j+3] = (byte) (v & 0xFF);
        }
        return out;
    }

    static public byte[] getBytesLE(long value) {
        byte[] out = new byte[8];
        out[0] = (byte) (value & 0xFF); value = value >> 8;
        out[1] = (byte) (value & 0xFF); value = value >> 8;
        out[2] = (byte) (value & 0xFF); value = value >> 8;
        out[3] = (byte) (value & 0xFF); value = value >> 8;
        out[4] = (byte) (value & 0xFF); value = value >> 8;
        out[5] = (byte) (value & 0xFF); value = value >> 8;
        out[6] = (byte) (value & 0xFF); value = value >> 8;
        out[7] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesLE(long[] value) {
        int n = value.length;
        byte[] out = new byte[8 * n];
        long v;
        for(int i=0, j=0; i<n; i++, j+=8) {
            v = value[i];
            out[j]   = (byte) (v & 0xFF); v = v >> 8;
            out[j+1] = (byte) (v & 0xFF); v = v >> 8;
            out[j+2] = (byte) (v & 0xFF); v = v >> 8;
            out[j+3] = (byte) (v & 0xFF); v = v >> 8;
            out[j+4] = (byte) (v & 0xFF); v = v >> 8;
            out[j+5] = (byte) (v & 0xFF); v = v >> 8;
            out[j+6] = (byte) (v & 0xFF); v = v >> 8;
            out[j+7] = (byte) (v & 0xFF);
        }
        return out;
    }

    static public byte[] getBytesLE(float floatValue) {
        int value = Float.floatToRawIntBits(floatValue);
        byte[] out = new byte[4];
        out[0] = (byte) (value & 0xFF); value = value >> 8;
        out[1] = (byte) (value & 0xFF); value = value >> 8;
        out[2] = (byte) (value & 0xFF); value = value >> 8;
        out[3] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesLE(float[] value) {
        int n = value.length;
        byte[] out = new byte[4 * n];
        int v;
        for(int i=0, j=0; i<n; i++, j+=4) {
            v = Float.floatToRawIntBits(value[i]);
            out[j]   = (byte) (v & 0xFF); v = v >> 8;
            out[j+1] = (byte) (v & 0xFF); v = v >> 8;
            out[j+2] = (byte) (v & 0xFF); v = v >> 8;
            out[j+3] = (byte) (v & 0xFF);
        }
        return out;
    }

    static public byte[] getBytesLE(double doubleValue) {
        long value = Double.doubleToRawLongBits(doubleValue);
        byte[] out = new byte[8];
        out[0] = (byte) (value & 0xFF); value = value >> 8;
        out[1] = (byte) (value & 0xFF); value = value >> 8;
        out[2] = (byte) (value & 0xFF); value = value >> 8;
        out[3] = (byte) (value & 0xFF); value = value >> 8;
        out[4] = (byte) (value & 0xFF); value = value >> 8;
        out[5] = (byte) (value & 0xFF); value = value >> 8;
        out[6] = (byte) (value & 0xFF); value = value >> 8;
        out[7] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesLE(double[] value) {
        int n = value.length;
        byte[] out = new byte[8 * n];
        long v;
        for(int i=0, j=0; i<n; i++, j+=8) {
            v = Double.doubleToRawLongBits(value[i]);
            out[j]   = (byte) (v & 0xFF); v = v >> 8;
            out[j+1] = (byte) (v & 0xFF); v = v >> 8;
            out[j+2] = (byte) (v & 0xFF); v = v >> 8;
            out[j+3] = (byte) (v & 0xFF); v = v >> 8;
            out[j+4] = (byte) (v & 0xFF); v = v >> 8;
            out[j+5] = (byte) (v & 0xFF); v = v >> 8;
            out[j+6] = (byte) (v & 0xFF); v = v >> 8;
            out[j+7] = (byte) (v & 0xFF);
        }
        return out;
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    static public char charFromBytesBE(byte[] byteArray) {
        char value = (char) (byteArray[0] & 0xFF); value = (char) (value << 8);
        value     |= (char) (byteArray[1] & 0xFF);
        return value;
    }

    static public char[] charArrayFromBytesBE(byte[] byteArray) {
        int n = byteArray.length / 2;
        char[] res = new char[n];
        char v;
        for(int i=0, j=0; i<n; i++, j+=2) {
            v  = (char) (byteArray[j  ] & 0xFF); v = (char) (v << 8);
            v |= (char) (byteArray[j+1] & 0xFF);
            res[i] = v;
        }
        return res;
    }

    static public short shortFromBytesBE(byte[] byteArray) {
        short value = (short) (byteArray[0] & 0xFF); value = (short) (value << 8);
        value      |= (short) (byteArray[1] & 0xFF);
        return value;
    }

    static public short[] shortArrayFromBytesBE(byte[] byteArray) {
        int n = byteArray.length / 2;
        short[] res = new short[n];
        short v;
        for(int i=0, j=0; i<n; i++, j+=2) {
            v  = (short) (byteArray[j  ] & 0xFF); v = (short) (v << 8);
            v |= (short) (byteArray[j+1] & 0xFF);
            res[i] = v;
        }
        return res;
    }

    static public int intFromBytesBE(byte[] byteArray) {
        int value = byteArray[0] & 0xFF; value = value << 8;
        value    |= byteArray[1] & 0xFF; value = value << 8;
        value    |= byteArray[2] & 0xFF; value = value << 8;
        value    |= byteArray[3] & 0xFF;
        return value;
    }

    static public int[] intArrayFromBytesBE(byte[] byteArray) {
        int n = byteArray.length / 4;
        int[] res = new int[n];
        int v;
        for(int i=0, j=0; i<n; i++, j+=4) {
            v  = byteArray[j  ] & 0xFF; v = v << 8;
            v |= byteArray[j+1] & 0xFF; v = v << 8;
            v |= byteArray[j+2] & 0xFF; v = v << 8;
            v |= byteArray[j+3] & 0xFF;
            res[i] = v;
        }
        return res;
    }

    static public long longFromBytesBE(byte[] byteArray) {
        long value = byteArray[0] & 0xFF; value = value << 8;
        value     |= byteArray[1] & 0xFF; value = value << 8;
        value     |= byteArray[2] & 0xFF; value = value << 8;
        value     |= byteArray[3] & 0xFF; value = value << 8;
        value     |= byteArray[4] & 0xFF; value = value << 8;
        value     |= byteArray[5] & 0xFF; value = value << 8;
        value     |= byteArray[6] & 0xFF; value = value << 8;
        value     |= byteArray[7] & 0xFF;
        return value;
    }

    static public long[] longArrayFromBytesBE(byte[] byteArray) {
        int n = byteArray.length / 8;
        long[] res = new long[n];
        long v;
        for(int i=0, j=0; i<n; i++, j+=8) {
            v  = byteArray[j  ] & 0xFF; v = v << 8;
            v |= byteArray[j+1] & 0xFF; v = v << 8;
            v |= byteArray[j+2] & 0xFF; v = v << 8;
            v |= byteArray[j+3] & 0xFF; v = v << 8;
            v |= byteArray[j+4] & 0xFF; v = v << 8;
            v |= byteArray[j+5] & 0xFF; v = v << 8;
            v |= byteArray[j+6] & 0xFF; v = v << 8;
            v |= byteArray[j+7] & 0xFF;
            res[i] = v;
        }
        return res;
    }

    static public float floatFromBytesBE(byte[] byteArray) {
        int value = byteArray[0] & 0xFF; value = value << 8;
        value    |= byteArray[1] & 0xFF; value = value << 8;
        value    |= byteArray[2] & 0xFF; value = value << 8;
        value    |= byteArray[3] & 0xFF;
        return Float.intBitsToFloat(value);
    }

    static public float[] floatArrayFromBytesBE(byte[] byteArray) {
        int n = byteArray.length / 4;
        float[] res = new float[n];
        int v;
        for(int i=0, j=0; i<n; i++, j+=4) {
            v  = byteArray[j  ] & 0xFF; v = v << 8;
            v |= byteArray[j+1] & 0xFF; v = v << 8;
            v |= byteArray[j+2] & 0xFF; v = v << 8;
            v |= byteArray[j+3] & 0xFF;
            res[i] = Float.intBitsToFloat(v);
        }
        return res;
    }

    static public double doubleFromBytesBE(byte[] byteArray) {
        long value = byteArray[0] & 0xFF; value = value << 8;
        value     |= byteArray[1] & 0xFF; value = value << 8;
        value     |= byteArray[2] & 0xFF; value = value << 8;
        value     |= byteArray[3] & 0xFF; value = value << 8;
        value     |= byteArray[4] & 0xFF; value = value << 8;
        value     |= byteArray[5] & 0xFF; value = value << 8;
        value     |= byteArray[6] & 0xFF; value = value << 8;
        value     |= byteArray[7] & 0xFF;
        return Double.longBitsToDouble(value);
    }

    static public double[] doubleArrayFromBytesBE(byte[] byteArray) {
        int n = byteArray.length / 8;
        double[] res = new double[n];
        long v;
        for(int i=0, j=0; i<n; i++, j+=8) {
            v  = byteArray[j  ] & 0xFF; v = v << 8;
            v |= byteArray[j+1] & 0xFF; v = v << 8;
            v |= byteArray[j+2] & 0xFF; v = v << 8;
            v |= byteArray[j+3] & 0xFF; v = v << 8;
            v |= byteArray[j+4] & 0xFF; v = v << 8;
            v |= byteArray[j+5] & 0xFF; v = v << 8;
            v |= byteArray[j+6] & 0xFF; v = v << 8;
            v |= byteArray[j+7] & 0xFF;
            res[i] = Double.longBitsToDouble(v);
        }
        return res;
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    static public char charFromBytesLE(byte[] byteArray) {
        char value = (char) (byteArray[1] & 0xFF); value = (char) (value << 8);
        value     |= (char) (byteArray[0] & 0xFF);
        return value;
    }

    static public char[] charArrayFromBytesLE(byte[] byteArray) {
        int n = byteArray.length / 2;
        char[] res = new char[n];
        char v;
        for(int i=0, j=0; i<n; i++, j+=2) {
            v  = (char) (byteArray[j+1] & 0xFF); v = (char) (v << 8);
            v |= (char) (byteArray[j  ] & 0xFF);
            res[i] = v;
        }
        return res;
    }

    static public short shortFromBytesLE(byte[] byteArray) {
        short value = byteArray[1]; value = (short) (value << 8);
        value      |= byteArray[0];
        return value;
    }

    static public short[] shortArrayFromBytesLE(byte[] byteArray) {
        int n = byteArray.length / 2;
        short[] res = new short[n];
        short v;
        for(int i=0, j=0; i<n; i++, j+=2) {
            v  = (short) (byteArray[j+1] & 0xFF); v = (short) (v << 8);
            v |= (short) (byteArray[j  ] & 0xFF);
            res[i] = v;
        }
        return res;
    }

    static public int intFromBytesLE(byte[] byteArray) {
        int value = byteArray[3]; value = value << 8;
        value    |= byteArray[2]; value = value << 8;
        value    |= byteArray[1]; value = value << 8;
        value    |= byteArray[0];
        return value;
    }

    static public int[] intArrayFromBytesLE(byte[] byteArray) {
        int n = byteArray.length / 4;
        int[] res = new int[n];
        int v;
        for(int i=0, j=0; i<n; i++, j+=4) {
            v  = byteArray[j+3] & 0xFF; v = v << 8;
            v |= byteArray[j+2] & 0xFF; v = v << 8;
            v |= byteArray[j+1] & 0xFF; v = v << 8;
            v |= byteArray[j  ] & 0xFF;
            res[i] = v;
        }
        return res;
    }

    static public long longFromBytesLE(byte[] byteArray) {
        long value = byteArray[7]; value = value << 8;
        value     |= byteArray[6]; value = value << 8;
        value     |= byteArray[5]; value = value << 8;
        value     |= byteArray[4]; value = value << 8;
        value     |= byteArray[3]; value = value << 8;
        value     |= byteArray[2]; value = value << 8;
        value     |= byteArray[1]; value = value << 8;
        value     |= byteArray[0];
        return value;
    }

    static public long[] longArrayFromBytesLE(byte[] byteArray) {
        int n = byteArray.length / 8;
        long[] res = new long[n];
        long v;
        for(int i=0, j=0; i<n; i++, j+=8) {
            v  = byteArray[j+7] & 0xFF; v = v << 8;
            v |= byteArray[j+6] & 0xFF; v = v << 8;
            v |= byteArray[j+5] & 0xFF; v = v << 8;
            v |= byteArray[j+4] & 0xFF; v = v << 8;
            v |= byteArray[j+3] & 0xFF; v = v << 8;
            v |= byteArray[j+2] & 0xFF; v = v << 8;
            v |= byteArray[j+1] & 0xFF; v = v << 8;
            v |= byteArray[j  ] & 0xFF;
            res[i] = v;
        }
        return res;
    }

    static public float floatFromBytesLE(byte[] byteArray) {
        int value = byteArray[3]; value = value << 8;
        value    |= byteArray[2]; value = value << 8;
        value    |= byteArray[1]; value = value << 8;
        value    |= byteArray[0];
        return Float.intBitsToFloat(value);
    }

    static public float[] floatArrayFromBytesLE(byte[] byteArray) {
        int n = byteArray.length / 4;
        float[] res = new float[n];
        int v;
        for(int i=0, j=0; i<n; i++, j+=4) {
            v  = byteArray[j+3] & 0xFF; v = v << 8;
            v |= byteArray[j+2] & 0xFF; v = v << 8;
            v |= byteArray[j+1] & 0xFF; v = v << 8;
            v |= byteArray[j  ] & 0xFF;
            res[i] = Float.intBitsToFloat(v);
        }
        return res;
    }

    static public double doubleFromBytesLE(byte[] byteArray) {
        long value = byteArray[7]; value = value << 8;
        value     |= byteArray[6]; value = value << 8;
        value     |= byteArray[5]; value = value << 8;
        value     |= byteArray[4]; value = value << 8;
        value     |= byteArray[3]; value = value << 8;
        value     |= byteArray[2]; value = value << 8;
        value     |= byteArray[1]; value = value << 8;
        value     |= byteArray[0];
        return Double.longBitsToDouble(value);
    }

    static public double[] doubleArrayFromBytesLE(byte[] byteArray) {
        int n = byteArray.length / 8;
        double[] res = new double[n];
        long v;
        for(int i=0, j=0; i<n; i++, j+=8) {
            v  = byteArray[j+7] & 0xFF; v = v << 8;
            v |= byteArray[j+6] & 0xFF; v = v << 8;
            v |= byteArray[j+5] & 0xFF; v = v << 8;
            v |= byteArray[j+4] & 0xFF; v = v << 8;
            v |= byteArray[j+3] & 0xFF; v = v << 8;
            v |= byteArray[j+2] & 0xFF; v = v << 8;
            v |= byteArray[j+1] & 0xFF; v = v << 8;
            v |= byteArray[j  ] & 0xFF;
            res[i] = Double.longBitsToDouble(v);
        }
        return res;
    }
}