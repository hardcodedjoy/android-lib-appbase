/*

MIT License

Copyright © 2023 HARDCODED JOY S.R.L. (https://hardcodedjoy.com)

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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.gui.MenuOption;

import java.util.Vector;

@SuppressWarnings("unused")
@SuppressLint("ViewConstructor")
public class CvTMSLL extends ContentView { // Title / Menu / ScrollView / LinearLayout

    // class of cv to be displayed for settings,
    // to be set to app specific CvSettings using setSettingsCvClass()
    static private Class<?> settingsCvClass;

    private final LinearLayout llMenuOptions;
    private Vector<MenuOption> menuOptions;

    @SuppressLint({"ClickableViewAccessibility", "RtlHardcoded"})
    public CvTMSLL() {
        menuOptions = new Vector<>();
        menuOptions.add(new MenuOption(getString(R.string.about), () -> new CvAboutBase().show()));
        menuOptions.add(new MenuOption(getString(R.string.settings), () -> {
            try { ((ContentView)settingsCvClass.newInstance()).show();
            } catch (Exception e) { e.printStackTrace(System.err); }
        }));

        inflate(R.layout.appbase_cv_tmsll);

        FrameLayout flMenuOptions = findViewById(R.id.fl_menu_options);

        llMenuOptions = (LinearLayout) flMenuOptions.getChildAt(0);

        findViewById(R.id.iv_menu).setOnClickListener(view -> {
            if(llMenuOptions.getVisibility() == View.VISIBLE) {
                llMenuOptions.setVisibility(View.GONE);
            } else if(llMenuOptions.getVisibility() == View.GONE) {

                llMenuOptions.removeAllViews();
                Button button;

                for(MenuOption option : menuOptions) {
                    button = new Button(getActivity());

                    button.setText(option.getName());
                    button.setOnClickListener(v -> {
                        llMenuOptions.setVisibility(View.GONE);
                        option.getRunnable().run();
                    });
                    button.setGravity(Gravity.LEFT);
                    llMenuOptions.addView(button);
                }

                llMenuOptions.setVisibility(View.VISIBLE);
            }
        });
        llMenuOptions.setVisibility(View.GONE);

        // hide menu on outside touch:
        flMenuOptions.setOnTouchListener((v, event) -> {
            if(llMenuOptions.getVisibility() == View.VISIBLE) {
                llMenuOptions.setVisibility(View.GONE);
                return true;
            }
            return false;
        });
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