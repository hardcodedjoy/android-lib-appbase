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
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.gui.DisplayUnit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint("ViewConstructor")
public class CvLicense extends CvTSLL {

    static private final String licenseText =
            "Copyright © [year] HARDCODED JOY S.R.L.<br/>" +
            "<a href=\"https://hardcodedjoy.com\">https://hardcodedjoy.com</a><br/>" +
            "<br/>" +
            "Permission is hereby granted, free of charge, to any person obtaining a copy<br/>" +
            "of this software and associated documentation files (the \"Software\"), to deal<br/>" +
            "in the Software without restriction, including without limitation the rights<br/>" +
            "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell<br/>" +
            "copies of the Software, and to permit persons to whom the Software is<br/>" +
            "furnished to do so, subject to the following conditions:<br/>" +
            "<br/>" +
            "The above copyright notice and this permission notice shall be included in all<br/>" +
            "copies or substantial portions of the Software.<br/>" +
            "<br/>" +
            "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR<br/>" +
            "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,<br/>" +
            "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE<br/>" +
            "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER<br/>" +
            "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,<br/>" +
            "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE<br/>" +
            "SOFTWARE.";

    public CvLicense() {
        TextView tv = findViewById(R.id.appbase_tv_title);
        tv.setText(R.string.mit_license);

        tv = new TextView(getActivity());
        setAsHTML(tv, licenseText.replace("[year]", getYear()));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int marginPx = DisplayUnit.dpToPx(10.0f);
        params.setMargins(marginPx, marginPx, marginPx, marginPx);
        tv.setLayoutParams(params);

        LinearLayout llContent = findViewById(R.id.appbase_ll_content);
        llContent.addView(tv);
    }

    @SuppressWarnings("deprecation")
    static private Spanned fromHTML(String s) {
        if (Build.VERSION.SDK_INT < 24) { return Html.fromHtml(s); }
        // else:
        return Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY);
    }

    private void setAsHTML(TextView tv, String s) {
        tv.setText(fromHTML(s));
        tv.setClickable(true);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    static private String getYear() {
        Locale locale;
        if(android.os.Build.VERSION.SDK_INT > 23) {
            locale = getActivity().getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getActivity().getResources().getConfiguration().locale;
        }
        DateFormat yearFormat = new SimpleDateFormat("yyyy", locale);
        return yearFormat.format(new Date());
    }
}