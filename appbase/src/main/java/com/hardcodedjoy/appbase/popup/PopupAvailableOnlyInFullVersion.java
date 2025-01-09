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

package com.hardcodedjoy.appbase.popup;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.hardcodedjoy.appbase.MarketLinkGenerator;
import com.hardcodedjoy.appbase.R;
import com.hardcodedjoy.appbase.contentview.ContentView;

public class PopupAvailableOnlyInFullVersion extends PopupAsk {

    private final String url;

    public PopupAvailableOnlyInFullVersion(
            String title,
            String fullVersionAppName,
            String fullVersionPackageName,
            String utmSource) {

        super(title,
              getString(R.string.available_only_in_full_version)
                      .replace("[x]", fullVersionAppName),
              getString(R.string.btn_get_full_version),
              getString(R.string.btn_ok));

        this.url  = MarketLinkGenerator.urlAppInMarket(
                fullVersionPackageName, utmSource);

        if(url == null) {
            // not installed form a store -> hide "get full version" button:
            View v = this.findViewById(R.id.appbase_btn_ok_text);
            if(v != null) { v.setVisibility(GONE); }
        }
    }

    @Override
    public void onOK() {
        if(url == null) { return; }
        // else -> was installed from a store ->
        // open full version app page in the same store:
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        ContentView.getActivity().startActivity(i);
    }
}