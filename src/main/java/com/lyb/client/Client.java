package com.lyb.client;

import com.lyb.constant.Constant;

import java.io.*;
import java.net.Socket;

/**
 * @Auther: 野性的呼唤
 * @Date: 2018/9/11 13:50
 * @Description:
 */
public class Client {

    private static final String FILE_SAVE_PATH  = "static/file/client";

    private static final String FILE_NAME  = "1.jpg";

    public static void main(String[] args){
        start();
    }

    private static void start() {

        DataOutputStream dataOutputStream = null;
        FileInputStream fileInputStream = null;

        try {

            Socket socket = new Socket(Constant.HOST, Constant.PORT);

            File file = new File(FILE_SAVE_PATH + File.separatorChar + FILE_NAME);

            if (file.exists()) {

                fileInputStream = new FileInputStream(file);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                // 文件名和长度
                dataOutputStream.writeUTF(file.getName());
                dataOutputStream.flush();
                dataOutputStream.writeLong(file.length());
                dataOutputStream.flush();

                // 开始传输文件
                System.out.println("======== 开始传输文件 ========");
                System.out.println(file.length());
                byte[] bytes = new byte[102400];
                int length;
                long progress = 0;
                while((length = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                    dataOutputStream.write(bytes, 0, length);
                    dataOutputStream.flush();
                    progress += length;

                    System.out.print("| " + ((100*progress)/file.length()) + "% |");
                }
                System.out.println();
                System.out.println("======== 文件传输成功 ========");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {

                if (fileInputStream != null) {
                    fileInputStream.close();
                }

                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
