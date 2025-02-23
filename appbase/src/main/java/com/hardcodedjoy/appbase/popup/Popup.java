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

import android.view.LayoutInflater;
import android.view.View;
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

    public void show() { ContentView.showPopUp(this); }

    static protected String getString(int resId) {
        return ContentView.getActivity().getString(resId);
    }
}
