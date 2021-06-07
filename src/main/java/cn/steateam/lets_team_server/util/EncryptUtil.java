package cn.steateam.lets_team_server.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * 加密解密工具类
 *
 * @author STEA_YY
 **/
public class EncryptUtil {
    /**
     * SHA256散列
     *
     * @param sourceStr 目标字符串
     * @return 散列结果
     */
    public static String getSha256(String sourceStr) {
        return DigestUtils.sha256Hex(sourceStr);
    }

    /**
     * 二进制数据转base64
     *
     * @param binaryData 二进制数据
     * @return base64字符串
     */
    public static String toBase64String(byte[] binaryData) {
        return new String(Base64.encodeBase64(binaryData));
    }
}