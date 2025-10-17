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

import com.hardcodedjoy.appbase.contentview.ContentView;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@SuppressWarnings("unused")
public class StringUtil {

	static private DecimalFormat df1 = new DecimalFormat("0.0");
	static private final Random rand = new Random(System.currentTimeMillis());

	// to be used after language changed:
	static public void initDf1() { df1 = new DecimalFormat("0.0"); }

	static public String getDelimitedPart(
			String input,
			String startDelimiter,
			String endDelimiter) {

		String s;

		if(startDelimiter == null) {
			s = input;
		} else {
			int a = input.indexOf(startDelimiter);
			if(a==-1) { return null; } // startDelimiter not found in input String

			// cut the startDelimiter:
			s = input.substring(a + startDelimiter.length());
		}

		if(endDelimiter == null) { return s; }
		
		int b = s.indexOf(endDelimiter);
		if(b==-1) { return null; } // endDelimiter not found in input String
		
		// cut the endDelimiter:
		return s.substring(0, b);
	}

	static public String getDelimitedPartIncl(
			String input,
			String startDelimiter,
			String endDelimiter) {

		if(startDelimiter == null && endDelimiter == null) { return input; }
		String delimited = getDelimitedPart(input, startDelimiter, endDelimiter);
		if(delimited == null) { return null; }
		if(startDelimiter != null) { delimited = startDelimiter + delimited; }
		if(endDelimiter != null) { delimited = delimited + endDelimiter; }
		return delimited;
	}

		static public String spaceSeparatedHexString(byte[] buf, int offset, int length) {
		StringBuilder sb = new StringBuilder();

		for(int i=0; i<length; i++) {
			if(i > 0) { sb.append(' '); }
			sb.append(String.format("%02X", (buf[offset+i])&0xFF ));
		}
		return sb.toString();
	}

	static public String spaceSeparatedHexString(byte[] buf, int length) {
		return spaceSeparatedHexString(buf, 0, length);
	}

	static public String spaceSeparatedHexString(byte[] buf) {
		return spaceSeparatedHexString(buf, 0, buf.length);
	}

	static public byte[] hexStringToByteArray(String s) {
		// remove spaces (if any)

		// replace() replaces all, not only first occurrence :)
		s = s.replace(" ", "")
				.replace("\n", "")
				.replace("\r", "")
				.replace("\t", "")
				.replace("0x", "")
				.replace(",", "")
				.replace("-", "")
				.replace(":", "")
				.replace(";", "")
				.replace(".", "");

		char[] ca = s.toCharArray();

		if(ca.length % 2 == 1) { // odd
			// add one more "0":
			s = s + "0";
			ca = s.toCharArray();

			// flip last 2 chars:
			char c = ca[ca.length-2];
			ca[ca.length-2] = ca[ca.length-1];
			ca[ca.length-1] = c;
		}

		// here ca.length is always even

		byte[] data = new byte[ca.length / 2];
		for (int i = 0; i < ca.length; i += 2) {
			data[i / 2] = (byte) ((Character.digit(ca[i], 16) << 4)
					+ Character.digit(ca[i+1], 16));
		}
		return data;
	}

	static public String millisToMMSS(long n) {

		//int millis = (int)(n%1000);
		n = n/1000;
		int ss = (int)(n%60);
		n = n/60;
		int mm = (int)(n%60);
		n = n/60;
		int hh = (int)(n);

		String st = "";

		if(hh > 0) { st = hh + ":"; }
		if(mm < 10) { st += "0"; }
		st += mm + ":";
		if(ss < 10) { st += "0"; }
		st += ss;
		return st;
	}

	static public String millisToHHMMSS(long n) {

		//int millis = (int)(n%1000);
		n = n/1000;
		int ss = (int)(n%60);
		n = n/60;
		int mm = (int)(n%60);
		n = n/60;
		int hh = (int)(n);

		String st = hh + ":";
		if(mm < 10) { st += "0"; }
		st += mm + ":";
		if(ss < 10) { st += "0"; }
		st += ss;
		return st;
	}

	static public String millisToHHMMSSmmm(long n) {

		int millis = (int)(n%1000);
		n = n/1000;
		int ss = (int)(n%60);
		n = n/60;
		int mm = (int)(n%60);
		n = n/60;
		int hh = (int)(n);

		String st = hh + ":";
		if(mm < 10) { st += "0"; }
		st += mm + ":";
		if(ss < 10) { st += "0"; }
		st += ss + ".";
		if(millis < 100) { st += "0"; }
		if(millis < 10) { st += "0"; }
		st += millis;

		return st;
	}

	static public String millisToMMSSmmm(long n) {

		int millis = (int)(n%1000);
		n = n/1000;
		int ss = (int)(n%60);
		n = n/60;
		int mm = (int)(n%60);
		n = n/60;
		int hh = (int)(n);

		String st = "";
		if(hh > 0) { st = hh + ":"; }

		if(mm < 10) { st += "0"; }
		st += mm + ":";
		if(ss < 10) { st += "0"; }
		st += ss + ".";
		if(millis < 100) { st += "0"; }
		if(millis < 10) { st += "0"; }
		st += millis;

		return st;
	}

	static public String getSizeInB(long sizeInBytes) {
		return sizeInBytes + " " + ContentView.getString(R.string.unit_bytes);
	}

	static public String getSizeInKB(long sizeInBytes) {
		return df1.format(sizeInBytes/1024.0) + " "
				+ ContentView.getString(R.string.unit_kilobytes);
	}

	static public String getSizeInMB(long sizeInBytes) {
		return df1.format(sizeInBytes/1048576.0) + " "
				+ ContentView.getString(R.string.unit_megabytes);
	}

	static public String getSizeInGB(long sizeInBytes) {
		return df1.format(sizeInBytes/1073741824.0) + " "
				+ ContentView.getString(R.string.unit_gigabytes);
	}

	static public String getSize(long sizeInBytes) {
		if(sizeInBytes >= 1024L*1024L*1024L) { return StringUtil.getSizeInGB(sizeInBytes); }
		if(sizeInBytes >= 1024*1024)         { return StringUtil.getSizeInMB(sizeInBytes); }
		if(sizeInBytes >= 1024)              { return StringUtil.getSizeInKB(sizeInBytes); }
		                                     { return StringUtil.getSizeInB (sizeInBytes); }
	}

	static public int getItemsStringId(int itemCount) {
		if("ru".equals(ContentView.getAppLanguage())) {
			if(itemCount == 0) { return R.string.items_ov; }
			if(itemCount >= 10 && itemCount <= 20) { return R.string.items_ov; }
			if((itemCount%10) == 1) { return R.string.item; }
			if((itemCount%10) >= 2 && (itemCount%10) <= 4) { return R.string.items_a; }
			return R.string.items_ov;
		}

		if(itemCount == 1) { return R.string.item; }
		return R.string.items_ov;
	}

	static public String getItemCountString(int itemCount) {
		return itemCount + " " + ContentView.getString(getItemsStringId(itemCount));
	}

	static public String trim(String s, String[] matchSequences) {
		boolean done = false;
		while(!done) {
			done = true;
			for(String seq : matchSequences) {
				if(s.startsWith(seq)) { s = s.substring(seq.length()); done = false; }
				if(s.endsWith(seq)) { s = s.substring(0, s.length() - seq.length()); done = false; }
			}
		}
		return s;
	}

	static public String trim(String s, String matchSequence) {
		return trim(s, new String[] {matchSequence});
	}

	static public String getDayDateTime() {

		Locale locale;
		if(android.os.Build.VERSION.SDK_INT > 23) {
			locale = ContentView.getActivity().getResources().getConfiguration().getLocales().get(0);
		} else {
			locale = ContentView.getActivity().getResources().getConfiguration().locale;
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", locale);
		DateFormat dayOfWeekFormat = new SimpleDateFormat("E", locale); // Sun/Mon/Tue/...
		Date date = new Date();
		String dateTime = dateFormat.format(date); //2017/08/16 10:29:51
		String dayOfWeek = dayOfWeekFormat.format(date);

		return dayOfWeek + " " + dateTime;
	}

	static public String getDateTimeFilenameFriendly() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
		return dateFormat.format(new Date()); // 20170816_102951
	}

	static public byte[] getBytesUTF8(String s) {
		if(s == null) { return null; }
		try {
			//noinspection CharsetObjectCanBeUsed
			return s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	static public String fromBytesUTF8(byte[] ba) {
		if(ba == null) { return null; }
		try {
			//noinspection CharsetObjectCanBeUsed
			return new String(ba, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	static public String join(CharSequence delimiter, CharSequence... elements) {
		StringBuilder sb = new StringBuilder();
		for (CharSequence cs: elements) {
			if(sb.length() > 0) { sb.append(delimiter); }
			sb.append(cs);
		}
		return sb.toString();
	}

	static public String randomString(boolean up, boolean low, boolean num, boolean sym, int len) {
		String alphabet = "";
		if(up) { alphabet += "QWERTYUIOPASDFGHJKLZXCVBNM"; }
		if(low) { alphabet += "qwertyuiopasdfghjklzxcvbnm"; }
		if(num) { alphabet += "1234567890"; }
		if(sym) { alphabet += "!@#$%^&*()_+-={}[]:;\"'|\\,.<>/?~`"; }
		StringBuilder sb = new StringBuilder(len);
		int x;
		for(int i=0; i<len; i++) {
			x = rand.nextInt(alphabet.length());
			sb.append(alphabet.charAt(x));
		}
		return sb.toString();
	}

	static public String findValueForKey(String key, String[] keys, String[] values) {
		if(key == null) { return null; }
		if(keys == null) { return null; }
		if(values == null) { return null; }
		if(keys.length != values.length) { return null; }
		for(int i=0; i<keys.length; i++) {
			if(key.equals(keys[i])) { return values[i]; }
		}
		return null;
	}

	static public String fillPlaceholderX(String s, String x) {
		if(s == null) { return null; }
		if(x == null) { x = ""; }
		return s.replace("[x]", x);
	}

	static public String fillPlaceholderXY(String s, String x, String y) {
		if(s == null) { return null; }
		if(x == null) { x = ""; }
		if(y == null) { y = ""; }

		String tempPlaceholderForX = "~!~#~TEMP_PLACEHOLDER_FOR_X~#~!~";
		return s.replace("[x]", tempPlaceholderForX)
				.replace("[y]", y)
				.replace(tempPlaceholderForX, x);
	}


	static public String fillPlaceholderXYZ(String s, String x, String y, String z) {
		if(s == null) { return null; }
		if(x == null) { x = ""; }
		if(y == null) { y = ""; }
		if(z == null) { z = ""; }

		String tempPlaceholderForX = "~!~#~TEMP_PLACEHOLDER_FOR_X~#~!~";
		String tempPlaceholderForY = "~!~#~TEMP_PLACEHOLDER_FOR_Y~#~!~";
		return s.replace("[x]", tempPlaceholderForX)
				.replace("[y]", tempPlaceholderForY)
				.replace("[z]", z)
				.replace(tempPlaceholderForX, x)
				.replace(tempPlaceholderForY, y);
	}

	static public String escapeBFNRT(String s) {
		if(s == null) { return null; }
		return s.replace("\\", "\\\\") // escape the backslash first !!!
				.replace("\b", "\\b")
				.replace("\f", "\\f")
				.replace("\n", "\\n")
				.replace("\r", "\\r")
				.replace("\t", "\\t");
	}

	static public String unescapeBFNRT(String s) {
		if(s == null) { return null; }
		return s.replace("\\b", "\b")
				.replace("\\f", "\f")
				.replace("\\n", "\n")
				.replace("\\r", "\r")
				.replace("\\t", "\t")
				.replace("\\\\", "\\"); // unescape the backslash last !!!
	}

	static public char removeDiacritics(char ch) {
		switch(ch) {
			case 'ă':
			case 'â':
			case 'ã':
			case 'á':
			case 'à':
			case 'ą': return 'a';
			case 'ć':
			case 'ç': return 'c';
			case 'é':
			case 'ê':
			case 'ę': return 'e';
			case 'ğ': return 'g';
			case 'î':
			case 'ı':
			case 'í':
			case 'ї': return 'i';
			case 'ł': return 'l';
			case 'ñ':
			case 'ń': return 'n';
			case 'й': return 'и';
			case 'ё': return 'е';
			case 'ó':
			case 'ô':
			case 'õ':
			case 'ö': return 'o';
			case 'ú':
			case 'ü': return 'u';
			case 'ś':
			case 'ș':
			case 'ş': return 's';
			case 'ț': return 't';
			case 'ź':
			case 'ż': return 'z';
			default: return ch;
		}
	}
	
	static public <T> String arrayValues(T[] array, int offset, int len) {
		if(array == null) { return "null"; }
		StringBuilder s = new StringBuilder("[");
		for(int i=0; i<len; i++) {
			if(i > 0) { s.append(" "); }
			s.append(array[offset + i]);
		}
		s.append("]");
		return s.toString();
	}

	static public <T> String arrayValues(T[] array) {
		return arrayValues(array, 0, array.length);
	}

	static public String formatNumber(byte b, int radix, boolean zeroPadded) {
		if(radix == 10) { return "" + b; }
		else if(radix == 16) {
			if(zeroPadded) {
				return String.format("%02X", b & 0xFF);
			} else {
				return String.format("%2X", b & 0xFF);
			}
		} else if(radix == 2) {
			String bin = Integer.toBinaryString(b & 0xFF);
			if(zeroPadded) {
				return "0".repeat(8 - bin.length()) + bin;
			} else {
				return bin;
			}
		}
		return "";
	}

	static public String formatNumber(char ch, int radix, boolean zeroPadded) {
		if(radix == 10) { return "" + (int) ch; }
		else if(radix == 16) {
			if(zeroPadded) {
				return String.format("%04X", ch & 0xFFFF);
			} else {
				return String.format("%4X", ch & 0xFFFF);
			}
		} else if(radix == 2) {
			String bin = Integer.toBinaryString(ch & 0xFFFF);
			if(zeroPadded) {
				return "0".repeat(16 - bin.length()) + bin;
			} else {
				return bin;
			}
		}
		return "";
	}

	static public String formatNumber(short sh, int radix, boolean zeroPadded) {
		if(radix == 10) { return "" + sh; }
		else if(radix == 16) {
			if(zeroPadded) {
				return String.format("%04X", sh & 0xFFFF);
			} else {
				return String.format("%4X", sh & 0xFFFF);
			}
		} else if(radix == 2) {
			String bin = Integer.toBinaryString(sh & 0xFFFF);
			if(zeroPadded) {
				return "0".repeat(16 - bin.length()) + bin;
			} else {
				return bin;
			}
		}
		return "";
	}

	static public String formatNumber(int i, int radix, boolean zeroPadded) {
		if(radix == 10) { return "" + i; }
		else if(radix == 16) {
			if(zeroPadded) {
				return String.format("%08X", i);
			} else {
				return String.format("%8X", i);
			}
		} else if(radix == 2) {
			String bin = Integer.toBinaryString(i);
			if(zeroPadded) {
				return "0".repeat(32 - bin.length()) + bin;
			} else {
				return bin;
			}
		}
		return "";
	}

	static public String formatNumber(long l, int radix, boolean zeroPadded) {
		if(radix == 10) { return "" + l; }
		else if(radix == 16) {
			if(zeroPadded) {
				return String.format("%016X", l);
			} else {
				return String.format("%16X", l);
			}
		} else if(radix == 2) {
			String bin = Long.toBinaryString(l);
			if(zeroPadded) {
				return "0".repeat(64 - bin.length()) + bin;
			} else {
				return bin;
			}
		}
		return "";
	}
}