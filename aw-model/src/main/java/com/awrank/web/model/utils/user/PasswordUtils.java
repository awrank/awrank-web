package com.awrank.web.model.utils.user;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * todo: description
 *
 * @author Alex Polyakov
 */
public class PasswordUtils {

	/**
	 * todo: description
	 * @param password
	 * @return
	 */
	public static String hashPassword(final String password) {
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] enc = md.digest(("aNi%Hh?46YKV7a19?0X2" + password).getBytes("UTF-8"));
			enc = md.digest((new BigInteger(enc).toString(Character.MAX_RADIX) + password + "}9Sj$SUeGq2#Dlwe0qaw}OD?l~6PKB").getBytes("UTF-8"));
			return new BigInteger(enc).toString(35);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * todo: description
	 * @param password
	 * @return
	 */
	public static String sha2(final String password) {
		String result = null;
		try {
			MessageDigest sha2 = MessageDigest.getInstance("SHA-512");
			byte messageDigest[] = sha2.digest(password.getBytes("UTF-8"));
			result = base16(messageDigest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * todo: description
	 * @param password
	 * @return
	 */
	public static String md5(final String password) {
		String result = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			byte messageDigest[] = md5.digest(password.getBytes("UTF-8"));
			result = base16(messageDigest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * todo: description
	 * @param messageDigest
	 * @return
	 */
	public static String base16(byte messageDigest[]) {
		final StringBuilder hexString = new StringBuilder();
		for (byte aMessageDigest : messageDigest) {
			String currentByte = Integer.toHexString(0xFF & aMessageDigest);
			if (currentByte.length() == 1) {
				hexString.append('0');
			}
			hexString.append(currentByte);
		}
		return hexString.toString();
	}

	/**
	 * todo: description
	 * @param length
	 * @return
	 */
	public static String generatePassword(int length) {
		String password = BigInteger.valueOf(UUID.randomUUID().getMostSignificantBits()).toString(36).toUpperCase();
		if (password.length() > length) {
			password = password.substring(password.length() - length);
		}
		return password;
	}
}
