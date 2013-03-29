package com.test.tracesystem.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {
	public static boolean checkEmail(String email){
        Pattern pattern = Pattern.compile(
        		"[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        System.out.println(matcher.matches());
		return matcher.matches();
	}
}
