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

package com.hardcodedjoy.appbase.contentview;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.gui.MenuOption;

import java.util.Vector;

@SuppressLint("ViewConstructor")
public class CvTM extends ContentView { // Content View with Title and Menu

    // class of cv to be displayed for settings,
    // to be set to app specific CvSettings using setSettingsCvClass()
    static protected Class<?> settingsCvClass;

    protected LinearLayout llMenuOptions;
    protected Vector<MenuOption> menuOptions;

    protected CvTM() {
        menuOptions = new Vector<>();
        menuOptions.add(new MenuOption(getString(R.string.about), () -> new CvAboutBase().show()));
        menuOptions.add(new MenuOption(getString(R.string.settings), () -> {
            try { ((ContentView)settingsCvClass.newInstance()).show();
            } catch (Exception e) { e.printStackTrace(System.err); }
        }));
    }

    @Override
    public boolean onBackPressed() {
        if(llMenuOptions.getVisibility() == View.VISIBLE) {
            llMenuOptions.setVisibility(View.GONE);
            return true; // consumed
        }
        return super.onBackPressed();
    }

    public Vector<MenuOption> getMenuOptions() { return menuOptions; }
    public void setMenuOptions(Vector<MenuOption> ops) { this.menuOptions = ops; }
    public void addMenuOptions(Vector<MenuOption> ops, int index) {
        menuOptions.addAll(index, ops);
    }

    static public void setSettingsCvClass(Class<?> c) { settingsCvClass = c; }
}