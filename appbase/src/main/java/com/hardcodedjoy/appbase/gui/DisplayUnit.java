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

import android.content.res.Resources;

public class DisplayUnit {

    public static int dpToPx(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float pxToDp(int px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }
    
    public static int spToPx(float sp) {
        return (int) (sp * Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    public static float pxToSp(int px) {
        return px / Resources.getSystem().getDisplayMetrics().scaledDensity;
    }
    
    public static int getPx(String s) {
        s = s.toLowerCase();
        if(s.endsWith("dip")) {
            s = s.substring(0, s.length()-3);
            return dpToPx(Float.parseFloat(s));
        }
        s = s.substring(0, s.length()-2);
        if(s.endsWith("px")) { return Integer.parseInt(s); }
        if(s.endsWith("sp")) { return spToPx(Float.parseFloat(s)); }
        if(s.endsWith("dp")) { return dpToPx(Float.parseFloat(s)); }
        return 0;
    }
    
    public static float getSp(String s) { return pxToSp(getPx(s)); }
}