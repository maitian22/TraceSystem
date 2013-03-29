package com.test.tracesystem.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	/***
	 * 调试用的toast
	 * 
	 * @param context
	 * @param text
	 */
	public static void i(Context context, String text) {
		if(context==null || text==null || text.equals("")){
			return;
		}
		if (Constant.ISDEBUG)
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/***
	 * 系统正常的toast
	 * 
	 * @param context
	 * @param text
	 */
	public static void s(Context context, String text) {
		if(context==null || text==null || text.equals("")){
			return;
		}
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}