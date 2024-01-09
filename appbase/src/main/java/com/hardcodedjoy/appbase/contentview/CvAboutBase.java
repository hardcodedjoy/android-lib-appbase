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
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.hardcodedjoy.appbase.AppBase;
import com.hardcodedjoy.appbase.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint("ViewConstructor")
public class CvAboutBase extends ContentView {

    static private String appVersion = "Unknown app version -> must be set using static { CvAboutBase.setAppVersion(BuildConfig.VERSION_NAME, BuildConfig.TIMESTAMP); } in MainActivity.";
    static private String infoAboutOpenSourceLibs = AppBase.about();

    public CvAboutBase() {
        inflate(R.layout.appbase_cv_about);

        TextView tv;
        String s;
        String url;

        tv = findViewById(R.id.tv_app_version);
        tv.setText(appVersion);

        tv = findViewById(R.id.tv_dev_website);
        s = tv.getText().toString();
        s = "<a href=\"" + s + "\">" + s + "</a>";
        setAsLink(tv, s);

        tv = findViewById(R.id.tv_insta_page);
        s = tv.getText().toString();
        s = "<a href=\"https://instagram.com/" + s.substring(1) + "\">" + s + "</a>";
        setAsLink(tv, s);
        findViewById(R.id.ll_insta_page).setOnClickListener(
                (view) -> findViewById(R.id.tv_insta_page).performClick());

        tv = findViewById(R.id.tv_youtube_channel);
        s = tv.getText().toString();
        s = "<a href=\"https://youtube.com/" + s + "\">" + s + "</a>";
        setAsLink(tv, s);
        findViewById(R.id.ll_youtube_channel).setOnClickListener(
                (view) -> findViewById(R.id.tv_youtube_channel).performClick());

        tv = findViewById(R.id.tv_github);
        s = tv.getText().toString();
        s = "<a href=\"https://github.com/" + s + "\">" + s + "</a>";
        setAsLink(tv, s);
        findViewById(R.id.ll_github).setOnClickListener(
                (view) -> findViewById(R.id.tv_github).performClick());

        tv = findViewById(R.id.tv_privacy_policy);
        s = tv.getText().toString();
        url = getPrivacyPolicyURL(getContext().getPackageName(), "en");
        s = "<a href=\"" + url + "\">" + s + "</a>";
        setAsLink(tv, s);

        tv = findViewById(R.id.tv_app_uses_open_source_libs);
        s = tv.getText().toString();

        // after second space from end, without space:
        int start = s.substring(0, s.lastIndexOf(' ')).lastIndexOf(' ') + 1;
        int end = s.length() - 1; // without ending ":"

        s += "\n" + infoAboutOpenSourceLibs;

        SpannableString spannableString = new SpannableString(s);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) { new CvLicense().show(); }
        };

        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        tv.setText(spannableString);
        tv.setClickable(true);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @SuppressWarnings("deprecation")
    static private Spanned fromHTML(String s) {
        if (Build.VERSION.SDK_INT < 24) { return Html.fromHtml(s); }
        // else:
        return Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY);
    }

    private void setAsLink(TextView tv, String s) {
        tv.setText(fromHTML(s));
        tv.setClickable(true);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @SuppressWarnings("SameParameterValue")
    static private String getPrivacyPolicyURL(String packageName, String languageCode) {
        return "https://hardcodedjoy.com/app-privacy-policy?id=" + packageName + "&hl=" + languageCode;
    }

    @SuppressWarnings("unused")
    static public void setAppVersion(String versionName, long timeStamp) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
        Date buildDate = new Date(timeStamp);
        appVersion = "v" + versionName + " (" + dateFormat.format(buildDate) + ")";
    }

    @SuppressWarnings("unused")
    static public void addInfoAboutOpenSourceLib(String s) { infoAboutOpenSourceLibs += "\n" + s; }
}