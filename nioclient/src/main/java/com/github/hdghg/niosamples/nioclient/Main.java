package com.github.hdghg.niosamples.nioclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 7171);
        SocketChannel socketChannel = SocketChannel.open(hostAddress);
        socketChannel.configureBlocking(false);
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while (!"quit".equals(line)) {
            if (br.ready()) {
                line = br.readLine();
                byte[] bytes = Arrays.copyOf(line.getBytes(), 128);
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                socketChannel.write(byteBuffer);
            }
            ByteBuffer readBuffer = ByteBuffer.allocate(128);
            while (128 == socketChannel.read(readBuffer)) {
                byte[] array = readBuffer.array();
                int end = 0;
                for (int i = 0; i < array.length; i++) {
                    if (0 == array[i]) {
                        end = i;
                        break;
                    }
                }
                System.err.println(new String(array, 0, end));
            }
        }
        socketChannel.close();
    }
}