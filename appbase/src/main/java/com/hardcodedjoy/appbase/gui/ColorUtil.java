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

package com.hardcodedjoy.appbase.gui;

@SuppressWarnings("unused")
public class ColorUtil {

    static public int blend(int col1, int col2, float col2ratio) {

        int b1 = col1 & 0xFF; col1 = col1 >> 8;
        int g1 = col1 & 0xFF; col1 = col1 >> 8;
        int r1 = col1 & 0xFF; col1 = col1 >> 8;
        int a1 = col1 & 0xFF;

        int b2 = col2 & 0xFF; col2 = col2 >> 8;
        int g2 = col2 & 0xFF; col2 = col2 >> 8;
        int r2 = col2 & 0xFF; col2 = col2 >> 8;
        int a2 = col2 & 0xFF;

        float col1ratio = 1.0f - col2ratio;

        int a = (int) (a1 * col1ratio + a2 * col2ratio + 0.5f);
        int r = (int) (r1 * col1ratio + r2 * col2ratio + 0.5f);
        int g = (int) (g1 * col1ratio + g2 * col2ratio + 0.5f);
        int b = (int) (b1 * col1ratio + b2 * col2ratio + 0.5f);

        a = limit(a, 0, 255);
        r = limit(r, 0, 255);
        g = limit(g, 0, 255);
        b = limit(b, 0, 255);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    @SuppressWarnings("SameParameterValue")
    static private int limit(int val, int min, int max) {
        if(val < min) { return min; }
        //noinspection ManualMinMaxCalculation
        if(val > max) { return max; }
        return val;
    }

    static public int grayLevel(int color) {
        int b = color & 0xFF; color = color >> 8;
        int g = color & 0xFF; color = color >> 8;
        int r = color & 0xFF;
        return (int)(((r+g+b) / 3.0f) + 0.5f);
    }
}