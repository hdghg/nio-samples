package com.github.hdghg.niosamples.ioclient;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] buffer = new byte[128];
        try (Socket socket = new Socket("127.0.0.1", 7171);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(System.in);
             BufferedReader br = new BufferedReader(inputStreamReader)
        ) {
            String s = "";
            while (!"quit".equals(s)) {
                while (0 < inputStream.available()) {
                    inputStream.read(buffer);
                    int end = 128;
                    for (int i = 0; i < 128; i++) {
                        if (0 == buffer[i]) {
                            end = i;
                            break;
                        }
                    }
                    System.out.println(new String(buffer, 0, end, StandardCharsets.ISO_8859_1));
                }
                s = br.readLine();
                byte[] bytes = s.getBytes(StandardCharsets.ISO_8859_1);
                outputStream.write(Arrays.copyOf(bytes, 128));
            }
        }
    }
}