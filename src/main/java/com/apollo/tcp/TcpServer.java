package com.apollo.tcp;

import com.apollo.position.RangingUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TcpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12900, 100,
                InetAddress.getByName("localhost"));
        System.out.println("Server started  at:  " + serverSocket);

        while (true) {
            System.out.println("Waiting for a  connection...");

            final Socket activeSocket = serverSocket.accept();

            System.out.println("Received a  connection from  " + activeSocket);
            Runnable runnable = () -> sendRangingMessage(activeSocket);
            new Thread(runnable).start(); // start a new thread
        }
    }

    public static void handleClientRequest(Socket socket) {
        try{
            BufferedReader socketReader = null;
            BufferedWriter socketWriter = null;
            socketReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            socketWriter = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()));

            String inMsg = null;
            while ((inMsg = socketReader.readLine()) != null) {
                System.out.println("Received from  client: " + inMsg);

                String outMsg = inMsg;
                socketWriter.write(outMsg);
                socketWriter.write("\n");
                socketWriter.flush();
            }
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void sendRangingMessage(Socket socket){
        OutputStream outputStream = null;

        try {
            while (true) {
                double[][] anchor = {{3, 4, 12}, {1, 2, 2}, {4, 4, 2}, {0, 4, 3}};
                double[] tag = {0, 0, 0};
                String rangingData = RangingUtil.ranging(anchor, tag, 0.1, 0, 0.15, RangingUtil.RangingMode.LOS);
                System.out.println(socket.getRemoteSocketAddress() + "  " + rangingData);
                outputStream = socket.getOutputStream();
                outputStream.write(rangingData.getBytes());
                outputStream.flush();
                Thread.sleep(200);
            }

        } catch (Exception e) {
            // e.printStackTrace();
            if (e instanceof SocketException) {
                System.err.println("软件导致连接中止：套接字写入错误");
            }
        } finally {
            try {
                if (outputStream != null) outputStream.close();
                if (socket != null)  socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
