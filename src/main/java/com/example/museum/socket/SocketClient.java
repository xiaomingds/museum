package com.example.museum.socket;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @ClassName SocketClient
 * @Author xiaomingds
 * @Date 2021/1/13 18:17
 **/
public class SocketClient {
    public static void main(String args[]) throws Exception {
        // 要连接的服务端IP地址和端口
        String host = "10.129.0.110";//"10.129.0.110"
        int port = 8888;
        // 与服务端建立连接
        Socket socket = new Socket(host, port);
        // 建立连接后获得输出流

        OutputStream outputStream = socket.getOutputStream();
        String message="你好xiaomingds";
        //String message1="啦啦啦啦";
        socket.getOutputStream().write(message.getBytes("UTF-8"));
        socket.shutdownOutput();  //数据传输完毕,关闭socket输出流,避免服务器端read方法阻塞


        //InputStream in = socket.getInputStream();      //字节输入流,读取服务器返回的数据
        //len = in.read(data);
        //System.out.println(new String(data,0,len));

        //socket.close();
        //fis.close();

        //socket.getOutputStream().write(message1.getBytes("UTF-8"));




        //接收数据
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            sb.append(new String(bytes, 0, len,"UTF-8"));
            System.out.println("get message from server: " + sb);
            //break;
        }
        //System.out.println("get message from server: " + sb);
        inputStream.close(); //输入流关闭

        //outputStream.close();
        //socket.getOutputStream().close();

        //socket.shutdownOutput();
        socket.close();




    }
}
