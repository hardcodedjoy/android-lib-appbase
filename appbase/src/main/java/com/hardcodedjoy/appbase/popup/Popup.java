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

package com.hardcodedjoy.appbase.popup;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;

abstract public class Popup extends LinearLayout {

    protected LayoutInflater inflater;
    protected OnClickListener ocl;
    protected LinearLayout llOutsidePopup;

    abstract void oclOnClick(View view);
    abstract public void onCancel();

    Popup() {
        super(ContentView.getActivity());
        inflater = ContentView.getInflater();
        this.ocl = this::oclOnClick;
    }

    public void enableDismissByOutsideClick() {
        llOutsidePopup = findViewById(R.id.appbase_ll_outside_popup);
        llOutsidePopup.setOnClickListener(ocl);
    }

    public void enableDismissByOutsideClick(Runnable onDismissByOutsideClick) {
        llOutsidePopup = findViewById(R.id.appbase_ll_outside_popup);
        llOutsidePopup.setOnClickListener(view -> {
            ocl.onClick(view);
            if(onDismissByOutsideClick != null) {
                onDismissByOutsideClick.run();
            }
        });
    }

    public void performOutsideClick() {
        if(llOutsidePopup != null) {
            llOutsidePopup.performClick();
        }
    }

    public void show() { ContentView.showPopUp(this); }

    static protected String getString(int resId) {
        return ContentView.getActivity().getString(resId);
    }



    // Adjust if softInput keyboard opens:

    private ViewTreeObserver viewTreeObserver;
    private ViewTreeObserver.OnGlobalLayoutListener listener;
    private final Rect contentAreaOfWindowBounds = new Rect();
    private int usableHeightPrevious = 0;

    private void possiblyResizeLayout() {
        getWindowVisibleDisplayFrame(contentAreaOfWindowBounds);
        int usableHeightNow = contentAreaOfWindowBounds.height();

        if(usableHeightNow != usableHeightPrevious) {

            int screenHeight = getRootView().getHeight();
            int keypadHeight = screenHeight - usableHeightNow;

            //noinspection StatementWithEmptyBody
            if(keypadHeight > screenHeight * 0.125) {
                // keyboard is opened
                // onSoftInputOpened();
            } else {
                // keyboard is closed
                // onSoftInputClosed();
            }

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = usableHeightNow;
            setLayoutParams(params);

            usableHeightPrevious = usableHeightNow;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(viewTreeObserver != null) {
            if(viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnGlobalLayoutListener(listener);
            }
        }
        viewTreeObserver = getViewTreeObserver();
        listener = this::possiblyResizeLayout;
        viewTreeObserver.addOnGlobalLayoutListener(listener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(viewTreeObserver != null && viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnGlobalLayoutListener(listener);
        }
    }
}