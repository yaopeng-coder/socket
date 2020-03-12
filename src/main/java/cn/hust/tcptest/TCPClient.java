package cn.hust.tcptest;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @program: socket
 * @author: yaopeng
 * @create: 2020-03-12 14:04
 **/
public class TCPClient {


    public static void main(String[] args) throws IOException {

        Socket socket = new Socket();

        //设置阻塞操作的超时时间 3000ms
        socket.setSoTimeout(3000);

        //设置连接的服务器地址和端口号，超时时间 3000
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000),3000);

        System.out.println("客户端已经成功连接至服务器");
        System.out.println("客户端的地址："+ socket.getLocalAddress() + "，客户端的端口号：" + socket.getLocalPort());
        System.out.println("服务器端的地址:" + socket.getInetAddress() + ",服务器端的端口号：" + socket.getPort());

        System.out.println("客户端将要开始发送信息 ：");

        try{
            sendData(socket);
        }catch (IOException e){
            System.out.println("出现异常");
        }

        socket.close();

        System.out.println("客户端已退出～");





    }


    public static  void sendData(Socket client) throws IOException {
        //从键盘上构建输入流
        InputStream inputStream = System.in;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        //socket 的输出流
        OutputStream clientOS = client.getOutputStream();
        PrintStream printStream = new PrintStream(clientOS);

        //socket的输入流
        InputStream clientIS = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(clientIS));

        boolean flag = true;
        while(flag){
            // 键盘读取一行
            String sendStr = bufferedReader.readLine();
            //发送到服务器
            printStream.println(sendStr);

            //接收到服务器端数据
            String echo = socketBufferedReader.readLine();
            if("bye".equals(echo)){
                flag = false;
            }

           System.out.println(echo);

        }

        //关闭资源
        bufferedReader.close();
        printStream.close();
        socketBufferedReader.close();


    }




}
