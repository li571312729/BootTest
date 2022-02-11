package com.hefu.common.utils;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkUtil {


    public static boolean isAlive(String ip) throws IOException {
        return isAlive(ip,3000);
    }

    public static boolean isAlive(String ip,int timeout) throws IOException {

        InetAddress address=InetAddress.getByName(ip);

        return address.isReachable(timeout);

    }

}
