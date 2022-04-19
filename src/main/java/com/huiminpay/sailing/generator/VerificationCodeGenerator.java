package com.huiminpay.sailing.generator;

/**
 * 验证码生成器
 */
public interface VerificationCodeGenerator {

    /**
     * 验证码生成
     *
     * @return
     */
    String generate();

}
