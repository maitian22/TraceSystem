package com.test.tracesystem.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {
	public static boolean checkPhone(String phone){
        Pattern pattern = Pattern.compile(
        		"1[1-9]{1}[0-9]{9}",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        System.out.println(matcher.matches());
		return matcher.matches();
	}
}