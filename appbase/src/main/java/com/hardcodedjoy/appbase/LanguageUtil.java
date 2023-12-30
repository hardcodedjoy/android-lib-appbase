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

package com.hardcodedjoy.appbase;

import android.content.res.Resources;

import java.util.Locale;

public class LanguageUtil {

    static private String[] availableAppLanguages = {};

    static public void setAvailableAppLanguages(String[] languages) {
        availableAppLanguages = new String[languages.length + 1];
        availableAppLanguages[0] = null; // default -> lang. that is set in Android settings
        System.arraycopy(languages, 0, availableAppLanguages, 1, languages.length);
    }

    @SuppressWarnings("unused")
    static public String[] getAvailableAppLanguages() { return availableAppLanguages; }

    static public boolean languageUnavailable(String languageCode) {
        for(String lang : availableAppLanguages) {
            if(lang == null) { continue; }
            if(lang.equals(languageCode)) { return false; }
        }
        return true;
    }

    static private String getSystemLanguageCode() { // the language that is set in Android Settings
        Locale locale;
        if (android.os.Build.VERSION.SDK_INT > 23) {
            locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        } else {
            locale = Resources.getSystem().getConfiguration().locale; // deprecated OK
        }
        return locale.getLanguage();
    }

    static public String getAvailableLanguageCode(String wanted) {
        if(wanted == null) { wanted = getSystemLanguageCode(); }

        // some String or system setting

        if(languageUnavailable(wanted)) { wanted = "en"; }

        // if unavailable -> en

        return wanted; // wanted / or system if available / or "en"
    }
}
