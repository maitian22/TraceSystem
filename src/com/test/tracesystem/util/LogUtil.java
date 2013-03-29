package com.test.tracesystem.util;

import android.util.Log;

public class LogUtil {

	public static void out(String tag, String text) {
		if (Constant.ISDEBUG)
			Log.i(tag, text);
	}

	public static void out(String tag, int text) {
		if (Constant.ISDEBUG)
			Log.i(tag, text + "");
	}

}
