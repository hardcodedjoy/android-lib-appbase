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

package com.hardcodedjoy.appbase;

import android.app.Activity;
import android.content.pm.PackageManager;

public class MarketLinkGenerator {

    static public String urlAppInMarket(String packageName, String utmSource) {
        String ispn = getInstallationSourcePackageName();
        if(ispn == null) { return null; }

        if(packageName == null) { return null; }

        String url = null;

        if(HardcodedJoyConstants.getInstallationSourceGooglePlay().equals(ispn)) {
            // installed from Google Play
            url = HardcodedJoyConstants.getGooglePlayAppDetailsMarketURL() + packageName;
            if(utmSource != null) {
                url += "&" + HardcodedJoyConstants.getReferrerUtmSource() + utmSource;
            }
        }

        if(HardcodedJoyConstants.getInstallationSourceAmazonAppStore().equals(ispn)) {
            // installed from Amazon App Store
            // url = "amzn://apps/android?p=" + packageName // TODO: test
            // if(utmSource != null) {
            //     url += "&" + HardcodedJoyConstants.getReferrerUtmSource() + utmSource
            // }
            return null;
        }

        if(HardcodedJoyConstants.getInstallationSourceSamsungGalaxyStore().equals(ispn)) {
            // installed from Samsung Galaxy Store
            url = HardcodedJoyConstants.getSamsungGalaxyStoreAppDetailsMarketURL() + packageName;
            /*if(utmSource != null) {
                 url += "?" + HardcodedJoyConstants.getReferrerUtmSource() + utmSource
            }*/
        }

        return url;
    }

    static public String urlAppInBrowser(String packageName, String utmSource) {
        String ispn = getInstallationSourcePackageName();
        if(ispn == null) { return null; }

        if(packageName == null) { return null; }

        String url = null;

        if(HardcodedJoyConstants.getInstallationSourceGooglePlay().equals(ispn)) {
            // installed from Google Play
            url = HardcodedJoyConstants.getGooglePlayAppDetailsBrowserURL() + packageName;
            if(utmSource != null) {
                url += "&" + HardcodedJoyConstants.getReferrerUtmSource() + utmSource;
            }
        }

        if(HardcodedJoyConstants.getInstallationSourceAmazonAppStore().equals(ispn)) {
            // installed from Amazon App Store
            return null; // TODO: implement
        }

        if(HardcodedJoyConstants.getInstallationSourceSamsungGalaxyStore().equals(ispn)) {
            // installed from Samsung Galaxy Store
            url = HardcodedJoyConstants.getSamsungGalaxyStoreAppDetailsBrowserURL() + packageName;
            /*if(utmSource != null) {
                url += "?" + HardcodedJoyConstants.getReferrerUtmSource() + utmSource
            }*/
        }

        return url;
    }

    /*static public String aHrefAppInBrowser(String text, String packageName, String utmSource) {
        String url = urlAppInBrowser(packageName, utmSource)
        if(url == null) { return null; }
        return HardcodedJoyConstants.aHref(url, text);
    }*/


    static public String getInstallationSourcePackageName() {
        Activity activity = FileUtil.getActivity();
        PackageManager pm = activity.getPackageManager();
        if(pm == null) { return null; }
        String thisPackageName = activity.getPackageName();
        if(thisPackageName == null) { return null; }
        String s = null;

        try {
            if(android.os.Build.VERSION.SDK_INT < 30) {
                s = pm.getInstallerPackageName(thisPackageName);
            } else {
                s = pm.getInstallSourceInfo(thisPackageName).getInitiatingPackageName();
            }
        } catch (Exception e) { /**/ }

        return s;
    }
}
