package model.helper;

import java.util.Random;

public class RandomNumber {
	public static String getNumber() {
		Random rnd = new Random();
	    int rawNumber = rnd.nextInt(99999);
	    return String.format("%05d", rawNumber);
	}
}
