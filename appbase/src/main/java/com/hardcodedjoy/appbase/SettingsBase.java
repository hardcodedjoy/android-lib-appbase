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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.hardcodedjoy.appbase.gui.ThemeUtil;

public class SettingsBase { // to be extended by specific app settings

    // class of settings, to be set to app specific settings using setSettingsClass()
    static private Class<?> settingsClass = SettingsBase.class;

    @SuppressLint("StaticFieldLeak")
    static private Activity activity;

    @SuppressLint("StaticFieldLeak")
    static private SettingsBase instance;

    static private final String THEME_MODE_DEFAULT = SettingsKeys.themeModeLight;
    static private final String LIGHT_THEME_DEFAULT = "SeaSaltEmerald";
    static private final String DARK_THEME_DEFAULT = "CharcoalEmerald";
    static private final String APP_LANGUAGE_CODE_DEFAULT = SettingsKeys.appLanguageCodeDefault;

    private SharedPreferences sp;

    private String themeMode;
    private String lightTheme;
    private String darkTheme;
    private String appLanguageCode;

    static public void setActivity(Activity activity) { SettingsBase.activity = activity; }

    @SuppressWarnings("SameParameterValue")
    static public void setSettingsClass(Class<?> c) { settingsClass = c; }

    static public SettingsBase getInstance() {
        if(instance == null) {

            try {
                instance = (SettingsBase) settingsClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                instance = new SettingsBase();
            }

            instance.sp = activity.getSharedPreferences("app_settings", Context.MODE_PRIVATE);
            instance.onLoad();
        }

        return instance;
    }

    protected SettingsBase() {}

    public void setThemeMode(String themeMode) { this.themeMode = themeMode; }
    public String getThemeMode() { return themeMode; }

    public void setLightTheme(String lightTheme) { this.lightTheme = lightTheme; }
    public String getLightTheme() { return lightTheme; }

    public void setDarkTheme(String darkTheme) { this.darkTheme = darkTheme; }
    public String getDarkTheme() { return darkTheme; }

    public String getTheme(Activity activity) {
        boolean lightNotDark = ThemeUtil.themeModeLightNotDark(activity, themeMode);
        if(lightNotDark) { return getLightTheme(); }
        else             { return getDarkTheme(); }
    }

    public void setAppLanguageCode(String lang) { this.appLanguageCode = lang; }
    public String getAppLanguageCode() { return appLanguageCode; }


    final public void onLoad() {
        setThemeMode(sp.getString(SettingsKeys.themeMode, THEME_MODE_DEFAULT));
        setLightTheme(sp.getString(SettingsKeys.lightTheme, LIGHT_THEME_DEFAULT));
        setDarkTheme(sp.getString(SettingsKeys.darkTheme, DARK_THEME_DEFAULT));
        setAppLanguageCode(sp.getString(SettingsKeys.appLanguageCode, APP_LANGUAGE_CODE_DEFAULT));
        onLoad(sp);
    }

    // to be overridden to load more stuff
    public void onLoad(SharedPreferences sp) {}

    // to be overridden to save more stuff
    public void onSave(SharedPreferences.Editor editor) {}

    @SuppressLint("ApplySharedPref")
    public final void save() {
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(SettingsKeys.themeMode, getThemeMode());
        editor.putString(SettingsKeys.lightTheme, getLightTheme());
        editor.putString(SettingsKeys.darkTheme, getDarkTheme());
        editor.putString(SettingsKeys.appLanguageCode, getAppLanguageCode());
        onSave(editor);

        editor.commit();
    }
}