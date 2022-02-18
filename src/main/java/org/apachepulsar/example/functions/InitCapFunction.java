package org.apachepulsar.example.functions;

import java.util.function.Function;

public class InitCapFunction implements Function<String, String> {
    @Override
    public String apply(String s) {
        String s1 = Character.toUpperCase(s.charAt(0)) + s.substring(1);
        return s1;
    }
}
