package com.learn.io;

import org.apache.tomcat.util.digester.DocumentProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NIOServer extends Thread {
    @Override
    public void run() {
        try(Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();){
            serverSocketChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(),8888));
            serverSocketChannel.configureBlocking(false);
            //注册到Selector，并说明关注点
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true){
                selector.select();//阻塞等待就绪的Channel，这是关键点之一
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectionKeys.iterator();
                while (iter.hasNext()){
                    SelectionKey key = iter.next();
                    //生产系统中一般会额外进行就绪状态检查
                    sayHelloWorld((ServerSocketChannel) key.channel());
                    iter.remove();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer();
        server.start();
        try (Socket client = new Socket(InetAddress.getLocalHost(),8888);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()))){
            bufferedReader.lines().forEach(System.out::println);
        }
    }
    private void sayHelloWorld(ServerSocketChannel server) throws IOException {
        try(SocketChannel client = server.accept();){
            client.write(Charset.defaultCharset().encode("Hello World!"));
        }
    }
}
