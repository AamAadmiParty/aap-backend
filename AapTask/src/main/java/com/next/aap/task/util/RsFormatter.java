package com.next.aap.task.util;

public class RsFormatter {

	public static String formatAmount(String amount){
		amount = amount.replaceAll("", "");
		char[] chars = amount.toCharArray();
		boolean firstThousandMark = false;
		int count=0;
		StringBuilder sb = new StringBuilder();
		//check if any . is there
		boolean dotFound = amount.contains(".");
		for(int i=chars.length-1;i>=0;i--){
			count++;
			if(dotFound){
				if(chars[i] == '.'){
					dotFound = false;
					count = 0;
				}
			}else{
				if(!firstThousandMark){
					if(count == 4){
						sb.insert(0, ',');
						firstThousandMark = true;
						count = 0;
					}
				}else{
					if(count == 2){
						sb.insert(0, ',');
						count = 0;
					}
					
				}
			}
			sb.insert(0, chars[i]);
			
		}
		amount = sb.toString();
		return amount;
	}
}
