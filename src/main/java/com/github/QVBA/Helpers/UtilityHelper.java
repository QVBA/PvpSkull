package com.github.QVBA.Helpers;

public class UtilityHelper {
	
	public static boolean listContains(Object[] objectList, Object toCompare) {
		for(Object theObject : objectList) {
			if(theObject == toCompare) return true;
		}
		return false;
	}

}
