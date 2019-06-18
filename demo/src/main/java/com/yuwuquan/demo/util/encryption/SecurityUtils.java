package com.yuwuquan.demo.util.encryption;

import java.util.Map;


public class SecurityUtils {
	
	static String publicKey;
    static String privateKey;

    static void test() throws Exception {
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
        String source = "orderId=80000324"+"2019-06-18 11:39:02";
        System.out.println("原文字：" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: " + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:" + status);
    }
    public static void main(String[] args) throws Exception {
        Map<String, Object> keyMap = RSAUtils.genKeyPair();
        privateKey = RSAUtils.getPrivateKey(keyMap);
        publicKey = RSAUtils.getPublicKey(keyMap);
        System.out.println(privateKey);
        System.out.println(publicKey);
        privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMP6d9tknCGAahExn2dQSk/c11XEEW" +
                "kKNwRs9O5thqlbNRgsK8sR/XJuDolAKYkbi3NMYQDqa843oVUIFn1NeaODgCY+wMoyYAedFNBHbr9hDSu11a5D" +
                "dmgUoLC0P+3KvaaScGVBQ1W4kMEpz5moL+s2SMUedLKlsiQFytYMbCxFAgMBAAECgYEAj0L1gyfVCph48QC5aij" +
                "DagYiU8v0fji9ZzrPAcyG+B54facsjFImWBQkBCknM9cy5aIb9zNmg2UfRq2W4xpTo/B0wjSFzE6yVrL+ltGXXaK" +
                "rdZwT/F9GbG143ZJfc2ePkGJaw18x+UDOGbXsC8/qQhowFjEA34W3XlwSM6mWjbECQQDsZzrsYQUgxeNigWLBv3DDM" +
                "eOBWf+VLLl3M5e89bzmGXzqtRC+fAt/LrG6DV4oJsnoHyITYrMoESEKT8QXBorbAkEA1DlfYJv/hlsUcoxdV0T+1jxy" +
                "uJFafcvEhi91isnULTvgt0EJ3IAnY/xIp8rxoXmyoHagBg1mVH1F/mfxCxR/XwJBAIBu8Qc9CoEsoYWBz7p9HHEfqH88+" +
                "ur7mmNVZ0mkpBTKoiANcORcFg7pumrfGllsAWOXjnbDt5u7+pxUi68KweMCQB/3xHY5CXSjo6Ddo/HRYtHCqUVEH+1zFFd" +
                "iLsKJRHzL2D3CfknIxCY7inY8C8nXGyEwvUosGo3/kNuRrK2OmOECQQCUX1Km1Wow3rumeFUz5ABfhlZMZ6OJmL9e8cZFN2wzJi9CLfCB6Tv+DuSTWCoj7MtYf47sI2fEiDY6cjlJZIAy";
        publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDD+nfbZJwhgGoRMZ9nUEpP3NdVxBFpCjcEbPTubY" +
                "apWzUYLCvLEf1ybg6JQCmJG4tzTGEA6mvON6FVCBZ9TXmjg4AmPsDKMmAHnRTQR26/YQ0rtdWuQ3ZoFKCwtD/tyr2mknBlQUNVuJDBKc+ZqC/rNkjFHnSypbIkBcrWDGwsRQIDAQAB";
        privateKey = "MIIBOgIBAAJBAJHzZy1CuUag0ODXAEuoavKJVYurQESbYKrkXQ+Hkr9TMo3iBupN" +
                "GXjIwj+fzCiq5LgbjBksqIQK8bunSfd6uo0CAwEAAQJADpiC5vnXQYLvoKyfsPFE" +
                "0MB/N5qGsNXBExYHYHg0asGlqxZmmvrNns64tOU4ZBTh1JBrsO6MtOiirdx3jj7p" +
                "gQIhAO+4QbQp09m7hsNal0IG53KUCA/2tnzjyuGf4sGC7b29AiEAm9zjhMXjFBXV" +
                "MXQTCRrC0wgZaX06WCz4cVlIHDsdNRECIB1caRfDA8KoWFI3F02sAM8k2HP0GNfl" +
                "IqVN6v0epzXVAiB+sjK75gPtesXfOkSS/Q7rbkUB7Aq2TG/z66hjUmYukQIhAJnS" +
                "MuCBSxSCfjz0Ytb+qJRxvseNUxRq2uf7fPAAmvjp";
        publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJHzZy1CuUag0ODXAEuoavKJVYurQESb" +
                "YKrkXQ+Hkr9TMo3iBupNGXjIwj+fzCiq5LgbjBksqIQK8bunSfd6uo0CAwEAAQ==";
        //加密
        byte[] b = SecurityUtils.encodedData(privateKey,"ywq");
        //解密
        String s = SecurityUtils.decrypt(publicKey,b);//ywq


        //签名
        String sign = RSAUtils.sign(b, privateKey);
        //验证
        boolean status = RSAUtils.verify(b, publicKey, sign);//true

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
