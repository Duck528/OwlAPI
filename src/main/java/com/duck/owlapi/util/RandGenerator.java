package com.duck.owlapi.util;

import java.util.Random;

public class RandGenerator {
	
	private static final Random r = new Random();

	/**
	 * @param len: 란덤으로 생성될 숫자의 길이 (만약 4가 입력되면 xxxx 길이의 랜덤한 정수가 생성된다)
	 * @return 랜덤한 정수
	 */
	public static int numLenFour() {
		return 1000 + r.nextInt(9000);
	}
}
