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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrentDateTimeReplacer {

    static private final String regex = "\\[(.*?)\\]";
    static private final Pattern pattern = Pattern.compile(regex);

    static public String process(String input) {
        Matcher matcher = pattern.matcher(input);
        StringBuffer substituted = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group(1);
            if(match == null) { continue; }
            String replacement = getReplacement(match);
            matcher.appendReplacement(substituted, replacement);
        }
        matcher.appendTail(substituted);
        return substituted.toString();
    }

    static private String getReplacement(String s) {
        DateFormat dateFormat = new SimpleDateFormat(s, Locale.US);
        return dateFormat.format(new Date());
    }
}