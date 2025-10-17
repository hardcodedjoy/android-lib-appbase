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

    static public byte[] getBytesBE(short value) {
        byte[] out = new byte[2];
        out[1] = (byte) (value & 0xFF); value = (short) (value >> 8);
        out[0] = (byte) (value & 0xFF);
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

    static public byte[] getBytesBE(float floatValue) {
        int value = Float.floatToRawIntBits(floatValue);
        byte[] out = new byte[4];
        out[3] = (byte) (value & 0xFF); value = value >> 8;
        out[2] = (byte) (value & 0xFF); value = value >> 8;
        out[1] = (byte) (value & 0xFF); value = value >> 8;
        out[0] = (byte) (value & 0xFF);
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

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    static public byte[] getBytesLE(char value) {
        byte[] out = new byte[2];
        out[0] = (byte) (value & 0xFF); value = (char) (value >> 8);
        out[1] = (byte) (value & 0xFF);
        return out;
    }

    static public byte[] getBytesLE(short value) {
        byte[] out = new byte[2];
        out[0] = (byte) (value & 0xFF); value = (short) (value >> 8);
        out[1] = (byte) (value & 0xFF);
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

    static public byte[] getBytesLE(float floatValue) {
        int value = Float.floatToRawIntBits(floatValue);
        byte[] out = new byte[4];
        out[0] = (byte) (value & 0xFF); value = value >> 8;
        out[1] = (byte) (value & 0xFF); value = value >> 8;
        out[2] = (byte) (value & 0xFF); value = value >> 8;
        out[3] = (byte) (value & 0xFF);
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

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    static public char charFromBytesBE(byte[] byteArray) {
        char value = (char) (byteArray[0] & 0xFF); value = (char) (value << 8);
        value     |= (char) (byteArray[1] & 0xFF);
        return value;
    }

    static public short shortFromBytesBE(byte[] byteArray) {
        short value = byteArray[0]; value = (short) (value << 8);
        value      |= byteArray[1];
        return value;
    }

    static public int intFromBytesBE(byte[] byteArray) {
        int value = byteArray[0]; value = value << 8;
        value    |= byteArray[1]; value = value << 8;
        value    |= byteArray[2]; value = value << 8;
        value    |= byteArray[3];
        return value;
    }

    static public long longFromBytesBE(byte[] byteArray) {
        long value = byteArray[0]; value = value << 8;
        value     |= byteArray[1]; value = value << 8;
        value     |= byteArray[2]; value = value << 8;
        value     |= byteArray[3]; value = value << 8;
        value     |= byteArray[4]; value = value << 8;
        value     |= byteArray[5]; value = value << 8;
        value     |= byteArray[6]; value = value << 8;
        value     |= byteArray[7];
        return value;
    }

    static public float floatFromBytesBE(byte[] byteArray) {
        int value = byteArray[0]; value = value << 8;
        value    |= byteArray[1]; value = value << 8;
        value    |= byteArray[2]; value = value << 8;
        value    |= byteArray[3];
        return Float.intBitsToFloat(value);
    }

    static public double doubleFromBytesBE(byte[] byteArray) {
        long value = byteArray[0]; value = value << 8;
        value     |= byteArray[1]; value = value << 8;
        value     |= byteArray[2]; value = value << 8;
        value     |= byteArray[3]; value = value << 8;
        value     |= byteArray[4]; value = value << 8;
        value     |= byteArray[5]; value = value << 8;
        value     |= byteArray[6]; value = value << 8;
        value     |= byteArray[7];
        return Double.longBitsToDouble(value);
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    static public char charFromBytesLE(byte[] byteArray) {
        char value = (char) (byteArray[1] & 0xFF); value = (char) (value << 8);
        value     |= (char) (byteArray[0] & 0xFF);
        return value;
    }

    static public short shortFromBytesLE(byte[] byteArray) {
        short value = byteArray[1]; value = (short) (value << 8);
        value      |= byteArray[0];
        return value;
    }

    static public int intFromBytesLE(byte[] byteArray) {
        int value = byteArray[3]; value = value << 8;
        value    |= byteArray[2]; value = value << 8;
        value    |= byteArray[1]; value = value << 8;
        value    |= byteArray[0];
        return value;
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

    static public float floatFromBytesLE(byte[] byteArray) {
        int value = byteArray[3]; value = value << 8;
        value    |= byteArray[2]; value = value << 8;
        value    |= byteArray[1]; value = value << 8;
        value    |= byteArray[0];
        return Float.intBitsToFloat(value);
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
}