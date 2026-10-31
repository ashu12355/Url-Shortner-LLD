package com.example.url_shortener.utility;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {

    private static final String CHARACTERS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public String convertIntoBase62Encoder(Long id) {

        if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid Id");
        }
        if (id == 0) {
            return "0";
        }

        StringBuilder shortCode = new StringBuilder();

        char ch = 0;
        while (id > 0) {

            Long position = id % 62;

            ch = CHARACTERS.charAt((Math.toIntExact(position)));
            shortCode.append(ch);
            id = id / 62;
        }

          return String.valueOf(shortCode.reverse());


    }
}
