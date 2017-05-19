package com.duck.owlapi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.util.encoders.Hex;

import com.duck.owlapi.vo.User;

/**
 * @author 안덕환
 * http://stackoverflow.com/questions/34551390/how-jwt-actually-works-with-spring-mvc-to-create-token-and-validate-token
 * 위 사이트를 참조하여 코드를 작성하였다 
 */

public class TokenUtil {
	public static final String MAGIC_KEY = "obfuscate";

    public static String createToken(User user){
        /* Expires in one hour */
        long expires = System.currentTimeMillis() + 1000L * 60 * 60;

        StringBuilder tokenBuilder = new StringBuilder();
        tokenBuilder.append(user.getEmail());
        tokenBuilder.append(":");
        tokenBuilder.append(expires);
        tokenBuilder.append(":");
        tokenBuilder.append(TokenUtil.computeSignature(user, expires));

        return tokenBuilder.toString();
    }
    
    public static String createToken(String email, String pw){
        /* Expires in one hour */
        long expires = System.currentTimeMillis() + 1000L * 60 * 60;

        StringBuilder tokenBuilder = new StringBuilder();
        tokenBuilder.append(email);
        tokenBuilder.append(":");
        tokenBuilder.append(expires);
        tokenBuilder.append(":");
        tokenBuilder.append(TokenUtil.computeSignature(email, pw, expires));

        return tokenBuilder.toString();
    }

    public static String computeSignature(User user, long expires){
        System.out.println("------ Compute Signature ------");
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(user.getEmail());
        signatureBuilder.append(":");
        signatureBuilder.append(expires);
        signatureBuilder.append(":");
        signatureBuilder.append(user.getPasswordHash());
        signatureBuilder.append(":");
        signatureBuilder.append(TokenUtil.MAGIC_KEY);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }
    
    public static String computeSignature(String email, String pw, long expires){
        System.out.println("------ Compute Signature ------");
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(email);
        signatureBuilder.append(":");
        signatureBuilder.append(expires);
        signatureBuilder.append(":");
        signatureBuilder.append(pw);
        signatureBuilder.append(":");
        signatureBuilder.append(TokenUtil.MAGIC_KEY);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }


    public static String getEmailFromToken(String authToken){
        if (null == authToken) {
            return null;
        }

        String[] parts = authToken.split(":");
        return parts[0];
    }

    public static boolean validateToken(String authToken, User user)  {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];

        if (expires < System.currentTimeMillis()) {
            return false;
        }
        return signature.equals(TokenUtil.computeSignature(user, expires));
    }
}
