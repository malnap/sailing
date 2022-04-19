package com.huiminpay.sailing.service;

import com.huiminpay.sailing.dto.VerificationInfo;
import com.huiminpay.sailing.handler.AbstractVerificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VerificationService {

    //sms=com.huiminpay.sailing.handler.SmsNumberVerificationHandler@22d59af3
    @Autowired
	@Qualifier("verificationHandlerMap")
    private Map<String, AbstractVerificationHandler> verificationHandlerMap;

    /**
     * 生成验证信息
     *
     * @param name          业务名称
     * @param payload       业务携带参数，如手机号 ，邮箱
     * @param effectiveTime 验证信息有效期(秒)
     * @return
     */
    public VerificationInfo generateVerificationInfo(String name, Map<String, Object> payload, int effectiveTime) {
        AbstractVerificationHandler verificationHandler = getVerificationHandler(name);
        return verificationHandler.generateVerificationInfo(payload, effectiveTime);
    }

    /**
     * 验证信息
     *
     * @param name             业务名称
     * @param verificationKey  验证key
     * @param verificationCode 验证码
     * @return
     */
    public boolean verify(String name, String verificationKey, String verificationCode) {
        AbstractVerificationHandler verificationHandler = getVerificationHandler(name);
        return verificationHandler.verify(verificationKey, verificationCode);
    }

    private AbstractVerificationHandler getVerificationHandler(String name) {
        AbstractVerificationHandler verificationHandler = verificationHandlerMap.get(name);
        if (verificationHandler == null) {
            throw new RuntimeException(String.format("No found handler process %s type.", name));
        }
        return verificationHandler;
    }
}