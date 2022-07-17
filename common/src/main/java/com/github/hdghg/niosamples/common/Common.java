package com.github.hdghg.niosamples.common;

public interface Common {
    int MESSAGE_SIZE = 128;

    static String messageToString(byte[] array) {
        int end = array.length;
        for (int i = 0; i < array.length; i++) {
            if (0 == array[i]) {
                end = i;
                break;
            }
        }
        return new String(array, 0, end);
    }
}
