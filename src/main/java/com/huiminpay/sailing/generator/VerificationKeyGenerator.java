package com.huiminpay.sailing.generator;

/**
 * verificationKey生成器
 */
public interface VerificationKeyGenerator {

    String generate(String prefix);
}
