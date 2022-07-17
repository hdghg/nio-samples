package com.github.hdghg.niosamples.nioclient;

import com.github.hdghg.niosamples.common.Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
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
        ByteBuffer readBuffer = ByteBuffer.allocate(Common.MESSAGE_SIZE);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        while (!"quit".equals(line)) {
            if (br.ready()) {
                line = br.readLine();
                byte[] bytes = Arrays.copyOf(line.getBytes(), Common.MESSAGE_SIZE);
                ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                socketChannel.write(byteBuffer);
            }
            while ((selector.select(125) > 0) && (Common.MESSAGE_SIZE == socketChannel.read(readBuffer))) {
                selector.selectedKeys().clear();
                System.err.println(Common.messageToString(readBuffer.array()));
                readBuffer.clear();
            }
        }
        socketChannel.close();
    }
}