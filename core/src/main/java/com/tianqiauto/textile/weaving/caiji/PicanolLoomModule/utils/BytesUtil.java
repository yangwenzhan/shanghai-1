package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils;

import java.nio.ByteBuffer;

/**
 * 对byte数据的处理 bjw
 * @Date 2019/3/6 9:12
 **/
public class BytesUtil {


    /**
     * 从byte中获取bit标识
     **/
    public static byte getBit(byte b, int index){
        return (byte) ((b >> index) & 0x1);
    }

    /**
     * 将byte转换成bit数组
     */
    public static byte[] toBitArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 0; i <=7; i++) {
            array[i] = (byte)(b & 0x1);
            b = (byte) (b >> 1);
        }
        return array;
    }



    /**
     * long转Byte[]
     * @Date  18:12
     **/
    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, x);
        return buffer.array();
    }

    /**
     * Byte[]转long
     * @Date 2019/3/6 18:13
     **/
    public static long bytesToLongWord(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put((byte)0);
        buffer.put((byte)0);
        buffer.put((byte)0);
        buffer.put((byte)0);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    /**
     * Byte[]转int
     * @Date 2019/3/6 18:13
     **/
    public static int bytesToWord(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put((byte)0);
        buffer.put((byte)0);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getInt();
    }

    /**
     * Byte[]转int
     * @Date 2019/3/6 18:13
     **/
    public static short bytesToShort(byte bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put((byte)0);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getShort();
    }

    public static void main(String[] args) {
        System.out.println((byte)129);
    }

}
