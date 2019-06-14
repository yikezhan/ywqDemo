package com.yuwuquan.demo.util.encryption;

import java.util.Map;


public class SecurityUtils {
	
	static String publicKey;
    static String privateKey;

   /* static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "orderId=80000324";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "orderId=80000324"+DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }*/
    
    public static void main(String[] args) throws Exception {
    	 Map<String, Object> keyMap = RSAUtils.genKeyPair("ywq121312","wb123213");
    	  privateKey = RSAUtils.getPrivateKey(keyMap);
    	  publicKey = RSAUtils.getPublicKey(keyMap);
    	  System.out.println(privateKey);
    	  System.out.println(publicKey);
	}
    /**
     * rsa加密
     * @param rkey
     * @param param
     * @return
     * @throws Exception
     */
    public static byte[] encodedData(String rkey,String param) throws Exception {
    	Map<String, Object> keyMap = RSAUtils.genKeyPair(null,rkey);
        privateKey = RSAUtils.getPrivateKey(keyMap);
        byte[] encodedData = RSAUtils.encryptByPrivateKey(param.getBytes(),privateKey);
        return encodedData ;
    }
    
    /**
     * rsa解密
     * @param pkey
     * @param encodedData
     * @return
     * @throws Exception
     */
    public static String decrypt(String pkey,byte[]encodedData) throws Exception{
    	Map<String, Object> keyMap = RSAUtils.genKeyPair(pkey,null);
    	 publicKey = RSAUtils.getPublicKey(keyMap);
         byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
         return new String(decodedData);
    }
}
