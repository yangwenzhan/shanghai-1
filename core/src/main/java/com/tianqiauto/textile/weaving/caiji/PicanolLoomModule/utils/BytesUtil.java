package com.tianqiauto.textile.weaving.caiji.PicanolLoomModule.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;

/**
 * 对byte数据的处理 bjw
 *
 * @Date 2019/3/6 9:12
 **/
public class BytesUtil {


    /**
     * 从byte中获取bit标识
     **/
    public static byte getBit(byte b, int index) {
        return (byte) ((b >> index) & 0x1);
    }

    /**
     * 将byte转换成bit数组
     */
    public static byte[] toBitArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 0; i <= 7; i++) {
            array[i] = (byte) (b & 0x1);
            b = (byte) (b >> 1);
        }
        return array;
    }


    /**
     * long转Byte[]
     *
     * @Date 18:12
     **/
    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, x);
        return buffer.array();
    }

    /**
     * Byte[]转long
     *
     * @Date 2019/3/6 18:13
     **/
    public static long bytesToLongWord(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put((byte) 0);
        buffer.put((byte) 0);
        buffer.put((byte) 0);
        buffer.put((byte) 0);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    /**
     * Byte[]转int
     *
     * @Date 2019/3/6 18:13
     **/
    public static int bytesToWord(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put((byte) 0);
        buffer.put((byte) 0);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getInt();
    }

    /**
     * Byte[]转int
     *
     * @Date 2019/3/6 18:13
     **/
    public static short bytesToShort(byte bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put((byte) 0);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getShort();
    }

    /**
     * 转换成BCDlong格式
     *
     * @Author bjw
     * @Date 2019/5/10 11:02
     **/
    public static Long byteToDe(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            byte bitH = (byte) ((aByte & 0xf0) >> 4);
            byte bitL = (byte) (aByte & 0x0f);
            sb.append(bitH).append(bitL);
        }
        return new Long(sb.toString());
    }

    /**
     * Signed Fraction Byte: signed 16-bit-value
     * MSB= signed integer byte value: LSB fractional part of the byte integer value (1/256 of the integer value)(-127,99...+128,996)
     * 有符号分数字节:有符号的16位值
     * MSB=有符号整数字节值:字节整数值的LSB小数部分(整数的1/256)(-127,99…+128,996)
     *
     * @Author bjw
     * @Date 2019/5/10 16:19
     **/
    public static double byteToFraction(byte[] bytes) {
        byte aByte = bytes[0];
        byte[] b = new byte[2];
        b[1] = bytes[1];
        short lsbByte = 0;
        for (int i = 0; i < 2; i++) {
            lsbByte <<= 8;
            lsbByte |= (b[i] & 0xff);
        }
        BigDecimal bg = new BigDecimal(aByte+(lsbByte / 256F)).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }

}