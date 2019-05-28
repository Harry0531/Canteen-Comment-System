package com.uml_review.uml.Utils;

import java.util.Random;
import java.util.UUID;

public class IdUtils {
        /**
         * 生成随机图片名
         */

        public static String genImageName() {        //取当前时间的长整形值包含毫秒
            String uuid =  UUID.randomUUID().toString().replaceAll("-", "");
            long millis = System.currentTimeMillis();        //long millis = System.nanoTime();
            //加上三位随机数
            Random random = new Random();
            int end3 = random.nextInt(999);        //如果不足三位前面补0
            String str = millis + String.format("%03d", end3) + uuid;
            return str;
        }
    }



