package com.hefu.common.utils;

import java.util.Random;

public class AbcUtils {
    /**
     * 生产ItemName随机函数
     * @param length
     * @return
     */
    public static String getItemName( int length ){
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < length; i++ )
        {
            int number = random.nextInt( base.length() );
            sb.append( base.charAt( number ) );
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getItemName(6));
    }
}
