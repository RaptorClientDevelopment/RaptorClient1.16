package me.ritomg.raptor.util;

public class EnumUtils {

    public static <T extends Enum<T>> T next(T value) {
        T[] enumValues = value.getDeclaringClass().getEnumConstants();
        return enumValues[(value.ordinal() + 1) % enumValues.length];
    }
}