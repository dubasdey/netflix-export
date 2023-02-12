package org.erc;

public class Utils {

    private static String getBlock(String input, int i, int l) {
        if (i + l > input.length()) {
            throw new RuntimeException("Not enough unicode digits! ");
        }
        StringBuilder hex = new StringBuilder();
        for (char x : input.substring(i, i + l).toCharArray()) {
            if (!Character.isLetterOrDigit(x)) {
                throw new RuntimeException("Bad character in unicode escape.");
            }
            hex.append(Character.toLowerCase(x));
        }
        return hex.toString();
    }

    public static String unescape(String input) {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        while (i < input.length()) {
            char delimiter = input.charAt(i);
            i++; // consume letter or backslash

            if (delimiter == '\\' && i < input.length()) {

                // consume first after backslash
                char ch = input.charAt(i);
                i++;

                if (ch == '\\' || ch == '/' || ch == '"' || ch == '\'') {
                    builder.append(ch);
                } else if (ch == 'n') {
                    builder.append('\n');
                } else if (ch == 'r') {
                    builder.append('\r');
                } else if (ch == 't') {
                    builder.append('\t');
                } else if (ch == 'b') {
                    builder.append('\b');
                } else if (ch == 'f') {
                    builder.append('\f');
                } else if (ch == 'x') {
                    int code = Integer.parseInt(getBlock(input, i, 2), 16);
                    i += 2; // consume those 2 digits.
                    builder.append((char) code);
                } else if (ch == 'u') {
                    int code = Integer.parseInt(getBlock(input, i, 4), 16);
                    i += 4; // consume those 4 digits.
                    builder.append((char) code);
                } else {
                    throw new RuntimeException("Illegal escape sequence: \\" + ch);
                }
            } else { // it's not a backslash, or it's the last character.
                builder.append(delimiter);
            }
        }
        return builder.toString();
    }
}
