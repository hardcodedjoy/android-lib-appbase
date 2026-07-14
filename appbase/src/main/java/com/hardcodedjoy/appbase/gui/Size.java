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

package com.hardcodedjoy.appbase.gui;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

public class Size {
    private int width;
    private int height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    static public Size fromStringWxH(String stringWxH) {
        int w = 0;
        int h = 0;
        String[] s = stringWxH.split(Pattern.quote("x"));
        if(s.length == 2) {
            try {
                w = Integer.parseInt(s[0]);
                h = Integer.parseInt(s[1]);
            } catch (Exception ignored) {}
        }
        return new Size(w, h);
    }

    public void setWidth(int width) { this.width = width; }
    public int getWidth() { return width; }

    public void setHeight(int height) { this.height = height; }
    public int getHeight() { return height; }

    @NonNull
    @Override
    public String toString() { return width + "x" + height; }
}