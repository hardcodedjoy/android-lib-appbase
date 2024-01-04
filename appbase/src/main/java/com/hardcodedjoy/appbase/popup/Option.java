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

package com.hardcodedjoy.appbase.popup;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Option {

    private int iconId;
    private Bitmap iconBitmap;
    private Drawable iconDrawable;
    private int backgroundColor;
    private int iconSize;
    private int iconTintColor;

    private int nameId;
    private String name;
    private final Runnable executor;
    private boolean selected;

    public Option(String name, Runnable executor) {
        this.name = name;
        this.executor = executor;
    }

    public Option(int iconId, int nameId, Runnable executor) {
        this.iconId = iconId;
        this.nameId = nameId;
        this.executor = executor;
    }

    public Option(Bitmap iconBitmap, String name, Runnable executor) {
        this.iconBitmap = iconBitmap;
        this.name = name;
        this.executor = executor;
    }

    public Option(int iconId, String name, Runnable executor) {
        this.iconId = iconId;
        this.name = name;
        this.executor = executor;
    }

    public Option(Drawable iconDrawable, String name, Runnable executor) {
        this.iconDrawable = iconDrawable;
        this.name = name;
        this.executor = executor;
    }

    public int getIconId() { return iconId; }
    public Bitmap getIconBitmap() { return iconBitmap; }
    public Drawable getIconDrawable() { return iconDrawable; }

    public int getNameId() { return nameId; }
    public String getName() { return name; }
    public Runnable getExecutor() { return executor; }
    public void run() { executor.run(); }

    public void setIconSize(int px) { this.iconSize = px; }
    public int getIconSize() { return iconSize; }

    public void setIconTintColor(int color) { this.iconTintColor = color; }
    public int getIconTintColor() { return iconTintColor; }

    public void setSelected() { this.selected = true; }
    public boolean isSelected() { return selected; }
}
