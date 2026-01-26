package com.dw.dashboard.util;

import com.dw.dashboard.common.ResultCode;
import com.dw.dashboard.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES加密工具类
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Component
public class EncryptUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    @Value("${app.aes-key:DW_DASHBOARD_KEY}")
    private String aesKey;

    /**
     * AES加密
     *
     * @param content 待加密内容
     * @return 加密后的Base64字符串
     */
    public String encrypt(String content) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("AES加密失败", e);
            throw new BusinessException(ResultCode.ENCRYPT_ERROR, "加密失败");
        }
    }

    /**
     * AES解密
     *
     * @param encryptedContent 加密的Base64字符串
     * @return 解密后的内容
     */
    public String decrypt(String encryptedContent) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedContent));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("AES解密失败", e);
            throw new BusinessException(ResultCode.ENCRYPT_ERROR, "解密失败");
        }
    }

}
