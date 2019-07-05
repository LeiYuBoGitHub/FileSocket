package com.lyb.util;

import java.util.Arrays;

/**
 * @Auther: 野性的呼唤
 * @Date: 2019/4/30 17:33
 * @Description:
 */
public class ByteUtil {


    public static void main(String[] args) {

        /*String x = "P";
        String p = getStringFromByte(x.getBytes());
        System.out.println(p);*/

        String x = "A";
        System.out.println(Arrays.toString(x.getBytes()));

    }

    public static String getStringFromByte(byte cmd) {
        return Integer.toHexString(cmd & 0xff);
    }

    public static String getStringFromByte(byte[] cmd) {

        StringBuilder stringBuilder = new StringBuilder();

        if (cmd == null || cmd.length <= 0) {
            return null;
        }

        for (byte b : cmd) {

            System.out.println("传输的比特:" + b);

            String hv = getStringFromByte(b);

            System.out.println("hv:" + hv);

            if (hv.length() < 2) {
                stringBuilder.append(0);
            }

            stringBuilder.append(hv).append(" ");
        }

        return stringBuilder.toString();
    }


}
