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
            Socket s = new Socket("127.0.0.1", 8888);

            OutputStream os = s.getOutputStream();

            //把输出流封装在DataOutputStream中
            DataOutputStream dos = new DataOutputStream(os);
            //使用writeUTF发送字符串
            dos.writeUTF(message);
            dos.close();
            s.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


