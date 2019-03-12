package com.mapscene.iot_show.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: qh
 * @Date: 2018/10/27 17:41
 * @Description:
 * 从计算机网络的角度来解析这段代码里的通信流程
 * 一、JAVA底层封装了TCP通信协议接口的实现，当new ServerSocket(portNumber)时，就将当前主机的8888端口打开了，
 * 这个时候客户端其实就可以发起请求，不过此时发起的请求归操作系统管，java并不会对其进行处理，而是将当前的连接请求放到请求队列中
 * （请求队列的长度和超时时间都可以配置），直到当前的serverSocket调用accept()方法才会从队列中取出一个最先加入的请求进行处理
 *
 * 二、客户端和服务器端互相使用流来发送消息，这里使用的发送消息的流是带缓冲区的BufferedWriter，字符输出流。而写入结束，
 * 进行发送的标志是调用flush()方法，这是一个很有意思过程，我们知道flush的作用是将当前缓冲区中的内容输出到目的地，如果写入的大小
 * 超过了缓冲区则自动进行flush。那么如果使用不带缓冲区的流是不是就直接输出到指定目标呢？这里暂不做研究，以后有空可以试试。
 *
 * 三、在发送消息之后，另外一端通过当前的BufferedReader的readline方法读取到换行符获取字符串，接着输出到控制台，这个过程如果
 * 没有读取到换行符则此方法会一直堵塞代码。
 *
 * 四、服务器端代码执行结束，服务器关闭（和severSocket的close()方法效果相同）
 *
 * socket确实是tcp支持的全双工长连接，如果只是让客户端反复的new socket()来请求，然后每次在服务器端使用循环不停的accept来获取
 * 客户端请求，然后每次只交换一次数据，当然可以做到即时的通讯，但是这样开销是极大的，并且服务器端无法对客户的访问进行持久化处理，
 * 简单的说，这就像HTTP协议，只能进行短连接，彼此互相收发一次数据就拜拜了，并不是长连接
 *
 *
 */
public class ModbusHelper {

    /**
     * 查询信息
     * @param ip 要连接的服务端IP地址
     * @param port 监听指定的端口
     * @param message 待发送数据
     * @param type
     * @return
     */
    public static Object queryDeviceInfo(String ip, int port, String message, int type) {
        String strOutput = "";
        // 与服务端建立连接
        try {
            Socket socket = new Socket(ip, port);
            //校验码
            String crc = SerialTool.Make_CRC(SerialTool.ConvertByte(message));
            socket.setKeepAlive(true);
            //发送数据,客户端输出流作为服务器的输入
            OutputStream socketWriter = socket.getOutputStream();
            socketWriter.write(SerialTool.ConvertByte(message.concat(" " + crc.substring(0,2) + " " + crc.substring(2))));
            socketWriter.flush();
            Thread.sleep(1000);

            //服务器的输出即为客户端的输入，这里主要是为了把服务器输出的字节流报文转化成字符串，方便进行解析，最终测试报文的正确性
            InputStream socketReader = socket.getInputStream();
            byte[] temp = new byte[100];
            int bytes = 0;
            /* read从输入流socketReader中读取temp（6）数量的字节数，并将它们存储到缓冲区数组temp。实际读取的字节数作为一个整数6返回。
             * 此方法块，直到输入数据可用，检测到文件结束，或抛出异常。如果B的长度为零，则没有读取字节数和返回0；
             * 否则，将有一个至少一个字节的尝试。如果没有可用的字节，因为流是在文件的结尾，值- 1返回；否则，至少一个字节被读取和存储到temp。
             */
            int available = socketReader.available();
            bytes = socketReader.read(temp);
            strOutput += ByteUtil.BinaryToHexString(temp);
            socket.close();
            return getContent(strOutput, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取报文体的内容
     * @param str
     * @return
     */
    private static Object getContent(String str, int type) {
        String[] order = str.split(" ");
        if (order.length > 2) {
            if (type == 1 || type == 2 || type == 7 || type == 8) {
                return (int) Long.parseLong(order[3], 16);
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append(order[3]);
                sb.append(order[4]);
                long value = Long.parseLong(sb.toString(), 16);
                if (type == 3) {
                    return value;
                }
                if (type == 6) {
                    return value / (double)100;
                }
                return value / (double)10;
            }
        }
        return 0;
    }


    public static Object open(String ip, int port, String message) {
        String strOutput = "";
        // 与服务端建立连接
        try {
            Socket socket = new Socket(ip, port);
            //校验码
            String crc = SerialTool.Make_CRC(SerialTool.ConvertByte(message));
            socket.setKeepAlive(true);
            //发送数据,客户端输出流作为服务器的输入
            OutputStream socketWriter = socket.getOutputStream();
            socketWriter.write(SerialTool.ConvertByte(message.concat(" " + crc.substring(0,2) + " " + crc.substring(2))));
            socketWriter.flush();
            Thread.sleep(1000);

            //服务器的输出即为客户端的输入，这里主要是为了把服务器输出的字节流报文转化成字符串，方便进行解析，最终测试报文的正确性
            InputStream socketReader = socket.getInputStream();
            byte[] temp = new byte[6];
            int bytes = 0;
            /* read从输入流socketReader中读取temp（6）数量的字节数，并将它们存储到缓冲区数组temp。实际读取的字节数作为一个整数6返回。
             * 此方法块，直到输入数据可用，检测到文件结束，或抛出异常。如果B的长度为零，则没有读取字节数和返回0；
             * 否则，将有一个至少一个字节的尝试。如果没有可用的字节，因为流是在文件的结尾，值- 1返回；否则，至少一个字节被读取和存储到temp。
             */
            bytes = socketReader.read(temp);
            strOutput += ByteUtil.BinaryToHexString(temp);
            socket.close();
            return strOutput.equals(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        long d = Long.parseLong("0462", 16);
        int i = Integer.parseInt("188", 16);
        System.out.println(d/(double)10);
        System.out.println(i);
    }

    /**
     * 从192.168.1.204:4196/192.168.1.205：8899 获取数据
     * @param socket
     * @param messages
     * @return
     */
    public static Map<String, Object> query(Socket socket, Map<String, Object> messages) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (String key : messages.keySet()) {
            //发送命令
            String msg = String.valueOf(messages.get(key));
            //校验码
            String crc = SerialTool.Make_CRC(SerialTool.ConvertByte(msg));
            //发送数据,客户端输出流作为服务器的输入
            OutputStream socketWriter = null;
            try {
                socketWriter = socket.getOutputStream();
                socketWriter.write(SerialTool.ConvertByte(msg.concat(" " + crc.substring(0,2) + " " + crc.substring(2))));
                socketWriter.flush();
                Thread.sleep(1000);

                //服务器的输出即为客户端的输入，这里主要是为了把服务器输出的字节流报文转化成字符串，方便进行解析，最终测试报文的正确性
                InputStream socketReader = socket.getInputStream();
                int available = socketReader.available();
                byte[] temp = new byte[available];
                int bytes = 0;
                /* read从输入流socketReader中读取temp（6）数量的字节数，并将它们存储到缓冲区数组temp。实际读取的字节数作为一个整数6返回。
                 * 此方法块，直到输入数据可用，检测到文件结束，或抛出异常。如果B的长度为零，则没有读取字节数和返回0；
                 * 否则，将有一个至少一个字节的尝试。如果没有可用的字节，因为流是在文件的结尾，值- 1返回；否则，至少一个字节被读取和存储到temp。
                 */
                bytes = socketReader.read(temp);
                String strOutput = ByteUtil.BinaryToHexString(temp);
                map.put("value" + key, getContent(strOutput, Integer.valueOf(key)));
               /* socketWriter.close();
                socketReader.close();*/
                socketWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 打开连接
     * @param ip
     * @param port
     * @return
     */
    public static Socket openSocket(String ip, int port) {
        // 与服务端建立连接
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            socket.setKeepAlive(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}
