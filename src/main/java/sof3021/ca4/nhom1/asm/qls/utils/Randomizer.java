package sof3021.ca4.nhom1.asm.qls.utils;

import java.util.Random;

public class Randomizer {
    public static String random(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
