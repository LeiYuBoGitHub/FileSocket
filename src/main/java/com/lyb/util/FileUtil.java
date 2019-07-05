package com.lyb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @Auther: 野性的呼唤
 * @Date: 2019/4/30 18:45
 * @Description:
 */
public class FileUtil {

    /**
     * 读取文本
     * @param file
     * File对象
     * @param encoding
     * 字符编码
     * @return
     */
    public static String readTXT(File file, String encoding){

        //判断是否是文件或者文件是否存在
        if(!file.isFile() && !file.exists()) {
            System.out.println("无法读取,可能不是文件或者文件不存在");
            return null;
        }

        StringBuilder txt = new StringBuilder();

        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while((lineTxt = bufferedReader.readLine()) != null){
                txt.append(lineTxt);
            }
            read.close();
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return txt.toString();
    }
}
