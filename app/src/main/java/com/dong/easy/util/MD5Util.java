package com.jm.android.jumei.tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * MD5验证工具 
 *  
 */  
public class MD5Util {  
  
    /** 
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合 
     */  
    protected static char  hexDigits[]   = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',  
            'd', 'e', 'f'                       };  
    protected static MessageDigest messagedigest = null;  
    static {  
        try {  
            messagedigest = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static String getFileMD5String(File file) throws IOException {  
        InputStream fis;  
        fis = new FileInputStream(file);  
        byte[] buffer = new byte[1024];  
        int numRead = 0;  
        while ((numRead = fis.read(buffer)) > 0) {  
            messagedigest.update(buffer, 0, numRead);  
        }  
        fis.close();  
        return bufferToHex(messagedigest.digest());  
    }  
  
    private static String bufferToHex(byte bytes[]) {  
        return bufferToHex(bytes, 0, bytes.length);  
    }  
  
    private static String bufferToHex(byte bytes[], int m, int n) {  
        StringBuffer stringbuffer = new StringBuffer(2 * n);  
        int k = m + n;  
        for (int l = m; l < k; l++) {  
            appendHexPair(bytes[l], stringbuffer);  
        }  
        return stringbuffer.toString();  
    }  
  
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {  
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换  
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同  
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换  
        stringbuffer.append(c0);  
        stringbuffer.append(c1);  
    }  
//    public static void main(String[] args) throws IOException {  
//        File file = new File("C:\\Users\\yongzhin\\Downloads\\1411642195.sqlite.zip");  
//        String md5 = getFileMD5String(file);  
//        System.out.println("md5:" + md5);  
//    }  
  
    public static final String md5(final String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = "0" + h;
				hexString.append(h);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
  
}  