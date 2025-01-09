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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Size;

public class ImageFraming {
    static public final String CROP = "CROP";
    static public final String FIT  = "FIT";
    static public final String FILL = "FILL";

    static public RectF getRectFramingCropCenter(int inW, int inH, int outW, int outH) {
        // for framing mode == CROP
        // call this method, then scale to (outW, outH)

        // Calculate the aspect ratios:
        float inAR = inW / (float) inH;
        float outAR = outW / (float) outH;

        if(inAR > outAR) { // Image is wider than display -> crop width:
            float cropW = outAR * inH;
            float cropX = (inW - cropW) / 2;
            float cropY = 0;
            //noinspection UnnecessaryLocalVariable
            float cropH = inH;
            return new RectF(cropX, cropY, cropX + cropW, cropY + cropH);

        } else { // Image is taller than display -> crop height:
            float cropH = inW / outAR;
            float cropX = 0;
            float cropY = (inH - cropH) / 2;
            //noinspection UnnecessaryLocalVariable
            float cropW = inW;
            return new RectF(cropX, cropY, cropX + cropW, cropY + cropH);
        }
    }

    static public Size getSizeFramingCenterFit(int inW, int inH, int outW, int outH) {
        // for framing mode == FIT
        // call this method, then draw to center in (outW, outH)

        // Calculate the aspect ratios:
        float inAR = inW / (float) inH;
        float outAR = outW / (float) outH;

        if(inAR > outAR) { // Image is wider than display:
            //noinspection UnnecessaryLocalVariable
            int newW = outW;
            int newH = (int)(newW / inAR + 0.5f);
            return new Size(newW, newH);
        } else { // Image is taller than display:
            //noinspection UnnecessaryLocalVariable
            int newH = outH;
            int newW = (int)(newH * inAR + 0.5f);
            return new Size(newW, newH);
        }
    }

    static public Bitmap applyFraming(Bitmap frameBitmap, int outW, int outH, String imageFramingMode) {

        if(frameBitmap == null) { return null; }

        int inW = frameBitmap.getWidth();
        int inH = frameBitmap.getHeight();

        if(inW == outW && inH == outH) { return frameBitmap; } // perfect, nothing to do

        // else:

        if(CROP.equals(imageFramingMode)) {
            RectF rectF = ImageFraming.getRectFramingCropCenter(inW, inH, outW, outH);
            float cropX = rectF.left;
            float corpY = rectF.top;
            float cropW = rectF.right - rectF.left;
            float cropH = rectF.bottom - rectF.top;
            return ImageUtil.cropAndScale(frameBitmap, cropX, corpY, cropW, cropH, outW, outH);
        } else if(FIT.equals(imageFramingMode)) {
            Size size = ImageFraming.getSizeFramingCenterFit(inW, inH, outW, outH);
            Bitmap temp = Bitmap.createScaledBitmap(frameBitmap,
                    size.getWidth(), size.getHeight(), true);
            frameBitmap = Bitmap.createBitmap(outW, outH, Bitmap.Config.ARGB_8888);
            frameBitmap.eraseColor(0xFF000000); // TODO: configurable color
            Canvas canvas = new Canvas(frameBitmap);
            canvas.drawBitmap(temp,
                    ((outW - temp.getWidth()) / 2.0f),
                    ((outH - temp.getHeight()) / 2.0f),
                    null);
            return frameBitmap;
        } else if(FILL.equals(imageFramingMode)) {
            // easiest mode -> just scale, break aspect:
            return Bitmap.createScaledBitmap(frameBitmap, outW, outH, true);
        }
        return null;
    }
}
