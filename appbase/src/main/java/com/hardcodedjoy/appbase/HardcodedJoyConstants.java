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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hardcodedjoy.appbase.contentview.ContentView;
import com.hardcodedjoy.appbase.gui.DisplayUnit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HardcodedJoyConstants {

    static public String aHref(String url, String text) {
        return "<a href=\"" + url + "\">" + text + "</a>";
    }

    static public String aHrefDevWebsite() {
        String text = Base64.decode(devWebsiteURL);
        String url = text;
        String packageName = ContentView.getActivity().getPackageName();
        String languageCode = ContentView.getAppLanguage();
        url += "?hl=" + languageCode;
        url += "&" + getReferrerUtmSource() + packageName;
        return aHref(url, text);
    }

    static public String aHrefDevGooglePlay() {
        String id = Base64.decode(devIdOnGooglePlay);
        String url = Base64.decode(googlePlayDevURL) + id;
        String name = Base64.decode(devCompanyName);
        return aHref(url, name);
    }

    static public String aHrefDevInstagram() {
        String page = Base64.decode(devInstagramPage);
        String url = Base64.decode(instagramURL) + "/" + (page.substring(1));
        return aHref(url, page);
    }

    static public String aHrefDevYouTube() {
        String handle = Base64.decode(devYouTubeHandle);
        String url = Base64.decode(youtubeURL) + "/" + handle;
        return aHref(url, handle);
    }

    static public String aHrefDevGitHub() {
        String name = Base64.decode(devGitHubName);
        String url = Base64.decode(githubURL) + "/" + name;
        return aHref(url, name);
    }

    static public String urlPrivacyPolicy() {
        String packageName = ContentView.getActivity().getPackageName();
        String languageCode = ContentView.getAppLanguage();
        return Base64.decode(privacyPolicyURL) + packageName + "&hl=" + languageCode;
    }

    static public Bitmap devLogo() {
        byte[] ba = android.util.Base64.decode(devLogoBase64, android.util.Base64.DEFAULT);
        Bitmap bmpLogo = BitmapFactory.decodeByteArray(ba, 0, ba.length);
        int w = DisplayUnit.dpToPx(240);
        int h = (bmpLogo.getHeight() * w) / bmpLogo.getWidth();
        return Bitmap.createScaledBitmap(bmpLogo, w, h, true);
    }

    static public String mitLicenseTextHTML() {
        String s = Base64.decode(mitLicenseTextBase64);
        s = s.replace("[year]", getYear());
        s = s.replace("[company]", Base64.decode(devCompanyName));
        s = s.replace("[url]", Base64.decode(devWebsiteURL));
        return s;
    }

    static private String getYear() {
        DateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.US);
        return yearFormat.format(new Date());
    }

    static public String getInstallationSourceAmazonAppStore() {
        return Base64.decode(installationSourceAmazonAppStore);
    }
    static public String getInstallationSourceGooglePlay() {
        return Base64.decode(installationSourceGooglePlay);
    }
    static public String getInstallationSourceSamsungGalaxyStore() {
        return Base64.decode(installationSourceSamsungGalaxyStore);
    }
    static public String getGooglePlayAppDetailsBrowserURL() {
        return Base64.decode(googlePlayAppDetailsBrowserURL);
    }
    static public String getGooglePlayAppDetailsMarketURL() {
        return Base64.decode(googlePlayAppDetailsMarketURL);
    }
    static public String getSamsungGalaxyStoreAppDetailsBrowserURL() {
        return Base64.decode(samsungGalaxyStoreAppDetailsBrowserURL);
    }
    static public String getSamsungGalaxyStoreAppDetailsMarketURL() {
        return Base64.decode(samsungGalaxyStoreAppDetailsMarketURL);
    }

    static public String getReferrerUtmSource() { return Base64.decode(referrerUtmSource); }

    static private final String devCompanyName = "SEFSRENPREVEIEpPWSBTLlIuTC4=";
    // "HARDCODED JOY S.R.L."

    static private final String devWebsiteURL = "aHR0cHM6Ly9oYXJkY29kZWRqb3kuY29t";
    // "https://hardcodedjoy.com"

    static private final String privacyPolicyURL =
            "aHR0cHM6Ly9oYXJkY29kZWRqb3kuY29tL2FwcC1wcml2YWN5LXBvbGljeT9pZD0=";
    // "https://hardcodedjoy.com/app-privacy-policy?id="

    static private final String devIdOnGooglePlay = "NjI1ODc0Mzg0MzQ3MTE4OTc2NA==";
    // "6258743843471189764"

    static private final String devInstagramPage = "QGhhcmRjb2RlZGpveQ==";
    // "@hardcodedjoy"

    static private final String devYouTubeHandle = "QGhhcmRjb2RlZF9qb3k=";
    // "@hardcoded_joy"

    static private final String devGitHubName = "aGFyZGNvZGVkam95";
    // "hardcodedjoy"

    static private final String googlePlayDevURL =
            "aHR0cHM6Ly9wbGF5Lmdvb2dsZS5jb20vc3RvcmUvYXBwcy9kZXY/aWQ9";
    // "https://play.google.com/store/apps/dev?id="

    static private final String googlePlayAppDetailsBrowserURL =
            "aHR0cHM6Ly9wbGF5Lmdvb2dsZS5jb20vc3RvcmUvYXBwcy9kZXRhaWxzP2lkPQ==";
    // "https://play.google.com/store/apps/details?id="

    static private final String googlePlayAppDetailsMarketURL = "bWFya2V0Oi8vZGV0YWlscz9pZD0=";
    // "market://details?id="

    static private final String samsungGalaxyStoreAppDetailsBrowserURL =
            "aHR0cHM6Ly9nYWxheHlzdG9yZS5zYW1zdW5nLmNvbS9kZXRhaWwv";
    // "https://galaxystore.samsung.com/detail/"

    static private final String samsungGalaxyStoreAppDetailsMarketURL =
            "c2Ftc3VuZ2FwcHM6Ly9Qcm9kdWN0RGV0YWlsLw==";
    // "samsungapps://ProductDetail/"



    static private final String referrerUtmSource = "cmVmZXJyZXI9dXRtX3NvdXJjZSUzRA==";
    // "referrer=utm_source%3D"

    static private final String instagramURL = "aHR0cHM6Ly9pbnN0YWdyYW0uY29t";
    // "https://instagram.com"

    static private final String youtubeURL = "aHR0cHM6Ly95b3V0dWJlLmNvbQ==";
    // "https://youtube.com"

    static private final String githubURL = "aHR0cHM6Ly9naXRodWIuY29t";
    // "https://github.com"

    static private final String installationSourceGooglePlay = "Y29tLmFuZHJvaWQudmVuZGluZw==";
    // "com.android.vending"

    static private final String installationSourceAmazonAppStore = "Y29tLmFtYXpvbi52ZW5lemlh";
    // "com.amazon.venezia"

    static private final String installationSourceSamsungGalaxyStore =
            "Y29tLnNlYy5hbmRyb2lkLmFwcC5zYW1zdW5nYXBwcw==";
    // "com.sec.android.app.samsungapps"

    static private final String devLogoBase64
          = "iVBORw0KGgoAAAANSUhEUgAAAVwAAABVCAYAAAAFblxQAAAUYUlEQVR42uzdT4iM" +
            "cRzH8a+/RUKZ59loDyQXlz3g4k/EkpTkzzOKhETCwYHZcXueCTk5uGhzceCyF6Uc" +
            "cFhmlsi28wwppd0kyf+I1pp/Pz9keZ7nl55mdqbNvl/1afY07eyfd0+/Z9qVDqeq" +
            "GryK3oeUU32uH5/o3U4nK5c6kuqwu1NNlwYp9bW0l33rXKlgXSvlrYL+uF8/viv5" +
            "1qCeauSKvnVEACAkGNzm7740QDFv7flXEAkugLEY3Kq7Sk2UEVb2E+cJLgCCG9xb" +
            "aYCSb3sEFwDB/bXBtFO9lXbUamkAlZ85s+zbnTp+LwkugFEd3LRTOZlOqvYRnaMW" +
            "Hd+qFhzbrGxpInW3dcrXQsu8Yp/dVipYK37cTBvR+dZHggugjuCq7YJYdGDfEFwA" +
            "BJfgAiC4BBcAwSW4BBcAwSW4AAguwSW4AAguwQVAcAkuAIJLcAkuAIJLcAEQXIIb" +
            "Cm7ePiQAYAhuheCO9BWufVAAwBDcUuQqN6mSgjqCmzggAGAIbjEc3JSjtgniBvdV" +
            "9EghsV8AwBDcIcORwmZBLKa/uVssWPsEAAzBHYwcKWxTmwRxg/vC8C6FvQIAhuB+" +
            "MZzhbhTEouP6PBzcct7aLQBgCO7ncHBPJNUGQczgJp5FguvbuwQADMH9ZDjDXS/N" +
            "0JM5Kjnv8/CymZVSq1zmSuC5mqTsWwPRK9zETgEAQ3BfG96lsFaaIZtJ6Tiq4WW9" +
            "NXU81/XAcyk1rknBfWq4wt0iAGAI7oCeCk4tJ7ixb5o91FPB2esEAMJSTvVRJLjb" +
            "VRvBjR3cO5Hg5u1lAgCGK9wbkSOFLaqV4MYO7tVwcIce2fMFAMJSycqFUHC/ua4a" +
            "T3Bjn+GeCwW3pO62ThEACOtw1I5QcLtFI7ixg5sMBTcrAGDirlITO5zKZR3afr0H" +
            "qa1qMcGNT/XKpGLBuqjD269j21fMz1oiABAXwQUAgktwAYDgAgDBBQBEgtvjnZVc" +
            "5kBt8x4TXACIFdz6R3ABgODWLpdp01v0c9nTluCXXnfqj6/J8B67k2Us6XanBV5/" +
            "b+ckGc163Dl/fo7dhVKPe+70wGvv6pogqCm4L/UGalqPV/k/g+u9+XPkkuG/Af+m" +
            "f9EC3+9ud66MJbfdpcH7H6dmj/Lgen99vr7UI+e2B177zTMzBE2+aZbz3hNcgktw" +
            "CS4ILsEluASX4BJcgktwCS7BJbgEl+ASXIKL7+2dC3BcZRXH7zAwwjg6+Bh1NN1Y" +
            "BB/jo2ixmyiPkgQoSOUhBXkWUHBqKdOmCGoR790EaHmIliLvCgoIKIwUQaFIS7Jp" +
            "LRSwtNBCCfSVDaWlpYWUpOlm/c/hRu759ty757LJzQa//8yZbrN3v73P33e+853z" +
            "rQWuBa4FrgWuBa4FrgWuBa4FrgWulQXuYAAX//8qztkkvOfCZuJ8XoIHsS52XuLD" +
            "sz+EtLpD0d4F1M7/LHMx2v+B8+9LP+2UKb/9nyP+1uQ80fSVEjmmn8GxnIZ9mOHv" +
            "RyP24xhn0W/2Kgu4BXc35ICOwfaT0V4GdhleT8e/RzjZWR95f+BrGoljm9i/r3RP" +
            "Z71TCYBl5ax6p9P5pza9afjb93GMe5YNXJx77OOPYZdQ23T8mR86be6nyskVp/uQ" +
            "oOo24fUUvK6hZ7CCgEv3cTZzis+dmbAZeH2m09b0BQtcC9xw4FLyuPdkROHHWtgE" +
            "XfK8OwvbbilRSLKLfoZ+kbtviX19PLCvJ777gDePwP/bWHt4wOXPu1/E9/wD2+RD" +
            "9mM7PdAocogFXNwL2P5sbNcZcYxvAcbXOI9c+WH1xE3WeyaivT5YK4FHKxQE4DOP" +
            "wvrENrPemwTKBe7usYGLDgvbvRSxv3lcs38BvPvH2N9j0e7qiDbbqaMY6kmzFvdb" +
            "dGz8vJq2HDY+tEPh257laMXv/eUWuMMNuPCe8LpXV3GX+WkJL2pVzAq+rQQQJXAB" +
            "hip66HgbMnBxs8O6lfvRhv04UAVcgrN3f4xjfIHBS4J3i3tljPb6YBcpvNrjsV2P" +
            "ss2FNJLRABdePdq+Ocb+7iKvD4ruwLw5Mdp0hwq4/ggwr99X90Y6Z8X358rA/fuQ" +
            "cj/3YZDHCNICdxgBF3Zv4KHE8bq/o4V7MISj1/CAjBuom3mlAhy5FwvvCjccDbfo" +
            "38x8+jvf7mWCWEngeifh8wvodSngtrpjBdjsgi2EzcX7t+PfZwWPhANX9mz/Ynxu" +
            "J+xhWigJx0kwNkEPz5W8SEkIQwjHtAp2A2ymD7dXir3TzNSIcMthtF/m8dP5dG/1" +
            "j3+Zefwq4OLzxud66bpiZIPvvQL/3iOMcPLM2zOF4xTOwTa09ze6b1q8u4XRxPLE" +
            "gYtRjeDVrofN9e/x67DPzwnHMqdEDLoH98feilDLxex6wsmxwB0uwOX2T1zwTzqG" +
            "ANeP4715xra/dgyZ3iHBLNv0JUdSW3M1W/qSzB2ngHhrwNOeT7EzAQoUO816rxpA" +
            "foQAKu93uwRxeXvvRyZIxXgdPov3nzLuxUliHJp7S1spxi0JXiLe38E6vxb3G8J3" +
            "7+1DoMA8KIRihDYPwftr2LZRwMXQ19huBQ2PxXUpvGYDTmvo78WTdQezc0Cv3SYW" +
            "X4doHiGbOY91ZkkCl8JT3ttGRztZ9l7dcXhvo7GP4835EmP0eIYjS/aKESqzMdzh" +
            "CFxAg93c0sIeAEHgQt8n3GCz2I1owkqGwjsBIFyoAK5ueIrhtvGZx5gHLcNxY0ng" +
            "IhZreG6rqEOKPsaOwPZPO6Z4zLaXPPMoAcbGfj4sPAOXmNtw75qLRiy41yXgCh3Z" +
            "mwy2pWKdGPKyNgFM4XotNr57eok2Txki4N4TK+7alvm20Tm8aDLE8NLn6bNoyE62" +
            "wB2GwKVZ9lLCBBcDmCkM/Zg3qRAb0mJYrQOue3nJWCCfyNlOcd9SAsRLAhcz8ez9" +
            "Nvdwh6QHDsvOQOzaXLtZmZ3xV8Mb3IfFV7nHupUyNEq3+ZOSwEX8nsWRn8h804EU" +
            "16MtcJ0XcSi5XzO8vAX0GcX9mChwcQ6NEM08ZbuXG/dvg3HefxUcsZBzEybcHyzc" +
            "0u8knVsYvce0XM2d03K17bCnpuXGHOAkJXghdKP4VtYSe1mvhXkfCakwevQe+frU" +
            "bX31qXbYM4VDq9KDCNz/KGePr48GrvtZevBh6iUf0bvHBG4ffU+pWXkOsdmORhiu" +
            "YvtcFHApHu2/R1DXCGEVY2h/SEjsNm8O+fX5st6UAMD25+9hMk4jLMWI7TdFA9db" +
            "yEI6+syDE4LXkN0fWe8XageJe4+1SQFXDqVgJKIRzqExZ/HbyPsDE9jK+/MWp1+N" +
            "nbVnALiFgC1whqOQ9jEkwG1InQjQFt6zEYsHD7judcrPzGHAVUgBxt6YwH1R4fmc" +
            "zW5gwClGB/t7E7jMc8RwWuGN6gVvTgo3KL3G1wKf/SObQece4+gY0LnVBK4B5C42" +
            "7NcKcwOhUOXzAxvpPOvPQUeCwL2JjRqU++l/Nss9fC42eQvPPXQSlN/XB78H3FzN" +
            "zQZwe9yCs5sFrk75utRsDtzUrkJt1V6DAtwW72dJAJdinVn3AOrB4XUyeKmAy4dx" +
            "ytnunZTYrxX3Yjhw4bkbnshEp0zxWXfALo4wYRLYn6WBjuyawN93ECi1QoqgAFxx" +
            "5ECZLDgnWuPD8cz5IZNAD8YMHz6QIHBbWApdHOF+Z3nPXKaX/45YMNPi3RbMRWZh" +
            "FwB2PgMubMqGdJUFrk4A7DwGXFhhbNW+gwTcSQMO3NbLP0YpXEgRom1bvM3SLHhc" +
            "4GKbOxT7eUOg3Q0xH4zDQoGLzsLwMOqcMsUzDjArH0c8D7Y98Pc/CH/XQudoE7hi" +
            "Fkr51hzS6VwXc5R0bVLA5Sl07p8dU/pJ3D6zapOyXKQUR57xsZ3lIAeFuO0KE7iN" +
            "G8aMcoZSi5o/RyfVN5pIqVzgthUBtyF1YCKlvXrgymEC5Esqk+2XwzYpgav3ArPu" +
            "nwLfsTKm15gOBS6GwYqJRr2QNWCcj4tiAuLqwLnbHDhf92ohJKVnhXu43vcGDrjI" +
            "7/ZlgGSmoxfFwBMDLs+DvslRSg7zwCExFEwhpEnRoOC8MGCbaYgA7KuwArcxBw1t" +
            "9oJ3jpkTqYDMwiEBbl1qeRFw66vGVTJwacYepazCA9ZDieDwCtBz/5JyEbNuit9k" +
            "euDGjMOuiTlEPUQCrgyjzHcH4DrsFHOb41+PDhaHVca8pWsoAFcGkp/o/36M5Rlj" +
            "IsgAsV6IoycFXPYL3ujUY46Up7K2pVxkhPYC23TR5L8c5251IBO4G03gTu9IHzYM" +
            "gfv8kAC3IbW62MOtPr5igYtZZ+6pkC2lWvuQdQTKAq7e63mbTW7oZtNl4KLAwPDS" +
            "jh7Y64A4XxxhJMHyYQXPF7Y1Vioj4uvhwM2MFrItyhaVPftt0jHFEcCXIHDbhNxn" +
            "bdtNLPUrrBiIF4lM6J9wZB0zOCYBd1uxh1s7zklacp6lfrKIxx7fSDCk8Eqxh1t9" +
            "asUCl+enUiyOgS4R4MoTX7SSlR5imVDgLrnsE8YDcVGMdo+gB4gsM0o6fspYEKSv" +
            "OJInvmjCSg+GWaHAxcpf/D08+FrpJ75eiunhPpcgcO9ipbxqcQ+VFuYxJReA3CuE" +
            "I3bQfgnAfasohtuZPmqIk/3HB08oTToIYl4bf8BeSBC4a4qAW5c6vWKBC8CyQgMk" +
            "ZCvbXDFwwJU9UVq+USukZkUXPnirdcUdvDqLx7QzjcJ5kx+m6Mq4PjEcgeomA4zT" +
            "Yhz/isg8XF4CfVec5Sapg/KNjlNK/Idpf+7c9wjzSQGXhwX0+0nPAjITVOEIXKvg" +
            "6MyfLMvKIwAO3K4iD7ejZryTpOQLVAhYJ63ZKkismcfDniBw1xV7uKmJlQlcPlFD" +
            "Xoc+N7N7wIGLGWAjP/MV1gHoiglk4GIRFRZiQixatWIXB+ARoWW6eKj11Us8nmzk" +
            "y77OvEYU/2jWFpYqzSIyI7rpfY2QJxz43CYa/YQXMNyg9G6vSLbwITMqajEaLUei" +
            "ytL9lfDybCUwtjKYe1QYcHcUAzd9jDPUMlfxwUWLqIF/2XxQEgTuhmIPd8RZFQtc" +
            "norURdArmbTu3knblwtc+TPNsaqtkKvL1jSQgCvHMR8QY6S8zDZrlBnvyZZ55AUM" +
            "WyibRhJ/8LsjQYPjldOwZPme1PKSwEUpr5GLe590/MJnetlShdGedZ7Fh+U2v07n" +
            "IEngQsZ60b20Jm6pKjOegrm5VOfPF2jy3mEOIjJbwoDbLcRwj6uANQSOE2bR76cy" +
            "PcS6aDiKSiVhTdcWB0oQuDlh0uzsigUuEtnNpPjIh5t7irxyq3zgUt27sDzgDLEj" +
            "wIPF1oGQgcsLDkxPB+CUYeteLaRDSbPTHBy8xNdcXWq9sf1xIZ7SNl5Om7mQ9knK" +
            "mcYkkHa1MOFc3ULXNByM6xikpKE4YttGm1vomQxvc23ii9fI/Oig/QlLQzU7cVwD" +
            "Rbx/Skg63VUOSQbuThO4UzvTJziVINz0MfMGO+jkJQvcjUIe7jkVB1wOuLe5l+Ld" +
            "AatHhdmXqTPDkJ2gx4HRxSYTsPxdWcDlYJgoLgSOmKH/kyin+cPSHAuHIJUsErgI" +
            "I3CYka2kh4lyVenBnSystdshriwGr4V5TmTU/hzKmkB7lAiPNCzu1UXHUanTk3Of" +
            "Z/irbZ0Ou8rwsJ+GdQjAZddaKGTpoDAHslJoJIjv9kc9PUWdXoiEBd37qHMDgAjI" +
            "NDR37zTi4Q8mBVw5z5msh9YWxjmlNhBCkiopYYtZh69Ye4EZnMEo4PYWebidNSc6" +
            "yUsezmIiRZecn1nAYJtYHm716wJwz63kPFwfMgWlddP3BhZTZoaJpnKBSyLAq/dp" +
            "gw/T5wXgSvHeLTHa3kaTWRG/i8W8IY0hnCEMT8UFrpW21v/FjpdDgcsXyOmMt7/u" +
            "tfTsRY58Mgu07ZGnjQyUxIGL/WQjAo2hQ6U5C6WE8/CsA0UBN1+UpdBRc5JTQaJh" +
            "G9KA/GqyDj9YvZ28HKyMxUo3KwK4IyY5SinTsdrJ0DPrfyHV/wyfaTWX7+sq+aBg" +
            "WBn4bbKt0cAlr4a+N1YlEp+YWleiY51PnhtE2Qf+90VVI+Jh308IL0jWStvqshmu" +
            "UjgC2ygMIcTzJPne8YboNnEcSPsKPOztZPhbiefnbjapI9tGWlhbO6OPnFV5gXG+" +
            "ODkdP2U/9N+T3t8TAa4/Mek7bdvFfeTe7yyWi66vTNNnmhBkJeBaqYEr5OFOdipf" +
            "FBP0i0zugmVhS/xcyxnSL0D4IYfbaciFh4aGpmUsqRk6KYbhrl+F9RhsKWwePQzl" +
            "luhiIo0W/KaOxFsEe5IAlnUv1a9UxmN/dP7QqfnwW+r/LNFc6hyNsIQeZBR/nOs7" +
            "GE/RNcG5pjUiyhB5mTSbTj871BrY3zlUkhqRCRTp8VPnTcP3LJ1XVCri9fSoTjAp" +
            "4PIFmSg8Mxf2qH9fLaRrh2uo/vmb6Lh+r4PzYYGbNHDrqs9zrKysnISBm3zIEyXZ" +
            "bEQIWeBa4FpZWeCWIWU++AQLXAtcK6sPlvAT/BUBXGT3sPQ4hMMscC1wraySVPLp" +
            "ogS65JcfCKaE0WLykAWuBa6V1fAVX+P4ZCE7ZG2CkF0Ge1zI2+5ykDljgWuBa2X1" +
            "gRBl0Mhpchc7CYmBXqjWtMC1wLWy+qACt5fWoUBF2BACt6d/ESMLXAtcK6vhL14d" +
            "OZMKoLD4OhV6JC1ax8Wb4Rd8nNVf2WqBa4FrZWWVgCxwLXCtrKyGE3Cn5tLXNHbW" +
            "TohjU3O1x2Lh8oYwm55Ljz5/fXq/C177TqJDgUJt1V6Fw6tHFg4dOapQP+IgLCzT" +
            "IBkgeWShLjUhrgGw2yxwrays9MBN3nbAFk59bUzdoEB27Of3ztenbmRr1Q6iWeBa" +
            "WVlVJnC5bXIGQYCt1w8/C1wrKysL3Hetzy2M3X3AgduQut4C18rKygKX2xJnEITf" +
            "FDvTAtfKyur/Ebh52BbYumm59KrGXPqJxlzNHfgZn8lT3kh/dNAmyzARlq9Lze5r" +
            "SD3UV1+9DCBsh22G7bDAtbKyGgr9F10BMrz/7eL3AAAAAElFTkSuQmCC";

    static private final String mitLicenseTextBase64 =
            "Q29weXJpZ2h0IChjKSBbeWVhcl0gW2NvbXBhbnldPGJyLz48YSBocmVmPSJbdXJsX" +
            "SI+W3VybF08L2E+PGJyLz48YnIvPlBlcm1pc3Npb24gaXMgaGVyZWJ5IGdyYW50ZW" +
            "QsIGZyZWUgb2YgY2hhcmdlLCB0byBhbnkgcGVyc29uIG9idGFpbmluZyBhIGNvcHk" +
            "gb2YgdGhpcyBzb2Z0d2FyZSBhbmQgYXNzb2NpYXRlZCBkb2N1bWVudGF0aW9uIGZp" +
            "bGVzICh0aGUgIlNvZnR3YXJlIiksIHRvIGRlYWwgaW4gdGhlIFNvZnR3YXJlIHdpd" +
            "GhvdXQgcmVzdHJpY3Rpb24sIGluY2x1ZGluZyB3aXRob3V0IGxpbWl0YXRpb24gdG" +
            "hlIHJpZ2h0cyB0byB1c2UsIGNvcHksIG1vZGlmeSwgbWVyZ2UsIHB1Ymxpc2gsIGR" +
            "pc3RyaWJ1dGUsIHN1YmxpY2Vuc2UsIGFuZC9vciBzZWxsIGNvcGllcyBvZiB0aGUg" +
            "U29mdHdhcmUsIGFuZCB0byBwZXJtaXQgcGVyc29ucyB0byB3aG9tIHRoZSBTb2Z0d" +
            "2FyZSBpcyBmdXJuaXNoZWQgdG8gZG8gc28sIHN1YmplY3QgdG8gdGhlIGZvbGxvd2" +
            "luZyBjb25kaXRpb25zOjxici8+PGJyLz5UaGUgYWJvdmUgY29weXJpZ2h0IG5vdGl" +
            "jZSBhbmQgdGhpcyBwZXJtaXNzaW9uIG5vdGljZSBzaGFsbCBiZSBpbmNsdWRlZCBp" +
            "biBhbGwgY29waWVzIG9yIHN1YnN0YW50aWFsIHBvcnRpb25zIG9mIHRoZSBTb2Z0d" +
            "2FyZS48YnIvPjxici8+VEhFIFNPRlRXQVJFIElTIFBST1ZJREVEICJBUyBJUyIsIF" +
            "dJVEhPVVQgV0FSUkFOVFkgT0YgQU5ZIEtJTkQsIEVYUFJFU1MgT1IgSU1QTElFRCw" +
            "gSU5DTFVESU5HIEJVVCBOT1QgTElNSVRFRCBUTyBUSEUgV0FSUkFOVElFUyBPRiBN" +
            "RVJDSEFOVEFCSUxJVFksIEZJVE5FU1MgRk9SIEEgUEFSVElDVUxBUiBQVVJQT1NFI" +
            "EFORCBOT05JTkZSSU5HRU1FTlQuIElOIE5PIEVWRU5UIFNIQUxMIFRIRSBBVVRIT1" +
            "JTIE9SIENPUFlSSUdIVCBIT0xERVJTIEJFIExJQUJMRSBGT1IgQU5ZIENMQUlNLCB" +
            "EQU1BR0VTIE9SIE9USEVSIExJQUJJTElUWSwgV0hFVEhFUiBJTiBBTiBBQ1RJT04g" +
            "T0YgQ09OVFJBQ1QsIFRPUlQgT1IgT1RIRVJXSVNFLCBBUklTSU5HIEZST00sIE9VV" +
            "CBPRiBPUiBJTiBDT05ORUNUSU9OIFdJVEggVEhFIFNPRlRXQVJFIE9SIFRIRSBVU0" +
            "UgT1IgT1RIRVIgREVBTElOR1MgSU4gVEhFIFNPRlRXQVJFLg==";
}