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

package com.hardcodedjoy.dev.appbase.cv;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hardcodedjoy.appbase.ImageFraming;
import com.hardcodedjoy.appbase.contentview.CvTSLL;
import com.hardcodedjoy.dev.appbase.R;

@SuppressLint("ViewConstructor")
public class CvImageFramingTest extends CvTSLL {

    public CvImageFramingTest() {
        // add initialization code here (that must run only one time)

        setTitle("Image Framing");
        setTitleIcon(com.hardcodedjoy.appbase.R.drawable.ic_info_1);
        LinearLayout ll = findViewById(R.id.appbase_ll_content);
        inflate(getActivity(), R.layout.cv_image_framing_test, ll);

        Bitmap bitmap = Bitmap.createBitmap(64, 32, Bitmap.Config.ARGB_8888);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int r;
        int g;
        int b;
        int color;
        for(int x=0; x<w; x++) {
            for(int y=0; y<h; y++) {
                r = x * 4;
                g = 255 - r;
                b = y * 8;
                color = 0xFF;
                color = (color << 8) + r;
                color = (color << 8) + g;
                color = (color << 8) + b;
                bitmap.setPixel(x, y, color);
            }
        }

        ImageView ivOriginal = findViewById(R.id.appbase_iv_original);
        ImageView ivFit      = findViewById(R.id.appbase_iv_fit);
        ImageView ivCrop     = findViewById(R.id.appbase_iv_crop);
        ImageView ivFill     = findViewById(R.id.appbase_iv_fill);

        ivOriginal.setImageBitmap(bitmap);

        /*ivFit.setImageBitmap(ImageFraming.applyFraming(
                bitmap, 128, 128, ImageFraming.FIT));
        ivCrop.setImageBitmap(ImageFraming.applyFraming(
                bitmap, 128, 128, ImageFraming.CROP));
        ivFill.setImageBitmap(ImageFraming.applyFraming(
                bitmap, 128, 128, ImageFraming.FILL));*/

        Bitmap destBitmapFit = Bitmap.createBitmap(
                128, 128, Bitmap.Config.ARGB_8888);
        Bitmap destBitmapCrop = Bitmap.createBitmap(
                128, 128, Bitmap.Config.ARGB_8888);
        Bitmap destBitmapFill = Bitmap.createBitmap(
                128, 128, Bitmap.Config.ARGB_8888);
        ImageFraming.applyFraming(bitmap, destBitmapFit, ImageFraming.FIT);
        ivFit.setImageBitmap(destBitmapFit);
        ImageFraming.applyFraming(bitmap, destBitmapCrop, ImageFraming.CROP);
        ivCrop.setImageBitmap(destBitmapCrop);
        ImageFraming.applyFraming(bitmap, destBitmapFill, ImageFraming.FILL);
        ivFill.setImageBitmap(destBitmapFill);
    }
}