package com.example.museum.socket;

import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @ClassName SocketService
 * @Author xiaomingds
 * @Date 2021/1/19 20:21
 **/
@Service
public class SocketService {

    public void PostMessage(String message) {
        try {

            // 要连接的服务端IP地址和端口
            String host = "10.129.0.110";//"10.129.0.110"
            int port = 8888;
            // 与服务端建立连接
            Socket socket = new Socket(host, port);
            // 建立连接后获得输出流

            OutputStream outputStream = socket.getOutputStream();
            //String message1="啦啦啦啦";
            socket.getOutputStream().write(message.getBytes("UTF-8"));
            socket.shutdownOutput();  //数据传输完毕,关闭socket输出流,避免服务器端read方法阻塞

            //Socket s = new Socket("10.129.0.110", 8888);


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


