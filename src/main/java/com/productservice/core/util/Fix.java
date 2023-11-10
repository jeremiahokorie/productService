package com.productservice.core.util;

import jakarta.persistence.TypedQuery;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

/**
 * @author Stephen Obi <stephen.obi@etranzact.com>
 * @philosophy Quality must be enforced, otherwise it won't happen. We programmers must be required to write tests, otherwise we won't do it!
 * <p>
 * ------
 * Tip: Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.
 * ------
 * @since 03/11/2021 14:19
 */
@SuppressWarnings("unused")
public class Fix {
    public static Object privacyControl(Object request) {
        return request;
    }

    public static Object integrityCheck(Object request) {
        return request;
    }

//    public static Object checkMarxByPass(CloseableHttpClient httpClient, HttpUriRequest request, ResponseHandler<Object> responseHandler) throws IOException {
//        return httpClient.execute(request, responseHandler);
//    }

    public static <T> List<T> fetchList(TypedQuery<T> query) {
        return query.getResultList();
    }


    public static <T> T fetchSingle(TypedQuery<T> query) {
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public static PublicKey generatePublicKey(String seed) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(seed);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public static PrivateKey generatePrivateKey(String seed) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(seed);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }


    public static Class<?> getClassInfo(String forName) throws ClassNotFoundException {
        return Class.forName(forName);
    }
}