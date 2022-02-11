package com.hefu.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author lxq
 * @date 2021年04月10日 21:52
 */
public class Client {

    private static final int HEAD_LEN = 4;
    private static final int BODY_LEN = 21;
    private static String serverIp;
    private static int serverPort;


    public static void main(String[] args) {
        serverIp = "192.168.1.150";
        serverPort = 9002;

            SocketChannel channel = null;
            ByteBuffer byteBuffer = ByteBuffer.allocate(HEAD_LEN + BODY_LEN);

            byte[] b = getB();
            byteBuffer.putInt(b.length).put(b);;
            try {
                channel = SocketChannel.open();
                channel.configureBlocking(true);
                if (channel.connect(new InetSocketAddress(serverIp, serverPort))) {
                    byteBuffer.flip();//必须flip, 从写模式变为可读模式
                    channel.write(byteBuffer);
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
    }

    public static byte[] getB(){
        byte[] msg = new byte[21];
        // 版本号
        byte[] bytes = "v1".getBytes();
        // 消息类型
        byte msg_type = 2;
        // 违规类型
        byte[] accidentTypeId = doubleToBytes_Big(12);
        // 违规人数
        byte[] accidentNum = intToByte4(4);
        // 摄像头编号
        byte[] cameraId = "zFvaqQ".getBytes();

        System.arraycopy(bytes, 0, msg, 0, bytes.length);
        msg[2] = msg_type;
        System.arraycopy(accidentTypeId, 0, msg, 3, accidentTypeId.length);
        System.arraycopy(accidentNum, 0, msg, 11, accidentNum.length);
        System.arraycopy(cameraId, 0, msg, 15, cameraId.length);
        return msg;
    }

    /**
     * long转byte数组，大端模式
     * @param l
     * @return
     */
    public static byte[] doubleToBytes_Big(long l){
        byte b[] = new byte[8];
        b[0] = (byte)  (0xff & (l >> 56));
        b[1] = (byte)  (0xff & (l >> 48));
        b[2] = (byte)  (0xff & (l >> 40));
        b[3] = (byte)  (0xff & (l >> 32));
        b[4] = (byte)  (0xff & (l >> 24));
        b[5] = (byte)  (0xff & (l >> 16));
        b[6] = (byte)  (0xff & (l >> 8));
        b[7] = (byte)  (0xff & l);
        return b;
    }

    /**
     * int整数转换为4字节的byte数组
     *
     * @param i  整数
     * @return byte数组
     */
    public static byte[] intToByte4(int i) {
        byte[] b = new byte[4];
        b[0] = (byte) (i >> 24 & 0xff); //数据组起始位,存放内存起始位, 即:高字节在前
        b[1] = (byte) (i >> 16 & 0xff); //高字节在前是与java存放内存一样的, 与书写顺序一样
        b[2] = (byte) (i >> 8 & 0xff);
        b[3] = (byte) (i & 0xff);
        return b;
    }

}

