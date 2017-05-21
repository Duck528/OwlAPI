package com.duck.owlapi.util;

import java.util.Random;

public class RandGenerator {
	
	private static final Random r = new Random();

	/**
	 * @return ������ 4�ڸ� ����
	 */
	public static int numLenFour() {
		return 1000 + r.nextInt(9000);
	}
	
	/**
	 * @param len: ���ڿ��� ����
	 * @return ������ len ���̸� ������ ���ڿ�
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
