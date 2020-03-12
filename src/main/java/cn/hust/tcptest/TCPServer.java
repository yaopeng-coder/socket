package cn.hust.tcptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: socket
 * @author: yaopeng
 * @create: 2020-03-12 14:43
 **/
public class TCPServer {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket();

        server.bind(new InetSocketAddress(Inet4Address.getLocalHost(),2000));
        System.out.println("服务器端地址:"+ server.getLocalSocketAddress() + ",服务器端端口号:" + server.getLocalPort());


        System.out.println("服务端已经准备就绪");

        for(;;){
            //循环接收客户端连接
            Socket client = server.accept();

            //异步处理每个客户端的连接
            new Thread(new ClientHandler(client)).start();


        }

    }


      static class ClientHandler implements Runnable{

        private Socket client;

        ClientHandler(Socket client){
            this.client = client;
        }


          //每个客户端连接的处理逻辑
         public void run() {

             System.out.println("新客户端连接：" + client.getInetAddress() + " P:" + client.getPort());

             try {
                 //每个客户端连接的输入流
                 BufferedReader clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));

                 //每个客户端连接的输出流
                 PrintStream clientOutput = new PrintStream(client.getOutputStream());

                 while(true){
                     //接收客户端发送的数据
                     String string = clientInput.readLine();

                     if("bye".equals(string)){
                         clientOutput.println("bye");
                         break;
                     }else {
                         System.out.println(string);
                         clientOutput.println("回送数据长度" + string.length());
                     }
                 }

                 clientInput.close();
                 clientOutput.close();

             } catch (IOException e) {
                System.out.println("客户端连接出现异常");
             }finally {
                 try {
                     client.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }

             System.out.println("客户端退出：" + client.getInetAddress() + " P:" + client.getPort());


         }
    }
}
