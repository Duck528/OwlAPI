package com.duck.owlapi.util;

import java.util.Random;

public class RandGenerator {
	
	private static final Random r = new Random();

	/**
	 * @param len: �������� ������ ������ ���� (���� 4�� �ԷµǸ� xxxx ������ ������ ������ �����ȴ�)
	 * @return ������ ����
	 */
	public static int numLenFour() {
		return 1000 + r.nextInt(9000);
	}
}
