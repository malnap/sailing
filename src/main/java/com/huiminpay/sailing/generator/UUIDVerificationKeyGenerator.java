package com.huiminpay.sailing.generator;

import java.util.UUID;

/**
 * verificationKey的value生成器
 */
public class UUIDVerificationKeyGenerator implements VerificationKeyGenerator {

    @Override
    public String generate(String prefix) {
        String uuid = UUID.randomUUID().toString();
        return prefix + uuid.replaceAll("-", "");
    }
}
