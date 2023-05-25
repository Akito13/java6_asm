package sof3021.ca4.nhom1.asm.qls.utils;

import java.io.*;
import java.util.Base64;

public class Base64Encoder {
    public static String toString(Serializable object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception ex) {
            System.out.println("Inside toString Base64Encoder");
            ex.printStackTrace();
            return null;
        }
    }

    public static Object fromString(String s) {
        Object o = null;
        byte[] data = Base64.getDecoder().decode(s);
        try(ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            o = ois.readObject();
        } catch (Exception ex) {
            System.out.println("Inside fromString Base64Encoder");
            ex.printStackTrace();
        }
        return o;
    }
}
