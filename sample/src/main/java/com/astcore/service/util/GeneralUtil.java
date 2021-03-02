package com.astcore.service.util;

/**
 * Utility class for generating random Strings.
 */
public final class GeneralUtil {

    public static boolean tryConvertStrToDecimal(String input) {
    	try {
    		Double.parseDouble(input);
    	} catch (Exception e) {
			return false;
		}
    	return true;
    }
}
