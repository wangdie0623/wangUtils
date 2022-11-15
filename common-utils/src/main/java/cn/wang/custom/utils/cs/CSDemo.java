package cn.wang.custom.utils.cs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

//CS模式通信样例
public class CSDemo {

    //默认端口
    public static Integer DEFAULT_PORT = 8080;
    //在库书籍
    public static List<String> BOOKS= Arrays.asList("红楼梦","西游记","三国演义","水浒传");

    //Socket通信工具类
    public static class SocketMsgUtils {
        //发送消息
        public static void sendMsg(Socket socket, String msg) {
            if (msg==null||msg.isEmpty()) {
                return;
            }
            OutputStream out = null;
            try {
                out = socket.getOutputStream();
                byte[] data = msg.getBytes("UTF-8");
                out.write(data);
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //接收消息
        public static String receiveMsg(Socket socket) {
            InputStream input = null;
            try {
                input = socket.getInputStream();
                byte[] bytes = new byte[1024];
                StringBuilder builder = new StringBuilder();
                for (int len = input.read(bytes); len != -1; len = input.read(bytes)) {
                    builder.append(new String(bytes, 0, len, "UTF-8"));
                }
                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("接收消息异常!");
        }

        //请求加响应
        public static String reqAndResp(Socket socket, String reqMsg) {
            if (reqMsg==null||reqMsg.isEmpty()) {
                return null;
            }
            OutputStream out = null;
            InputStream input = null;
            try {
                out = socket.getOutputStream();
                input = socket.getInputStream();
                sendMsg(socket,reqMsg);
                return receiveMsg(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                    }
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                }
            }
            return null;
        }
    }

    //客户端
    public static class Client {
        private static final int CONNECT_TIME_OUT = 5000;
        public Integer port;//服务端端口
        public String host;//服务端ip

        public Client(Integer port, String host) {
            this.port = port;
            this.host = host;
        }

        //发送消息
        public String sendMsg(String msg) {
            try {
                Socket socket = new Socket(host, port);
                socket.setSoTimeout(CONNECT_TIME_OUT);
                return SocketMsgUtils.reqAndResp(socket,msg);
            } catch (IOException e) {
                return null;
            }
        }

        public static void main(String[] args) {
            Client client = new Client(DEFAULT_PORT, "127.0.0.1");
            System.out.println(client.sendMsg("红楼梦"));
        }
    }

    //服务端
    public static class Server {
        public Integer port;//提供服务端端口
        private ServerSocket server;//服务端对象

        public Server(Integer port) throws IOException {
            this.port = port;
            this.server = new ServerSocket(port);
        }
        //启动服务
        public void start() {
            int count = 1;
            while (true) {
                try {
                    Socket accept = server.accept();
                    if (accept != null) {
                        String msg = SocketMsgUtils.receiveMsg(accept);
                        System.out.println("接收到请求信息：" + msg);
                        //添加处理请求信息逻辑
                        if (BOOKS.contains(msg)){
                            SocketMsgUtils.sendMsg(accept, "找到了：" + msg);
                        }else{
                            SocketMsgUtils.sendMsg(accept, "查无此书");
                        }
                        accept.close();
                    } else {
                        count++;
                    }
                    if (count > 10) {
                        Thread.sleep(500);
                        count = 1;
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) throws IOException {
            Server server = new Server(DEFAULT_PORT);
            server.start();
        }
    }


}
