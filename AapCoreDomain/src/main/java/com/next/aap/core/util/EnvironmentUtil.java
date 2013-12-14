package com.next.aap.core.util;

public class EnvironmentUtil {

	public static boolean isProductionEnv() {
		if (System.getenv("prod") != null && System.getenv("prod").equals("true")) {
			return true;
		}
		return false;
	}
}
