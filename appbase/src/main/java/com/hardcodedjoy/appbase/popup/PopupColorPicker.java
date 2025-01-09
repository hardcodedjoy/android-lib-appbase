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

package com.hardcodedjoy.appbase.popup;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.gui.GuiLinker;
import com.hardcodedjoy.appbase.setgetters.SetGetter;

abstract public class PopupColorPicker extends Popup {

    private final Button btnPositive;
    private final Button btnNegative;
    private final ImageButton btnOK;
    private final ImageButton btnCancel;

    private final ImageView ivSaturationLightness;
    private final ImageView ivHue;
    private final ImageView ivColorCurrent;

    private final SeekBar sbOpacity;
    private boolean sbOpacitySetIgnore;

    private final EditText etCodeARGB;
    private boolean etCodeARGBSetIgnore;

    private int colorCurrent;
    private float hueCurrent;
    private float saturationCurrent;
    private float lightnessCurrent;

    private final Bitmap bmpSaturationLightness;
    private final int[] bitmapSaturationLightnessPixels;

    private final Bitmap bitmapHueBar;
    private final int[] bitmapHueBarPixels;

    abstract public void onOK(int colorNew);
    @Override
    public void onCancel() { }

    @SuppressLint("ClickableViewAccessibility")
    public PopupColorPicker(String title, String message, int colorOld) {

        bmpSaturationLightness = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888);
        bitmapSaturationLightnessPixels = new int[bmpSaturationLightness.getWidth() * bmpSaturationLightness.getHeight()];

        bitmapHueBar = Bitmap.createBitmap(1, 360, Bitmap.Config.ARGB_8888);
        bitmapHueBarPixels = new int[bitmapHueBar.getHeight()];

        // first inflate:
        inflater.inflate(R.layout.appbase_popup_color_picker, this);

        TextView tvTitle = findViewById(R.id.appbase_tv_title);
        TextView tvMessage = findViewById(R.id.appbase_tv_message);

        if(title == null) { tvTitle.setVisibility(GONE); }
        else { tvTitle.setText(title); }

        if(message == null) { tvMessage.setVisibility(GONE); }
        else { tvMessage.setVisibility(VISIBLE); tvMessage.setText(message); }

        btnOK = findViewById(R.id.appbase_btn_ok);
        btnCancel = findViewById(R.id.appbase_btn_cancel);
        btnPositive = findViewById(R.id.appbase_btn_ok_text);
        btnNegative = findViewById(R.id.appbase_btn_cancel_text);

        ivSaturationLightness = findViewById(R.id.appbase_iv_saturation_lightness);
        ivHue = findViewById(R.id.appbase_iv_hue);
        ImageView ivColorOld = findViewById(R.id.appbase_iv_color_old);
        ivColorCurrent = findViewById(R.id.appbase_iv_color_current);

        sbOpacity = findViewById(R.id.appbase_sb_current_opacity);
        etCodeARGB = findViewById(R.id.appbase_et_current_argb_code);

        btnOK.setOnClickListener(ocl);
        btnCancel.setOnClickListener(ocl);
        btnPositive.setOnClickListener(ocl);
        btnNegative.setOnClickListener(ocl);
        setOnClickListener(ocl);

        this.colorCurrent = colorOld;
        hsvCurrentUpdate();

        ivColorOld.setBackgroundColor(colorOld);
        ivColorCurrent.setBackgroundColor(colorCurrent);

        refreshSaturationLightnessArea();
        ivSaturationLightness.setOnTouchListener((v, event) -> {
            int index = event.getActionIndex();
            //int actionMasked = event.getActionMasked();
            int width = v.getWidth();
            int height = v.getHeight();
            float x = event.getX(index);
            float y = event.getY(index);

            if(x < 0) { x = 0; }
            if(x > width-1) { x = width-1; }

            if(y < 0) { y = 0; }
            if(y > height-1) { y = height-1; }


            x = x / (width-1);

            y = (height-1) - y;
            y = y / (height-1);

            saturationCurrent = x;
            lightnessCurrent = y;

            float[] hsv = new float[3];
            //Color.colorToHSV(colorCurrent, hsv);
            hsv[0] = hueCurrent;
            hsv[1] = saturationCurrent;
            hsv[2] = lightnessCurrent;
            colorCurrent = (Color.HSVToColor(hsv) & 0x00FFFFFF) | (colorCurrent & 0xFF000000);

            ivColorCurrent.setBackgroundColor(colorCurrent);
            refreshSaturationLightnessArea();
            refreshHueBar();
            etCodeARGBSetIgnore = true; // so that setText will not trigger code from set()
            etCodeARGB.setText(String.format("%08X", colorCurrent));
            return true;
        });


        refreshHueBar();
        ivHue.setOnTouchListener((v, event) -> {
            int index = event.getActionIndex();
            //int actionMasked = event.getActionMasked();
            int height = v.getHeight();
            float y = event.getY(index);

            if(y < 0) { y = 0; }
            if(y > height-1) { y = height-1; }

            y = (height-1) - y;
            y = y * 360;
            y = y / (height-1);

            hueCurrent = y;

            float[] hsv = new float[3];
            Color.colorToHSV(colorCurrent, hsv);
            hsv[0] = hueCurrent;
            colorCurrent = (Color.HSVToColor(hsv) & 0x00FFFFFF) | (colorCurrent & 0xFF000000);

            ivColorCurrent.setBackgroundColor(colorCurrent);
            refreshSaturationLightnessArea();
            refreshHueBar();
            etCodeARGBSetIgnore = true; // so that setText will not trigger code from set()
            etCodeARGB.setText(String.format("%08X", colorCurrent));
            return true;
        });

        GuiLinker.link(sbOpacity, new SetGetter() {
            @Override
            public void set(String s) {
                if(sbOpacitySetIgnore) {
                    sbOpacitySetIgnore = false;
                    return;
                }
                int opacity = Integer.parseInt(s);
                colorCurrent = (colorCurrent & 0x00FFFFFF) | (opacity << 24);
                ivColorCurrent.setBackgroundColor(colorCurrent);
                etCodeARGBSetIgnore = true; // so that setText will not trigger code from set()
                etCodeARGB.setText(String.format("%08X", colorCurrent));
            }

            @Override
            public String get() { return "" + ((colorCurrent >> 24) & 0xFF); }
        });

        GuiLinker.link(etCodeARGB, new SetGetter() {
            @Override
            public void set(String colorHex) {
                if(etCodeARGBSetIgnore) {
                    etCodeARGBSetIgnore = false;
                    return;
                }
                //noinspection PointlessBitwiseExpression
                colorCurrent = (int)(Long.parseLong(colorHex, 16) & 0xFFFFFFFF);
                ivColorCurrent.setBackgroundColor(colorCurrent);
                hsvCurrentUpdate();
                refreshSaturationLightnessArea();
                refreshHueBar();
                sbOpacitySetIgnore = true; // so that setText will not trigger code from set()
                sbOpacity.setProgress((colorCurrent >> 24) & 0xFF);
            }
            @Override
            public String get() { return String.format("%08X", colorCurrent); }
        });
    }

    @Override
    void oclOnClick(View view) {
        ContentView.removePopUp(this);  // dismiss

        int id = view.getId();
        if(id == R.id.appbase_btn_ok || id == R.id.appbase_btn_ok_text) { onOK(colorCurrent); }
        else if(id == R.id.appbase_btn_cancel || id == R.id.appbase_btn_cancel_text) { onCancel(); }
        else if(id == R.id.appbase_ll_outside_popup) { onCancel(); }
    }

    public PopupColorPicker(String title, String message, String positive, String negative, int colorOld) {
        this(title, message, colorOld);
        btnPositive.setText(positive);
        btnNegative.setText(negative);

        btnOK.setVisibility(GONE);
        btnCancel.setVisibility(GONE);
        btnPositive.setVisibility(VISIBLE);
        btnNegative.setVisibility(VISIBLE);

        if(positive.length() > 10 && negative.length() < 5) {
            ((LinearLayout.LayoutParams)btnPositive.getLayoutParams()).weight = 0.7f;
            ((LinearLayout.LayoutParams)btnNegative.getLayoutParams()).weight = 1.3f;
        } else if(negative.length() > 10 && positive.length() < 5) {
            ((LinearLayout.LayoutParams)btnNegative.getLayoutParams()).weight = 0.7f;
            ((LinearLayout.LayoutParams)btnPositive.getLayoutParams()).weight = 1.3f;
        }
    }

    @SuppressWarnings("unused")
    public PopupColorPicker(int titleStringId, int messageStringId,
                            int positiveStringId, int negativeStringId, int colorOld) {
        this(ContentView.getString(titleStringId),
                ContentView.getString(messageStringId),
                ContentView.getString(positiveStringId),
                ContentView.getString(negativeStringId),
                colorOld);
    }

    @SuppressWarnings("unused")
    public PopupColorPicker(int titleStringId, int messageStringId, int colorOld) {
        this(ContentView.getString(titleStringId),
                ContentView.getString(messageStringId), colorOld);
    }

    @SuppressWarnings("unused")
    public PopupColorPicker(int titleStringId, int colorOld) {
        this(ContentView.getString(titleStringId), null, colorOld);
    }

    public PopupColorPicker(int colorOld) {
        this(ContentView.getString(R.string.title_select_color), null, colorOld);
    }

    private void refreshSaturationLightnessArea() {
        int w = bmpSaturationLightness.getWidth();
        int h = bmpSaturationLightness.getHeight();
        float[] hsv = new float[3];
        float saturation;
        float lightness;

        int lightnessInPixels;
        int saturationCurrentInPixels = (int)(saturationCurrent * (w-1) + 0.5f);
        int lightnessCurrentInPixels  = (int)(lightnessCurrent  * (h-1) + 0.5f);

        hsv[0] = hueCurrent;
        int color;
        for(int i=0; i<w; i++) {
            saturation = ((float)i)/(w-1);
            hsv[1] = saturation;
            for(int j=0; j<h; j++) {
                lightness = ((float)(h-1-j))/(h-1);
                hsv[2] = lightness;
                color = Color.HSVToColor(hsv);

                // horizontal cursor
                lightnessInPixels = h-1-j;
                if(lightnessInPixels == lightnessCurrentInPixels) { color = 0xFF000000; }
                if(lightnessInPixels+1 == lightnessCurrentInPixels) { color = 0xFFFFFFFF; }
                if(lightnessInPixels-1 == lightnessCurrentInPixels) { color = 0xFFFFFFFF; }

                // vertical cursor
                if(i == saturationCurrentInPixels) { color = 0xFF000000; }
                if(!(lightnessInPixels == lightnessCurrentInPixels)) {
                    if(i+1 == saturationCurrentInPixels) { color = 0xFFFFFFFF; }
                    if(i-1 == saturationCurrentInPixels) { color = 0xFFFFFFFF; }
                }

                //bitmapSaturationLightness.setPixel(i, j, color);
                bitmapSaturationLightnessPixels[j*w + i] = color;
            }
        }

        bmpSaturationLightness.setPixels(bitmapSaturationLightnessPixels, 0, w, 0, 0, w, h);
        ivSaturationLightness.setImageBitmap(bmpSaturationLightness);
    }


    private void refreshHueBar() {
        int height = bitmapHueBar.getHeight();
        int color;
        int hue;
        int hueCurrentInt = (int)(hueCurrent + 0.5f);
        float[] hsv = new float[3];
        hsv[1] = 1;
        hsv[2] = 1;
        for(int i=0; i<height; i++) {
            hue = height-1-i;
            hsv[0] = hue;
            color = Color.HSVToColor(hsv);

            // cursor:
            if(hue == hueCurrentInt) { color = 0xFF000000; }
            if(hue+1 == hueCurrentInt) { color = 0xFF000000; }
            if(hue-1 == hueCurrentInt) { color = 0xFF000000; }
            if(hue+2 == hueCurrentInt) { color = 0xFFFFFFFF; }
            if(hue-2 == hueCurrentInt) { color = 0xFFFFFFFF; }
            if(hue+3 == hueCurrentInt) { color = 0xFFFFFFFF; }
            if(hue-3 == hueCurrentInt) { color = 0xFFFFFFFF; }

            //bitmapHueBar.setPixel(0, i, color);
            bitmapHueBarPixels[i] = color;
        }
        bitmapHueBar.setPixels(bitmapHueBarPixels, 0, 1, 0, 0, 1, height);
        ivHue.setImageBitmap(bitmapHueBar);
    }

    private void hsvCurrentUpdate() {
        float[] hsv = new float[3];
        Color.colorToHSV(colorCurrent, hsv);
        hueCurrent = hsv[0];
        saturationCurrent = hsv[1];
        lightnessCurrent = hsv[2];
    }
}