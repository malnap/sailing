package com.huiminpay.sailing.handler;

import com.huiminpay.sailing.dto.VerificationInfo;
import com.huiminpay.sailing.generator.VerificationCodeGenerator;
import com.huiminpay.sailing.generator.VerificationKeyGenerator;
import com.huiminpay.sailing.store.VerificationStore;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 验证处理接口
 */
public abstract class AbstractVerificationHandler {

    public abstract String getName();

    public abstract VerificationStore getVerificationStore();

    public abstract VerificationKeyGenerator getVerificationKeyGenerator();

    public abstract VerificationCodeGenerator getVerificationCodeGenerator();

    public abstract int getEffectiveTime();

    /**
     * 执行混淆动作：
     * 举例：
     * 1.生成混淆图片
     * 2.发送短信验证码
     * 3.发送邮件验证码
     * 4.生成邮件链接附加参数，并发送邮件
     *
     * @return 混淆后内容
     * 举例：
     * 1.图片验证码为:图片base64编码
     * 2.短信验证码为:null
     * 3.邮件验证码为: null
     * 4.邮件链接点击验证为：url附加验证参数信息
     */
    abstract String confusion(Map<String,Object> payload , String key, String code);

    /**
     * 生成验证信息
     *
     * @param payload 业务携带参数，如手机号，邮箱
     * @param effectiveTime 验证信息有效时间(秒)
     * @return
     */
    public VerificationInfo generateVerificationInfo(Map<String,Object> payload, int effectiveTime){
        effectiveTime = effectiveTime > 0 ? effectiveTime : getEffectiveTime();
        //key=sms:a9bbf3d184844dd493347c63eb9a2095
        String key = getVerificationKeyGenerator().generate(getName() + ":");
        //code=818036
        String code = getVerificationCodeGenerator().generate();
        String content = confusion(payload, key,code);
        getVerificationStore().set(key, code, effectiveTime);
        return new VerificationInfo(key, content);
    }

    /**
     * 验证信息，从缓存查出key对应的验证码，比较
     *
     * @param verificationKey  验证key
     * @param verificationCode 验证码
     * @return
     */
    public boolean verify(String verificationKey, String verificationCode) {
        if (StringUtils.isBlank(verificationKey) || StringUtils.isBlank(verificationCode)) {
            return false;
        }
        String code = getVerificationStore().get(verificationKey);
        if (code == null) {
            return false;
        }
        return code.equals(verificationCode);
    }
}
