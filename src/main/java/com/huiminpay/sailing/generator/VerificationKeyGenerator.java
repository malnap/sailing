package com.huiminpay.sailing.generator;

/**
 * 验证key生成器
 */
public interface VerificationKeyGenerator {

    String generate(String prefix);
}
