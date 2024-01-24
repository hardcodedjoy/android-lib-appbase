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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.LanguageUtil;
import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.Settings;
import com.hardcodedjoy.appbase.SettingsKeys;
import com.hardcodedjoy.appbase.gui.ThemeUtil;
import com.hardcodedjoy.appbase.popup.Option;
import com.hardcodedjoy.appbase.popup.PopupChoose;

import java.util.Vector;

@SuppressLint("ViewConstructor")
public class CvSettingsBase extends ContentView {

    private Settings settings;

    public CvSettingsBase() { init(); }

    public void init() {
        removeAllViews();
        inflate(R.layout.appbase_cv_settings);

        settings = (Settings) ContentView.settings;

        TextView tvTheme = findViewById(R.id.appbase_tv_theme);

        LinearLayout dd;

        dd = findViewById(R.id.appbase_dd_theme_mode);
        EditText etThemeMode = dd.findViewById(R.id.appbase_et_drop_down);
        ImageButton btnThemeMode = dd.findViewById(R.id.appbase_btn_drop_down_expand);

        dd = findViewById(R.id.appbase_dd_theme);
        @SuppressLint("CutPasteId")
        EditText etTheme = dd.findViewById(R.id.appbase_et_drop_down);
        @SuppressLint("CutPasteId")
        ImageButton btnTheme = dd.findViewById(R.id.appbase_btn_drop_down_expand);

        dd = findViewById(R.id.appbase_dd_app_language);
        @SuppressLint("CutPasteId")
        EditText etAppLanguage = dd.findViewById(R.id.appbase_et_drop_down);
        @SuppressLint("CutPasteId")
        ImageButton btnAppLanguage = dd.findViewById(R.id.appbase_btn_drop_down_expand);

        if(LanguageUtil.getAvailableAppLanguages().length == 0) {
            // no app languages set -> only "en", no language to choose from
            findViewById(R.id.appbase_tv_app_language).setVisibility(GONE);
            dd.setVisibility(GONE);
        }

        etThemeMode.setText(keyToText(settings.getThemeMode()));
        etThemeMode.setFocusable(false);
        etThemeMode.setFocusableInTouchMode(false);
        etThemeMode.setOnClickListener(view -> onBtnThemeMode());
        btnThemeMode.setOnClickListener(view -> onBtnThemeMode());

        boolean lightNotDark = ThemeUtil.themeModeLightNotDark(
                getActivity(), settings.getThemeMode());

        if(lightNotDark) {
            tvTheme.setText(R.string.light_theme);
            etTheme.setText(settings.getLightTheme());
        } else {
            tvTheme.setText(R.string.dark_theme );
            etTheme.setText(settings.getDarkTheme());
        }

        etTheme.setFocusable(false);
        etTheme.setFocusableInTouchMode(false);
        etTheme.setOnClickListener(view -> onBtnTheme());
        btnTheme.setOnClickListener(view -> onBtnTheme());

        etAppLanguage.setText(keyToText(settings.getAppLanguageCode()));
        etAppLanguage.setFocusable(false);
        etAppLanguage.setFocusableInTouchMode(false);
        etAppLanguage.setOnClickListener(view -> onBtnAppLanguage());
        btnAppLanguage.setOnClickListener(view -> onBtnAppLanguage());
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

    private void onBtnThemeMode() {
        String title = getString(R.string.theme_mode);
        String currentThemeMode = settings.getThemeMode();
        Vector<Option> op = new Vector<>();
        String[] themeModes = new String[] {
                SettingsKeys.themeModeDefault,
                SettingsKeys.themeModeLight,
                SettingsKeys.themeModeDark
        };
        for(String themeMode : themeModes) {
            Option option = new Option(keyToText(themeMode), () -> onThemeModeSelected(themeMode));
            if(themeMode.equals(currentThemeMode)) { option.setSelected(); }
            op.add(option);
        }
        PopupChoose popupChoose = new PopupChoose(title, null, op);
        popupChoose.enableDismissByOutsideClick();
        popupChoose.show();
    }

    private void onThemeModeSelected(String themeMode) {
        settings.setThemeMode(themeMode);
        settings.save();
        // settings.getTheme needs activity to get the day / night mode of android settings
        ThemeUtil.setTheme(getActivity(), settings.getTheme(getActivity()));
        init(); // re-init to reflect new theme
    }

    private void onBtnTheme() {
        String title;
        String currentThemeName;
        boolean lightNotDark = ThemeUtil.themeModeLightNotDark(
                getActivity(), settings.getThemeMode());
        if(lightNotDark) {
            title = getString(R.string.light_theme);
            currentThemeName = settings.getLightTheme();
        } else {
            title = getString(R.string.dark_theme);
            currentThemeName = settings.getDarkTheme();
        }
        Vector<Option> op = new Vector<>();
        String[] themes = ThemeUtil.getThemes(getActivity(), lightNotDark);

        for(String themeName : themes) {
            Option option = new Option(themeName, () -> onThemeSelected(lightNotDark, themeName));
            if(themeName.equals(currentThemeName)) { option.setSelected(); }
            op.add(option);
        }
        PopupChoose popupChoose = new PopupChoose(title, null, op);
        popupChoose.enableDismissByOutsideClick();
        popupChoose.show();
    }

    private void onThemeSelected(boolean lightNotDark, String themeName) {
        if(lightNotDark) { settings.setLightTheme(themeName); }
        else             { settings.setDarkTheme(themeName); }
        // settings.getTheme needs activity to get the day / night mode of android settings
        ThemeUtil.setTheme(getActivity(), settings.getTheme(getActivity()));
        init(); // re-init to reflect new theme
    }

    private void onBtnAppLanguage() {
        String title = getString(R.string.app_language);
        Vector<Option> op = new Vector<>();
        String[] languages = LanguageUtil.getAvailableAppLanguages();
        String currentLangCode = settings.getAppLanguageCode();
        for(String langCode : languages) {
            String text = keyToText(langCode);
            Option option = new Option(text, () -> onAppLanguageSelected(langCode));
            if(langCode.equals(currentLangCode)) { option.setSelected(); }
            op.add(option);
        }
        PopupChoose popupChoose = new PopupChoose(title, null, op);
        popupChoose.enableDismissByOutsideClick();
        popupChoose.show();
    }

    private void onAppLanguageSelected(String langCode) {
        settings.setAppLanguageCode(langCode);
        settings.save();
        langCode = settings.getAppLanguageCode();
        setAppLanguage(langCode);
        init(); // re-init to reflect new language
    }

    @Override
    public boolean onBackPressed() {
        ((Settings) settings).save();
        return super.onBackPressed();
    }
}