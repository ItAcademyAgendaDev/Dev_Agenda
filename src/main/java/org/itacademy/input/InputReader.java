package org.itacademy.input;

public interface InputReader {
    String readString(String message);
    int readInt(String message);
    boolean readYesNo(String message);
}