package com.huiminpay.sailing.handler;

import com.huiminpay.sailing.generator.NumberVerificationCodeGenerator;
import com.huiminpay.sailing.generator.UUIDVerificationKeyGenerator;
import com.huiminpay.sailing.generator.VerificationCodeGenerator;
import com.huiminpay.sailing.generator.VerificationKeyGenerator;
import com.huiminpay.sailing.sms.SmsService;
import com.huiminpay.sailing.store.VerificationStore;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 短信(数字随机验证码)处理器
 */
@Slf4j
public class SmsNumberVerificationHandler extends AbstractVerificationHandler {

	/**
	 * 标识，sms
	 */
	private String name;

	/**
	 * 验证码长度
	 */
	private int len;

	private VerificationStore verificationStore;

	/**
	 * 验证码key生成器
	 */
	private VerificationKeyGenerator verificationKeyGenerator;

	/**
	 * 验证码生成器
	 */
	private VerificationCodeGenerator verificationCodeGenerator;

	private SmsService smsService;

	public SmsNumberVerificationHandler(String name, int len) {
		this.name = name;
		this.len = len;
		verificationKeyGenerator = new UUIDVerificationKeyGenerator();
		verificationCodeGenerator = new NumberVerificationCodeGenerator(len);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public VerificationStore getVerificationStore() {
		return verificationStore;
	}

	public void setVerificationStore(VerificationStore verificationStore) {
		this.verificationStore = verificationStore;
	}

	@Override
	public VerificationKeyGenerator getVerificationKeyGenerator() {
		return verificationKeyGenerator;
	}

	@Override
	public VerificationCodeGenerator getVerificationCodeGenerator() {
		return verificationCodeGenerator;
	}

	@Override
	public int getEffectiveTime() {
		return 300;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	@Override
	public String confusion(Map<String, Object> payload, String key, String code) {
		String mobile = String.valueOf(payload.get("mobile"));

		// 使用腾讯云发送短信
//		smsService.send(mobile, code, getEffectiveTime());

		// 测试使用，在控制台输出验证码
		smsService.sendOnConsole(mobile, code, getEffectiveTime());
		return null;
	}
}
