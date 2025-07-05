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

package com.hardcodedjoy.appbase.gui;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.SettingsKeys;

import java.lang.reflect.Field;
import java.util.Vector;

public class ThemeUtil {

    static public void setTheme(Activity activity, String themeName) {
        Resources resources = activity.getResources();
        String packageName = activity.getPackageName();
        int id = resources.getIdentifier(themeName, "style", packageName);
        activity.setTheme(id);

        if(Build.VERSION.SDK_INT >= 23) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            window.setStatusBarColor(getColor(activity, android.R.attr.colorForeground));
            if(currentThemeIsDarkNotLight(activity)) {
                // dark theme -> light title bar and status bar
                // adjust status bar text accordingly:
                if(Build.VERSION.SDK_INT < 26) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else if(Build.VERSION.SDK_INT < 30) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR |
                                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                } else {
                    WindowInsetsControllerCompat controller =
                            WindowCompat.getInsetsController(window, window.getDecorView());
                    controller.setAppearanceLightStatusBars(true);
                    controller.setAppearanceLightNavigationBars(true);
                }
            } else {
                if(Build.VERSION.SDK_INT < 30) {
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                } else {
                    window.setNavigationBarColor(Color.TRANSPARENT); // important!
                    WindowInsetsControllerCompat controller =
                            WindowCompat.getInsetsController(window, window.getDecorView());
                    controller.setAppearanceLightStatusBars(false); // white text and icons
                    controller.setAppearanceLightNavigationBars(false); // white icons*/
                }
            }
        }
    }

    static public boolean themeModeLightNotDark(Activity activity, String themeMode) {
        if(SettingsKeys.themeModeLight.equals(themeMode)) { return true; }
        if(SettingsKeys.themeModeDark.equals(themeMode)) { return false; }
        int systemUiMode = activity.getResources().getConfiguration().uiMode;
        int nightMode = systemUiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightMode != Configuration.UI_MODE_NIGHT_YES;
    }

    static public int getColor(Activity activity, int androidRAttrColorId) {
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(androidRAttrColorId, typedValue, true);
        return typedValue.data;
    }

    static private int getColor(Activity activity, int themeId, int androidRAttrColorId) {
        TypedArray a = activity.getTheme().obtainStyledAttributes(
                themeId, new int[]{ androidRAttrColorId });
        int col = a.getColor(0, 0);
        a.recycle();
        return col;
    }

    static public String[] getThemes(Activity activity) { return getThemes(activity, 'a'); }

    static public String[] getThemes(Activity activity, boolean lightNotDark) {
        if(lightNotDark) { return getThemes(activity, 'l'); }
        else             { return getThemes(activity, 'd'); }
    }

    static private String[] getThemes(Activity activity, char mode) { // mode = 'l' / 'd' / 'a'
        Resources resources = activity.getResources();
        String packageName = activity.getPackageName();

        Class<?> c = R.style.class;
        Field[] fields = c.getFields();
        String themeName;

        Vector<String> themes = new Vector<>();

        for(Field field : fields) {
            themeName = field.getName();
            int id = resources.getIdentifier(themeName, "style", packageName);
            if(isNotTheme(activity, id)) { continue; }

            int colorBackground = getColor(activity, id, android.R.attr.colorBackground);
            int colorForeground = getColor(activity, id, android.R.attr.colorForeground);
            if(colorBackground == 0) { continue; }
            if(colorForeground == 0) { continue; }

            int bgGray = ColorUtil.grayLevel(colorBackground);
            int fgGray = ColorUtil.grayLevel(colorForeground);

            if( (bgGray < fgGray) && mode == 'l' ) { continue; } // dark theme unwanted
            if( (bgGray > fgGray) && mode == 'd' ) { continue; } // light theme unwanted

            // else -> wanted theme
            themes.add(themeName);
        }

        return themes.toArray(new String[0]);
    }

    static private boolean isNotTheme(Activity activity, int themeId) {
        Resources resources = activity.getResources();
        TypedArray a = resources.obtainTypedArray(themeId);
        boolean res = a.length() < 100;

        // theme -> ~300
        // something else -> ~15
        // TODO: improve

        a.recycle();
        return res;
    }

    static public boolean currentThemeIsLightNotDark(Activity activity) {
        int colorBackground = getColor(activity, android.R.attr.colorBackground);
        int colorForeground = getColor(activity, android.R.attr.colorForeground);
        int bgGray = ColorUtil.grayLevel(colorBackground);
        int fgGray = ColorUtil.grayLevel(colorForeground);
        return (bgGray > fgGray);
    }

    static public boolean currentThemeIsDarkNotLight(Activity activity) {
        return !currentThemeIsLightNotDark(activity);
    }
}