package com.example.museum.socket;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.Socket;

/**
 * @ClassName SocketService
 * @Author xiaomingds
 * @Date 2021/1/19 20:21
 **/
@Service
public class  SocketService {
   String host = "0.129.0.110";//"10.129.0.110"
   int port = 9035;
   public String PostMessage(String message){
      Socket socket = null;
      StringBuilder result = new StringBuilder();

      try {
         System.out.println("开始连接ip-----> " +  host);

         socket = new Socket(host, port);
      } catch (IOException e) {
         e.printStackTrace();
      }
      try {
         OutputStream outputStream = socket.getOutputStream();
         System.out.println("发送message----->  " +  message);
         socket.getOutputStream().write(message.getBytes("UTF-8"));
         socket.shutdownOutput();  //数据传输完毕,关闭socket输出流,避免服务器端read方法阻塞

         //接收数据
         InputStream inputStream = socket.getInputStream();
         byte[] bytes = new byte[1024];
         int len;

         while ((len = inputStream.read(bytes)) != -1) {
            //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
            result.append(new String(bytes, 0, len,"UTF-8"));
         }
         System.out.println("反馈的消息------>" + result);
         inputStream.close(); //输入流关闭
      } catch (IOException e) {
         e.printStackTrace();
      }
     return String.valueOf(result);
   }
}
