package com.example.admin.center.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.pdf.codec.Base64;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shihao
 * @Title: WxLoginConfig
 * @ProjectName Second-order-center
 * @Description: 微信小程序登录
 * @date Created in
 * @Version: $
 */
@Component
public class WxLoginConfig {

    public static String APPID ="wx71884e4db086daf9";
    public static String SECRET ="6b03404877f005f79c6b3c73c84c36ee";
    public static String GRANTTYPE = "authorization_code";
    public static CloseableHttpClient client = HttpClients.createDefault();
    public static Integer bossId;

    public static Integer getBossId() {
        return bossId;
    }

    public static void setBossId(Integer bossId) {
        WxLoginConfig.bossId = bossId;
    }


    public static enum AuthType{
        //小程序微信登录
        WECHART("1"),
        //账号密码登录
        AUTH("3");

        private String authType;
        AuthType(String authType) {
            this.authType = authType;
        }

        public String getAuthType() {
            return this.authType;
        }
    }

    public static JSONObject parseWechart(String encryptedData, String sessionKey, String iv)
            throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] dataByte = Base64.decode(encryptedData);
        byte[] keyByte = Base64.decode(sessionKey);
        byte[] ivByte = Base64.decode(iv);
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));

        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
        byte[] resultByte = cipher.doFinal(dataByte);
        String result = new String(resultByte, "UTF-8");
        return JSON.parseObject(result);
    }

    public static JSONObject getSessionKeyOrOpenId(String code, String appName) throws ParseException, IOException {

        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("appid", APPID));
        list.add(new BasicNameValuePair("secret", SECRET));
        list.add(new BasicNameValuePair("grant_type", GRANTTYPE));
        list.add(new BasicNameValuePair("js_code", code));

        String params = EntityUtils.toString(new UrlEncodedFormEntity(list, Consts.UTF_8));
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session" + "?" + params);

        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, Consts.UTF_8);
        JSONObject jsonObject = JSONObject.parseObject(result);
        response.close();
        return jsonObject;
    }
}
