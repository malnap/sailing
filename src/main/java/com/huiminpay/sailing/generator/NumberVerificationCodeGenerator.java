package com.huiminpay.sailing.generator;

import java.util.Random;

/**
 * 数字短信验证码生成器
 */
public class NumberVerificationCodeGenerator implements VerificationCodeGenerator {

    private int len;

    public NumberVerificationCodeGenerator(int len){
        this.len = len;
    }

    @Override
    public String generate() {
        return getNumRandom(len);
    }

    /**
     * 随机生成6位数的验证码，左开右闭
     *
     * @param length
     * @return
     */
    private String getNumRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val.append(random.nextInt(10));
        }
        return val.toString();
    }

}
