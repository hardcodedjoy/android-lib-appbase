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

import android.view.inputmethod.EditorInfo;

abstract public class PopupInputNumber extends PopupInput {

    abstract public void onOK(String s);

    public PopupInputNumber(String title, String message, String def) {
        super(title, message, def);
        getEtInput().setInputType(EditorInfo.TYPE_CLASS_NUMBER);
    }

    public PopupInputNumber(int titleStringId, int messageStringId, String def) {
        this(getString(titleStringId), getString(messageStringId), def);
    }

    public PopupInputNumber(String title, String message) { this(title, message, null); }
    public PopupInputNumber(String title) { this(title, null, null); }
    public PopupInputNumber(int titleStringId, int messageStringId) {
        this(titleStringId, messageStringId, null);
    }
    public PopupInputNumber(int titleStringId) { this(getString(titleStringId), null, null); }
    public PopupInputNumber(int titleStringId, String def) {
        this(getString(titleStringId), null, def);
    }
}