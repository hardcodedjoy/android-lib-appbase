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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.gui.Size;

public class ImageFraming {
    static public final String CROP = "CROP";
    static public final String FIT  = "FIT";
    static public final String FILL = "FILL";

    static public String getTitle() {
        return ContentView.getString(R.string.title_image_framing);
    }

    static public String[] getKeys() {
        return new String[] { FIT, CROP, FILL };
    }

    static public String[] getValues() {
        return new String[] {
                ContentView.getString(R.string.option_fit),
                ContentView.getString(R.string.option_crop),
                ContentView.getString(R.string.option_fill)
        };
    }

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

    static public Bitmap applyFraming(Bitmap bitmap,
                                      int outW, int outH,
                                      String imageFramingMode) {

        if(bitmap == null) { return null; }

        int inW = bitmap.getWidth();
        int inH = bitmap.getHeight();

        if(inW == outW && inH == outH) { return bitmap; } // perfect, nothing to do

        // else:

        if(CROP.equals(imageFramingMode)) {
            RectF rectF = ImageFraming.getRectFramingCropCenter(
                    inW, inH, outW, outH);
            float cropX = rectF.left;
            float cropY = rectF.top;
            float cropW = rectF.right - rectF.left;
            float cropH = rectF.bottom - rectF.top;
            return ImageUtil.cropAndScale(bitmap,
                    cropX, cropY, cropW, cropH, outW, outH);
        } else if(FIT.equals(imageFramingMode)) {
            Size size = ImageFraming.getSizeFramingCenterFit(
                    inW, inH, outW, outH);
            Bitmap temp = Bitmap.createScaledBitmap(bitmap,
                    size.getWidth(), size.getHeight(), true);
            Bitmap output = Bitmap.createBitmap(outW, outH, Bitmap.Config.ARGB_8888);
            output.eraseColor(0xFF000000); // TODO: configurable color
            Canvas canvas = new Canvas(output);
            Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
            canvas.drawBitmap(temp,
                    ((outW - temp.getWidth()) / 2.0f),
                    ((outH - temp.getHeight()) / 2.0f),
                    paint);
            if(temp != bitmap) { temp.recycle(); }
            return output;
        } else if(FILL.equals(imageFramingMode)) {
            // easiest mode -> just scale, break aspect:
            return Bitmap.createScaledBitmap(bitmap, outW, outH, true);
        }
        return null;
    }

    static public void applyFraming(Bitmap srcBitmap,
                                    Bitmap destBitmap,
                                    String imageFramingMode) {

        if(destBitmap == null) { return; }

        destBitmap.eraseColor(0xFF000000); // TODO: configurable color
        Canvas canvas = new Canvas(destBitmap);

        if(srcBitmap == null) { return; }

        int inW = srcBitmap.getWidth();
        int inH = srcBitmap.getHeight();

        int outW = destBitmap.getWidth();
        int outH = destBitmap.getHeight();

        if(inW == outW && inH == outH) { // perfect, copy as is:
            canvas.drawBitmap(srcBitmap, 0, 0, null);
            return;
        }

        // else:

        Rect srcRect;
        Rect destRect;

        if(CROP.equals(imageFramingMode)) {
            RectF cropRectF = ImageFraming.getRectFramingCropCenter(inW, inH, outW, outH);

            // Map the float crop formulas to integer pixel coordinates
            srcRect = new Rect(
                    (int) cropRectF.left,
                    (int) cropRectF.top,
                    (int) cropRectF.right,
                    (int) cropRectF.bottom);
            destRect = new Rect(0, 0, outW, outH);
        } else if(FIT.equals(imageFramingMode)) {
            Size size = ImageFraming.getSizeFramingCenterFit(
                    inW, inH, outW, outH);
            int left = (outW - size.getWidth()) / 2;
            int top = (outH - size.getHeight()) / 2;
            srcRect = new Rect(0, 0, inW, inH);
            destRect = new Rect(left, top, left + size.getWidth(), top + size.getHeight());
        } else if(FILL.equals(imageFramingMode)) {
            // easiest mode -> just scale, break aspect:
            srcRect = new Rect(0, 0, inW, inH);
            destRect = new Rect(0, 0, outW, outH);
        } else {
            return;
        }

        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        // Draws zero-allocation scaled crop directly
        canvas.drawBitmap(srcBitmap, srcRect, destRect, paint);
    }
}