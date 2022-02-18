package org.apachepulsar.example.functions;

import java.util.Objects;
import java.util.function.Function;

public class InitCapFunction implements Function<String, String> {
    @Override
    public String apply(String s) {
        if (Objects.nonNull(s) && s.length() > 0) {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
        throw new IllegalArgumentException("Incoming message length is invalid");
    }
}
