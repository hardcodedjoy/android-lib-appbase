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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

@SuppressWarnings("unused")
public class HttpsTransfer {

    // Don't forget to add:
    // <uses-permission android:name="android.permission.INTERNET" />
    // to the Manifest when using this class in your app !!!

    private String userAgent;
    private String accept;
    private String acceptLanguage;
    private String protocol;

    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public String getUserAgent() { return userAgent; }

    public void setAccept(String accept) { this.accept = accept; }
    public String getAccept() { return accept; }

    public void setAcceptLanguage(String lang) { this.acceptLanguage = lang; }
    public String getAcceptLanguage() { return acceptLanguage; }

    public void setProtocol(String protocol) { this.protocol = protocol; }
    public String getProtocol() { return protocol; }

    public byte[] getAsByteArray(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpsURLConnection c = (HttpsURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            if(userAgent != null) { c.setRequestProperty("User-Agent", userAgent); }
            if(accept != null) { c.setRequestProperty("Accept", accept); }
            if(acceptLanguage != null) { c.setRequestProperty("Accept-Language", acceptLanguage); }

            c.setUseCaches(false);
            c.setDoOutput(true);

            setSSLSocketFactory(c);

            InputStream is = c.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            readAllBytes(is, baos);
            is.close();
            c.disconnect();

            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public String getAsString(String urlString) {
        try {
            byte[] ba = getAsByteArray(urlString);
            if(ba == null) { return null; }
            //noinspection CharsetObjectCanBeUsed
            return new String(ba, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }


    public String postForString(String urlString, String name, byte[] content) {
        return postForString(urlString, new String[]{ name }, new byte[][]{ content });
    }

    public String postForString(String urlString, Vector<String> names, Vector<byte[]> contents) {
        return postForString(urlString,
                names.toArray(new String[]{}),
                contents.toArray(new byte[][]{}));
    }

    public String postForString(String urlString, String[] names, byte[][] contents) {
        byte[] ba = postForBytes(urlString, names, contents);
        return StringUtil.fromBytesUTF8(ba);
    }

    public byte[] postForBytes(String urlString, String name, byte[] content) {
        return postForBytes(urlString, new String[]{ name }, new byte[][]{ content });
    }

    public byte[] postForBytes(String urlString, Vector<String> names, Vector<byte[]> contents) {
        return postForBytes(urlString,
                names.toArray(new String[]{}),
                contents.toArray(new byte[][]{}));
    }

    @SuppressWarnings("CharsetObjectCanBeUsed")
    public byte[] postForBytes(String urlString, String[] names, byte[][] contents) {
        try {
            String boundary = Long.toHexString(System.currentTimeMillis());
            URL destinationURL = new URL(urlString);
            HttpsURLConnection c = (HttpsURLConnection) destinationURL.openConnection();
            //HttpURLConnection c = (HttpURLConnection) destinationURL.openConnection();

            c.setRequestMethod("POST");
            if(userAgent != null) { c.setRequestProperty("User-Agent", userAgent); }

            c.setDoOutput(true);
            c.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream os = c.getOutputStream();

            int n = names.length;
            String name;
            byte[] content;

            for(int i=0; i<n; i++) {
                name = names[i];
                content = contents[i];

                if(name == null) { name = ""; }
                if(content == null) { content = new byte[0]; }

                os.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
                os.write(("Content-Disposition: form-data; name=\"" + name + "\"\r\n")
                        .getBytes("UTF-8"));

                os.write(("\r\n").getBytes("UTF-8"));

                os.write(content);

                os.write(("\r\n").getBytes("UTF-8"));
                os.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
            }

            os.close();

            InputStream is = c.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            readAllBytes(is, baos);
            is.close();

            c.disconnect();

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    private void setSSLSocketFactory(HttpsURLConnection c) throws Exception {

        SSLContext context;
        if(protocol == null) {
            context = SSLContext.getInstance("TLS");
        } else {
            context = SSLContext.getInstance(protocol);
        }
        context.init(null, null, null);
        SSLSocketFactory sslSocketFactory = context.getSocketFactory();
        c.setSSLSocketFactory(sslSocketFactory);
    }

    static private void readAllBytes(InputStream is, OutputStream os) throws Exception {
        byte[] block = new byte[1024];
        int bytesRead;
        while((bytesRead = is.read(block)) != -1) {
            os.write(block, 0, bytesRead);
        }
    }
}