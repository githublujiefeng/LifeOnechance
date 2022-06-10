package com.learn.io;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoServer extends Thread {
    private ServerSocket serverSocket;
    public int getPort() {
        return serverSocket.getLocalPort();
    }
    public void run() {
        System.out.println("启动DemoServer");
        try {
            serverSocket = new ServerSocket(0);
            ExecutorService executors = Executors.newFixedThreadPool(8);
            while (true) {
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
                //requestHandler.start();
                executors.execute(requestHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        DemoServer server = new DemoServer();
        server.start();
        try (Socket client = new Socket(InetAddress.getLocalHost(), server.getPort());
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()))){
                bufferedReader.lines().forEach(s -> System.out.println(s));
             }
    }
}

// 简化实现，不做读取，直接发送字符串
class RequestHandler extends Thread {
    private Socket socket;
    RequestHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        System.out.println("启动RequestHandler");
        try (PrintWriter out = new PrintWriter(socket.getOutputStream());) {
            out.println("Hello world!");
            out.println("dd");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

