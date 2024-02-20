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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.AppBase;
import com.hardcodedjoy.appbase.HardcodedJoyConstants;
import com.hardcodedjoy.appbase.IntentUtil;
import com.hardcodedjoy.appbase.MarketLinkGenerator;
import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.gui.DisplayUnit;
import com.hardcodedjoy.appbase.gui.ThemeUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

@SuppressLint("ViewConstructor")
public class CvAboutBase extends ContentView {

    static private String appVersion = "Unknown app version -> must be set using static { CvAboutBase.setAppVersion(BuildConfig.VERSION_NAME, BuildConfig.TIMESTAMP); } in MainActivity.";
    static private String infoAboutOpenSourceLibs = AppBase.about();

    static private int appUsageType = 0;
    static private String linkEULA = null;

    static private final Vector<String> otherAppNames = new Vector<>();
    static private final Vector<String> otherAppURLs = new Vector<>();

    public CvAboutBase() {
        inflate(R.layout.appbase_cv_about);

        TextView tv;
        String s;

        tv = findViewById(R.id.appbase_tv_app_package);
        tv.setText(getActivity().getPackageName());

        tv = findViewById(R.id.appbase_tv_app_version);
        tv.setText(appVersion);

        if(appUsageType == 0) {
            findViewById(R.id.appbase_tv_app_usage_type).setVisibility(GONE);
        } else {
            tv = findViewById(R.id.appbase_tv_app_usage_type);
            tv.setText(appUsageType);
        }

        if(linkEULA == null) {
            findViewById(R.id.appbase_tv_eula).setVisibility(GONE);
        } else {
            tv = findViewById(R.id.appbase_tv_eula);
            s = tv.getText().toString();
            s = HardcodedJoyConstants.aHref(linkEULA, s);
            setAsLink(tv, s);
        }

        tv = findViewById(R.id.appbase_tv_privacy_policy);
        setAsLink(tv, HardcodedJoyConstants.aHrefPrivacyPolicy());
        findViewById(R.id.appbase_ll_privacy_policy).setOnClickListener(
                (view) -> findViewById(R.id.appbase_tv_privacy_policy).performClick());

        String packageName = getActivity().getPackageName();

        {
            tv = findViewById(R.id.appbase_tv_rate_app);
            s = tv.getText().toString();
            s = MarketLinkGenerator.aHrefAppInMarket(s, packageName, null);
            LinearLayout ll = findViewById(R.id.appbase_ll_rate_app);
            if(s == null) {
                ll.setVisibility(GONE);
            } else if(s.equals("unimplemented")) {
                ll.removeAllViews();
                ll.setOnClickListener(view -> {});
                LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
                ll.setLayoutParams(params);
                ll.setBackgroundColor(ThemeUtil.getColor(getActivity(), android.R.attr.colorForeground));
            } else {
                setAsLink(tv, s);
                ll.setOnClickListener(
                        (view) -> findViewById(R.id.appbase_tv_rate_app).performClick());
            }
        }

        {
            tv = findViewById(R.id.appbase_tv_share_app);
            s = tv.getText().toString();
            tv.setText(fromHTML("<u>" + s + "</u>"));
            tv.setTextColor(ThemeUtil.getColor(getActivity(), android.R.attr.textColorLink));

            String url = MarketLinkGenerator.urlAppInBrowser(packageName, "share");
            LinearLayout ll = findViewById(R.id.appbase_ll_share_app);
            if(url == null) {
                ll.setVisibility(GONE);
            } else if(url.equals("unimplemented")) {
                ll.removeAllViews();
                ll.setOnClickListener(view -> {});
                LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
                ll.setLayoutParams(params);
                ll.setBackgroundColor(ThemeUtil.getColor(getActivity(), android.R.attr.colorForeground));
            } else {
                tv.setOnClickListener(view -> {
                    String title = getResources().getString(R.string.share) + " "
                            + getResources().getString(R.string.app_name);
                    IntentUtil.shareText(url, title);
                });
                ll.setOnClickListener(
                        (view) -> findViewById(R.id.appbase_tv_share_app).performClick());
            }
        }

        ImageView ivDevLogo = findViewById(R.id.appbase_iv_dev_logo);
        int padding = DisplayUnit.dpToPx(8);
        ivDevLogo.setImageBitmap(HardcodedJoyConstants.devLogo());
        ivDevLogo.setPadding(0, padding, 0, padding);

        tv = findViewById(R.id.appbase_tv_dev_website);
        setAsLink(tv, HardcodedJoyConstants.aHrefDevWebsite());

        tv = findViewById(R.id.appbase_tv_gp_page);
        setAsLink(tv, HardcodedJoyConstants.aHrefDevGooglePlay());
        findViewById(R.id.appbase_ll_gp_page).setOnClickListener(
                (view) -> findViewById(R.id.appbase_tv_gp_page).performClick());

        tv = findViewById(R.id.appbase_tv_insta_page);
        setAsLink(tv, HardcodedJoyConstants.aHrefDevInstagram());
        findViewById(R.id.appbase_ll_insta_page).setOnClickListener(
                (view) -> findViewById(R.id.appbase_tv_insta_page).performClick());

        tv = findViewById(R.id.appbase_tv_youtube_channel);
        setAsLink(tv, HardcodedJoyConstants.aHrefDevYouTube());
        findViewById(R.id.appbase_ll_youtube_channel).setOnClickListener(
                (view) -> findViewById(R.id.appbase_tv_youtube_channel).performClick());

        tv = findViewById(R.id.appbase_tv_github);
        setAsLink(tv, HardcodedJoyConstants.aHrefDevGitHub());
        findViewById(R.id.appbase_ll_github).setOnClickListener(
                (view) -> findViewById(R.id.appbase_tv_github).performClick());

        tv = findViewById(R.id.appbase_tv_app_uses_open_source_libs);
        s = tv.getText().toString();
        s += "\n" + infoAboutOpenSourceLibs;
        s += "\n" + getString(R.string.libs_released_under_mit_license);

        // after second space from end, without space:
        int start = s.substring(0, s.lastIndexOf(' ')).lastIndexOf(' ') + 1;
        int end = s.length() - 1; // without ending ":"

        SpannableString spannableString = new SpannableString(s);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) { new CvLicense().show(); }
        };

        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        tv.setText(spannableString);
        tv.setClickable(true);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        if(otherAppNames.size() == 0) {
            findViewById(R.id.appbase_tv_you_might_also_like).setVisibility(GONE);
            findViewById(R.id.appbase_ll_links_to_other_apps).setVisibility(GONE);
        } else {
            LinearLayout ll = findViewById(R.id.appbase_ll_links_to_other_apps);
            ll.removeAllViews();
            int n = otherAppNames.size();
            for(int i=0; i<n; i++) {
                tv = new TextView(getActivity());
                s = otherAppNames.elementAt(i);
                s = "<a href=\"" + otherAppURLs.elementAt(i) + "\">" + s + "</a>";
                setAsLink(tv, s);
                ll.addView(tv);
            }
        }
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

    @SuppressWarnings("unused")
    static public void setAppVersion(String versionName, long timeStamp) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
        Date buildDate = new Date(timeStamp);
        appVersion = "v" + versionName + " (" + dateFormat.format(buildDate) + ")";
    }

    @SuppressWarnings("unused")
    static public void addInfoAboutOpenSourceLib(String s) { infoAboutOpenSourceLibs += "\n" + s; }

    @SuppressWarnings("unused")
    static public void addLinkToOtherApp(String appName, String url) {
        otherAppNames.add(appName);
        otherAppURLs.add(url);
    }

    @SuppressWarnings("unused")
    static public void setAppUsageType(int resStringId) { appUsageType = resStringId; }

    @SuppressWarnings("unused")
    static public void setLinkEULA(String s) { linkEULA = s; }
}