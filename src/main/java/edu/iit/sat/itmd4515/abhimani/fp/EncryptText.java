package edu.iit.sat.itmd4515.abhimani.fp;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Ankit Bhimani (abhimani) on edu.iit.sat.itmd4515.abhimani.fp
 */
public class EncryptText{
    public static String crypt(String text){
	return DigestUtils.sha256Hex(text);
    }

    public static boolean hashEquals(String hashKey, String text){
	return hashKey.equals(crypt(text));
    }
}
