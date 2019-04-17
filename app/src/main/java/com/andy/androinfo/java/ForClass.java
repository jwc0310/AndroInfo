package com.andy.androinfo.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * java反射
 */
public class ForClass {
    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    public static void main(String args[]) throws Exception {

        System.out.println(Bean.INT_VALUE);
        setFinalStatic(Bean.class.getField("INT_VALUE"), 200);
        System.out.println(Bean.INT_VALUE);

        System.out.println("------------------");
        System.out.println(Bean.STRING_VALUE);
        setFinalStatic(Bean.class.getField("STRING_VALUE"), "String_2");
        System.out.println(Bean.STRING_VALUE);

        System.out.println("------------------");
        System.out.println(Bean.BOOLEAN_VALUE);
        setFinalStatic(Bean.class.getField("BOOLEAN_VALUE"), true);
        System.out.println(Bean.BOOLEAN_VALUE);

        System.out.println("------------------");
        System.out.println(Bean.OBJECT_VALUE);
        setFinalStatic(Bean.class.getField("OBJECT_VALUE"), new Date());
        System.out.println(Bean.OBJECT_VALUE);

    }
}

class Bean {
    public static final int INT_VALUE = 100;
    public static final Boolean BOOLEAN_VALUE = false;
    public static final String STRING_VALUE = "String_1";
    public static final Object OBJECT_VALUE = "234";
}
