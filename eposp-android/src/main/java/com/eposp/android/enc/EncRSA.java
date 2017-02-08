package com.eposp.android.enc;


import com.eposp.android.log.LogUtils;

public class EncRSA {
	public static final String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD1mecWBLMB1snW3J089PGK/yIC\nyWRzXnheUuIHD756S9g9XT0QqeR2l8k8L946VnTWLm3QmtpkS32c2ejfarvVnzku\nJrYZyGZivN2hswz+PRxwresR8n/8NQOJ9hu9XVURL24owRKICQg5pD3lqRVL0MFx\nW+BJB/BZn+uSUFQMIwIDAQAB";
	public static String EncPass(String source) throws Exception

	{

		LogUtils.d("公钥加密——私钥解密");
		LogUtils.d("\r加密前文字：\r\n" + source);
		// publicKey = Session.getSession().getEnv().get("loginPubkeys");
//		LogUtils.d("\r加密公钥：\r\n" + CustomApplcation.publicKey);
		LogUtils.d("\r加密公钥：\r\n" + publicKey);
		byte[] data = source.getBytes();
//		byte[] encodedData = RSAUtils.encryptByPublicKey(data, CustomApplcation.publicKey);
		byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);

		LogUtils.d("加密后文字：\r\n" + RSAUtils.hexString(encodedData));
		// byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData,
		// privateKey);
		// String target = new String(decodedData);
		// LogUtils.d("解密后文字: \r\n" + target);
		return RSAUtils.hexString(encodedData);
	}
	public static String EncPass(String source, String publicKey) throws Exception

	{

		LogUtils.d("公钥加密——私钥解密");
		LogUtils.d("\r加密前文字：\r\n" + source);
		// publicKey = Session.getSession().getEnv().get("loginPubkeys");
		LogUtils.d("\r加密公钥：\r\n" + publicKey);
		byte[] data = source.getBytes();
		byte[] encodedData = RSAUtils.encryptByPublicKey(data,publicKey);

		LogUtils.d("加密后文字：\r\n" + RSAUtils.hexString(encodedData));
		// byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData,
		// privateKey);
		// String target = new String(decodedData);
		// LogUtils.d("解密后文字: \r\n" + target);
		return RSAUtils.hexString(encodedData);
	}

	public static String EncPassByte(byte[] source, String publicKey) throws Exception

	{
		LogUtils.d("公钥加密——私钥解密");
		LogUtils.d("\r加密前文字：\r\n" + source);
		// publicKey = Session.getSession().getEnv().get("loginPubkeys");
		LogUtils.d("\r加密公钥：\r\n" + publicKey);
		byte[] encodedData = RSAUtils.encryptByPublicKey(source, publicKey);

		LogUtils.d("加密后文字：\r\n" + RSAUtils.hexString(encodedData));
		// byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData,
		// privateKey);
		// String target = new String(decodedData);
		// LogUtils.d("解密后文字: \r\n" + target);
		return RSAUtils.hexString(encodedData);
	}


//	/**
//	 * 
//	 * @param source
//	 *            加密字符
//	 * @param pubkey
//	 *            加密公钥
//	 * @return
//	 * @throws Exception
//	 */
//	public static String EncPass(String source, String pubkey) throws Exception
//
//	{
//		LogUtils.d("公钥加密——私钥解密");
//		LogUtils.d("\r加密前文字：\r\n" + source);
//		LogUtils.d("\r加密公钥：\r\n" + pubkey);
//		byte[] data = source.getBytes();
//		byte[] encodedData = RSAUtils.encryptByPublicKey(data, pubkey);
//
//		LogUtils.d("加密后文字：\r\n" + RSAUtils.hexString(encodedData));
//		// byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData,
//		// privateKey);
//		// String target = new String(decodedData);
//		// LogUtils.d("解密后文字: \r\n" + target);
//		return RSAUtils.hexString(encodedData);
//	}

}
