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

package com.hardcodedjoy.appbase;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.util.Size;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.gui.ThemeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtil {

    static public Bitmap loadImage(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        return loadImage(fis);
    }

    static public Bitmap loadImage(InputStream is) throws Exception {
        if(is == null) { throw new Exception("is == null"); }

        if(android.os.Build.VERSION.SDK_INT < 24) {
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        }

        if( !(is instanceof FileInputStream)) {
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        }

        // else -> "is" is a FileInputStream:
        FileInputStream fis = (FileInputStream) is;
        ExifInterface exif = new ExifInterface(fis);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        fis.getChannel().position(0);
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        fis.close();

        Matrix matrix = new Matrix();

        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            matrix.postRotate(90);
        }
        else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            matrix.postRotate(180);
        }
        else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            matrix.postRotate(270);
        }

        return Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.getWidth(),
                bitmap.getHeight(),
                matrix,
                true); // rotating bmp
    }

    static public void saveAsPNG(Bitmap bitmap, OutputStream os) throws Exception {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
    }

    static public void saveAsJPG(Bitmap bitmap, OutputStream os, int jpegQuality) throws Exception {
        bitmap.compress(Bitmap.CompressFormat.JPEG, jpegQuality, os);
    }

    static public void saveAsPNG(Bitmap bitmap, File file) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().truncate(0);
        saveAsPNG(bitmap, fos);
        fos.close();
    }

    static public void saveAsJPG(Bitmap bitmap, File file, int jpegQuality) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().truncate(0);
        saveAsJPG(bitmap, fos, jpegQuality);
        fos.close();
    }

    static public Bitmap cropAndScale(Bitmap inputBitmap, float cropX, float cropY,
            float cropW, float cropH, int outW, int outH) {

        if(inputBitmap == null) { return null; }

        int x = (int)(cropX + 0.5f);
        int y = (int)(cropY + 0.5f);
        int w = (int)(cropW + 0.5f);
        int h = (int)(cropH + 0.5f);

        Bitmap resBitmap;

        if(cropRectangleIsInside(x, y, w, h, inputBitmap.getWidth(), inputBitmap.getHeight())) {
            // crop image to selection, but resolution may be bigger or smaller
            resBitmap = Bitmap.createBitmap(inputBitmap, x, y, w, h);
        } else {
            // transparent pixels will remain where no pixels in inputBitmap

            resBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            resBitmap.eraseColor(0x00000000);
            Canvas canvas = new Canvas(resBitmap);
            canvas.drawBitmap(inputBitmap, -x, -y, null);
        }

        // scale:
        return Bitmap.createScaledBitmap(resBitmap, outW, outH, true);
    }

    static public boolean cropRectangleIsInside(int cx, int cy, int cw, int ch, int w, int h) {
        if(cx < 0) { return false; }
        if(cy < 0) { return false; }
        if(cw < 0) { return false; }
        if(ch < 0) { return false; }

        if(cx >= w) { return false; }
        if(cy >= h) { return false; }

        if(cx + cw > w) { return false; }
        if(cy + ch > h) { return false; }

        return true;
    }

    static public Size limitSizeKeepRatio(Size inputSize, int max) {
        int w = inputSize.getWidth();
        int h = inputSize.getHeight();
        float ratio = w / (float) h;

        if(w > h) {
            if(w > max) {
                w = max;
                h = (int)(w / ratio + 0.5f);
            }
        } else {
            if(h > max) {
                h = max;
                w = (int)(h * ratio + 0.5f);
            }
        }
        return new Size(w, h);
    }


    static public void shrinkJPG(InputStream is, OutputStream os,
                                 int maxSizePixels, int jpegQuality) throws Exception {
        if(is == null) { throw new Exception("is == null"); }
        if(os == null) {
            is.close();
            throw new Exception("os == null");
        }

        Bitmap bitmap = ImageUtil.loadImage(is); // also closes the is

        Size inputSize = new Size(bitmap.getWidth(), bitmap.getHeight());
        Size outputSize = ImageUtil.limitSizeKeepRatio(inputSize, maxSizePixels);

        bitmap = Bitmap.createScaledBitmap(bitmap,
                outputSize.getWidth(), outputSize.getHeight(), true);

        ImageUtil.saveAsJPG(bitmap, os, jpegQuality);
        os.close();
    }

    static public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(0x00000000);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    static public Bitmap drawableToBitmap(Drawable drawable, int w, int h) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(0x00000000);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    static public BitmapDrawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(ContentView.getActivity().getResources(), bitmap);
    }

    static public Drawable getDrawable(int resId) {
        Activity activity = ContentView.getActivity();
        Resources.Theme theme = activity.getTheme();
        return activity.getResources().getDrawable(resId, theme);
    }

    static public void setTint(ImageView iv, int tintColor) {
        iv.setImageTintList(ColorStateList.valueOf(tintColor));
        iv.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
    }

    static public void setTint(ImageButton button, int tintColor) {
        button.setImageTintList(ColorStateList.valueOf(tintColor));
        button.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
    }
}