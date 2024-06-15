/*

MIT License

Copyright © 2024 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ViewUtil {

    static public RectF getRect(View view) {
        float x = view.getX();
        float y = view.getY();
        int w = view.getWidth();
        int h = view.getHeight();
        return new RectF(x, y, x+w, y+h);
    }

    static public void setXYWH(View view, float x, float y, float w, float h) {
        ViewGroup.LayoutParams params;
        params = new ViewGroup.LayoutParams((int)w, (int)h);
        view.setLayoutParams(params);
        view.setX(x);
        view.setY(y);
    }

    static public void setRect(View view, RectF rect) {
        ViewGroup.LayoutParams params;
        params = new ViewGroup.LayoutParams((int)(rect.right - rect.left), (int)(rect.bottom - rect.top));
        view.setLayoutParams(params);
        view.setX(rect.left);
        view.setY(rect.top);
    }

    static public void setColorOnImageView(View view, int color) {
        view.post(() -> {
            int w = view.getWidth() - (view.getPaddingLeft() + view.getPaddingRight());
            int h = view.getHeight() - (view.getPaddingTop() + view.getPaddingBottom());
            if(w < 1 || h < 1) { return; }
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(color);
            if(view instanceof ImageView) { // ImageButton is also an ImageView
                ((ImageView)view).setImageBitmap(bitmap);
            }
        });
    }
}