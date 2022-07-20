package com.github.hdghg.niosamples.ioclient;

import com.github.hdghg.niosamples.common.Common;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] buffer = new byte[128];
        Charset consoleEncoding = Common.guessConsoleEncoding();
        System.out.println("Console encoding detected as " + consoleEncoding);
        try (Socket socket = new Socket("127.0.0.1", 7171);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(System.in, consoleEncoding);
             BufferedReader br = new BufferedReader(inputStreamReader)
        ) {
            String s = "";
            while (!"quit".equals(s)) {
                while (0 < inputStream.available()) {
                    inputStream.read(buffer);
                    System.out.println(Common.messageToString(buffer));
                }
                s = br.readLine();
                byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
                outputStream.write(Arrays.copyOf(bytes, 128));
            }
        }
    }
}