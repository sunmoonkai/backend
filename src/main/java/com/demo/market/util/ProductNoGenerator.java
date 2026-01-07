package com.demo.market.util;

import org.springframework.stereotype.Component;

@Component
public class ProductNoGenerator {

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    // Base62
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // 生成商品編號
    public synchronized String nextProductNo() {
        long timestamp = System.currentTimeMillis();

        if (timestamp == lastTimestamp) {
            sequence++;
            // 每毫秒最多 1000 個
            long MAX_SEQUENCE = 999L;
            if (sequence > MAX_SEQUENCE) {
                // 同一毫秒序列滿，等下一毫秒
                while (timestamp <= lastTimestamp) {
                    timestamp = System.currentTimeMillis();
                }
                sequence = 0;
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        long id = timestamp * 1000 + sequence; // 毫秒 + 序列
        return encodeBase62(id);
    }

    private String encodeBase62(long value) {
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            int remainder = (int) (value % 62);
            sb.append(BASE62.charAt(remainder));
            value /= 62;
        }
        return sb.reverse().toString();
    }
}
