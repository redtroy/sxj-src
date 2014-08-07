package com.sxj.supervisor.manage.page.tag.support;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UtilFactory {
	public static String UrlEncode(String s, String enc) {
		// StringBuffer out = new StringBuffer(s.length());
		// StringBuffer outtemp = new StringBuffer();
		// ByteArrayOutputStream buf = new ByteArrayOutputStream();
		// OutputStreamWriter writer = null;
		// try {
		// writer = new OutputStreamWriter(buf, enc);
		// } catch (UnsupportedEncodingException ex) {
		// writer = new OutputStreamWriter(buf);
		// }
		// for (int i = 0; i < s.length(); i++) {
		// int c = s.charAt(i);
		// if (isSafeChar(c)) {
		// out.append((char) c);
		// } else {
		// try {
		// writer.write(c);
		// writer.flush();
		// } catch (IOException e) {
		// buf.reset();
		// continue;
		// }
		// }
		// }
		// byte[] ba = buf.toByteArray();
		// for (int j = 0; j < ba.length; j++) {
		// out.append('%');
		// out.append(Character.forDigit((ba[j] >> 4) & 0xf, 16));
		// out.append(Character.forDigit(ba[j] & 0xf, 16));
		// }
		// buf.reset();
		String out = "";
		try {
			out = URLEncoder.encode(s, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return out;
	}

	// private static boolean isSafeChar(int c) {
	// if (c >= 'a' && c <= 'z') {
	// return true;
	// }
	// if (c >= 'A' && c <= 'Z') {
	// return true;
	// }
	// if (c >= '0' && c <= '9') {
	// return true;
	// }
	// if (c == '-' || c == '_' || c == '.' || c == '!' || c == '~'
	// || c == '*' || c == '\'' || c == '(' || c == ')') {
	// return true;
	// }
	// return false;
	// }
}
