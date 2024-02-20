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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.LanguageUtil;
import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.Settings;
import com.hardcodedjoy.appbase.SettingsKeys;
import com.hardcodedjoy.appbase.gui.GuiLinker;
import com.hardcodedjoy.appbase.gui.SetGetter;
import com.hardcodedjoy.appbase.gui.ThemeUtil;

@SuppressLint("ViewConstructor")
public class CvSettingsBase extends ContentView {

    private Settings settings;

    public CvSettingsBase() { init(); }

    public void init() {
        removeAllViews();
        inflate(R.layout.appbase_cv_settings);

        settings = (Settings) ContentView.settings;

        TextView tvTheme = findViewById(R.id.appbase_tv_theme);

        String[] themeModes = new String[] {
                SettingsKeys.themeModeDefault,
                SettingsKeys.themeModeLight,
                SettingsKeys.themeModeDark
        };

        GuiLinker.linkDropDownList(
                findViewById(R.id.appbase_dd_theme_mode),
                getString(R.string.theme_mode),
                themeModes,
                keyToText(themeModes),
                new SetGetter() {
                    @Override
                    public void set(String key) { onThemeModeSelected(key); }
                    @Override
                    public String get() { return settings.getThemeMode(); }
                });


        boolean lightNotDark = ThemeUtil.themeModeLightNotDark(
                getActivity(), settings.getThemeMode());

        if(lightNotDark) { tvTheme.setText(R.string.light_theme); }
        else             { tvTheme.setText(R.string.dark_theme ); }

        String[] themes = ThemeUtil.getThemes(getActivity(), lightNotDark);


        GuiLinker.linkDropDownList(
                findViewById(R.id.appbase_dd_theme),
                getString(R.string.theme),
                themes,
                themes,
                new SetGetter() {
                    @Override
                    public void set(String key) { onThemeSelected(lightNotDark, key); }
                    @Override
                    public String get() {
                        if(lightNotDark) { return settings.getLightTheme(); }
                        else { return settings.getDarkTheme(); }
                    }
                });

        String[] langCodes = LanguageUtil.getAvailableAppLanguages();
        String[] lcn = new String[langCodes.length]; // ex: "en - English"
        for(int i=0; i<langCodes.length; i++) {

            // default -> "default" in various languages:
            if(langCodes[i].equals(SettingsKeys.appLanguageCodeDefault)) {
                lcn[i] = getString(R.string.lang_default);
                continue;
            }
            // else:

            // ex: "en - English"
            lcn[i] = langCodes[i] + " - " + LanguageUtil.getLanguageName(langCodes[i]);
        }

        GuiLinker.linkDropDownList(
                findViewById(R.id.appbase_dd_app_language),
                getString(R.string.app_language),
                langCodes,
                lcn,
                new SetGetter() {
                    @Override
                    public void set(String key) { onAppLanguageSelected(key); }
                    @Override
                    public String get() { return settings.getAppLanguageCode(); }
                });

        if(LanguageUtil.getAvailableAppLanguages().length == 0) {
            // no app languages set -> only "en", no language to choose from
            findViewById(R.id.appbase_tv_app_language).setVisibility(GONE);
            findViewById(R.id.appbase_dd_app_language).setVisibility(GONE);
        }


        // fast fix for text color for API 21:
        int textColor = ThemeUtil.getColor(getActivity(), android.R.attr.colorForeground);
        EditText et;
        et = findViewById(R.id.appbase_dd_theme_mode).findViewById(R.id.appbase_et_drop_down);
        et.setTextColor(textColor);
        et = findViewById(R.id.appbase_dd_theme).findViewById(R.id.appbase_et_drop_down);
        et.setTextColor(textColor);
        et = findViewById(R.id.appbase_dd_app_language).findViewById(R.id.appbase_et_drop_down);
        et.setTextColor(textColor);
    }

    public void addSettings(View view) {
        LinearLayout llAdditionalSettings = findViewById(R.id.appbase_ll_additional_settings);
        llAdditionalSettings.addView(view);
    }

    @SuppressWarnings("unused")
    public void addSettings(int layoutResId) {
        addSettings(inflate(getActivity(), layoutResId, null));
    }

    static private String keyToText(String key) {
        switch (key) {
            case SettingsKeys.themeModeDefault: return getString(R.string.theme_mode_default);
            case SettingsKeys.themeModeLight: return getString(R.string.theme_mode_light);
            case SettingsKeys.themeModeDark: return getString(R.string.theme_mode_dark);
            case SettingsKeys.appLanguageCodeDefault: return getString(R.string.lang_default);
            default: return key;
        }
    }

    static private String[] keyToText(String[] keys) {
        String[] text = new String[keys.length];
        for(int i=0; i< keys.length; i++) { text[i] = keyToText(keys[i]); }
        return text;
    }

    private void onThemeModeSelected(String themeMode) {
        if(themeMode == null) { return; }
        settings.setThemeMode(themeMode);
        settings.save();
        // settings.getTheme needs activity to get the day / night mode of android settings
        ThemeUtil.setTheme(getActivity(), settings.getTheme(getActivity()));
        init(); // re-init to reflect new theme
        showInfoAppRestartRequiredForTheme();
    }

    private void onThemeSelected(boolean lightNotDark, String themeName) {
        if(lightNotDark) { settings.setLightTheme(themeName); }
        else             { settings.setDarkTheme(themeName); }
        // settings.getTheme needs activity to get the day / night mode of android settings
        ThemeUtil.setTheme(getActivity(), settings.getTheme(getActivity()));
        init(); // re-init to reflect new theme
        showInfoAppRestartRequiredForTheme();
    }

    private void onAppLanguageSelected(String langCode) {
        settings.setAppLanguageCode(langCode);
        settings.save();
        langCode = settings.getAppLanguageCode();
        setAppLanguage(langCode);
        init(); // re-init to reflect new language
        showInfoAppRestartRequiredForLang();
    }

    private void showInfoAppRestartRequiredForTheme() {
        String s = getString(R.string.theme_changed) + " "
                + getString(R.string.please_restart_to_take_effect);
        showInfo(s);
    }

    private void showInfoAppRestartRequiredForLang() {
        String s = getString(R.string.language_changed) + " "
                + getString(R.string.please_restart_to_take_effect);
        showInfo(s);
    }

    @Override
    public boolean onBackPressed() {
        settings.save();
        return super.onBackPressed();
    }
}