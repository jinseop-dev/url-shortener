package me.jinseoplee.urlshortener.domain.url.util;

public final class Base62Encoder {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = 62;

    private Base62Encoder() {
    }

    public static String encode(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }

        if (id == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        while (id > 0) {
            int r = (int) (id % BASE);
            sb.append(BASE62.charAt(r));
            id /= BASE;
        }

        return sb.reverse().toString();
    }
}
