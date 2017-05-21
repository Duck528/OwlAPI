package com.duck.owlapi.util;

import java.util.Random;

public class RandGenerator {
	
	private static final Random r = new Random();

	/**
	 * @return 랜덤한 4자리 정수
	 */
	public static int numLenFour() {
		return 1000 + r.nextInt(9000);
	}
	
	/**
	 * @param len: 문자열의 길이
	 * @return 랜덤한 len 길이를 가지는 문자열
	 */
	public static String charsWithLen(int len) {
		Random r = new Random();
		StringBuilder builder = new StringBuilder();
		
		for (int i=0; i<len; i++) {
			builder.append((char)(r.nextInt(26) + 97));
		}
		return builder.toString();
	}
}
