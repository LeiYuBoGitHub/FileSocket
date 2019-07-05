package com.lyb.server;

import com.lyb.constant.Constant;

import java.io.*;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

/**
 * @Auther: 野性的呼唤
 * @Date: 2018/9/11 13:50
 * @Description:
 */
public class Server {

    private static final String FILE_SAVE_PATH  = "static/file/server";

    private static DecimalFormat df;

    static {
        // 设置数字格式，保留一位有效小数
        df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
    }


    public static void main(String[] args){
        start();
    }

    private static void start() {

        ServerSocket serverSocket;

        Socket socket = null;

        DataInputStream dataInputStream = null;

        FileOutputStream fileOutputStream = null;

        try {

            serverSocket = new ServerSocket(Constant.PORT);

            while (true) {

                socket = serverSocket.accept();
                System.out.println("客户端已连接......");

                //接收信息
                InputStream inputStream = socket.getInputStream();
                dataInputStream = new DataInputStream(inputStream);

                String fileName = dataInputStream.readUTF();
                long fileLength = dataInputStream.readLong();

                File file = new File(FILE_SAVE_PATH + File.separatorChar + fileName);
                fileOutputStream = new FileOutputStream(file);

                //开始接收文件
                byte[] bytes = new byte[1024];
                int length;
                while((length = dataInputStream.read(bytes, 0, bytes.length)) != -1) {
                    fileOutputStream.write(bytes, 0, length);
                    fileOutputStream.flush();
                }

                System.out.println("========文件接收成功========");
                System.out.println("文件名称:" + fileName);
                System.out.println("文件大小:" + getFormatFileSize(fileLength));
            }

        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {

                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }

                if (dataInputStream != null) {
                    dataInputStream.close();
                }

                if (socket != null) {
                    socket.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化文件大小
     */
    private static String getFormatFileSize(long length) {

        double size = ((double) length) / (1 << 30);
        if(size >= 1) {
            return df.format(size) + "GB";
        }
        size = ((double) length) / (1 << 20);
        if(size >= 1) {
            return df.format(size) + "MB";
        }
        size = ((double) length) / (1 << 10);
        if(size >= 1) {
            return df.format(size) + "KB";
        }
        return length + "B";
    }
}
