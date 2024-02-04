package mingle.chang.service.utils;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static final String SECRET_KET = "mingle.chang";

    public static String createJwt(Map<String, Object> map) {
        return createJwt(map, new Date());
    }
    public static String createJwt(Map<String, Object> map,
                                   Date issued) {
        return createJwt(map, issued, null);
    }
    public static String createJwt(Map<String, Object> map,
                                   long expiration) {
        Date issued = new Date();
        long expirationTimestamp = issued.getTime() + expiration;
        Date expirationDate = new Date(expirationTimestamp);
        return createJwt(map, issued, expirationDate);
    }
    public static String createJwt(Map<String, Object> map,
                                   long expiration,
                                   String secretKey) {
        Date issued = new Date();
        long expirationTimestamp = issued.getTime() + expiration;
        Date expirationDate = new Date(expirationTimestamp);
        return createJwt(map, issued, expirationDate, secretKey);
    }
    public static String createJwt(Map<String, Object> map,
                                   Date issued,
                                   long expiration) {
        long expirationTimestamp = issued.getTime() + expiration;
        Date expirationDate = new Date(expirationTimestamp);
        return createJwt(map, new Date(), expirationDate);
    }
    public static String createJwt(Map<String, Object> map,
                                   Date issued,
                                   Date expiration) {
        return createJwt(map, issued, expiration, SECRET_KET);
    }
    public static String createJwt(Map<String, Object> map,
                                   Date issued,
                                   Date expiration,
                                   String secretKey) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return createJwt(map, issued, expiration, algorithm);
    }
    public static String createJwt(Map<String, Object> map,
                                   Date issued,
                                   Date expiration,
                                   Algorithm algorithm) {
        JWTCreator.Builder builder = JWT.create();
        builder.withPayload(map);
        builder.withIssuedAt(issued);
        builder.withExpiresAt(expiration);
        String jwt = builder.sign(algorithm);
        return jwt;
    }

    public static DecodedJWT parseJwt(String jwt) {
        DecodedJWT decodedJWT = JWT.decode(jwt);
        return decodedJWT;
    }
    public static DecodedJWT verifierJwt(DecodedJWT jwt, String secretKey) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return verifierJwt(jwt, algorithm);
    }
    public static DecodedJWT verifierJwt(DecodedJWT jwt, Algorithm algorithm) {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
        return decodedJWT;
    }
    public static DecodedJWT verifierJwt(String jwt, String secretKey) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return verifierJwt(jwt, algorithm);
    }
    public static DecodedJWT verifierJwt(String jwt, Algorithm algorithm) {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
        return decodedJWT;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "mingle");
        String jwt = createJwt(map);
        System.out.println(jwt);
        DecodedJWT decodedJWT = JWT.decode(jwt);
        decodedJWT.getClaim("username").asString();
        System.out.println(decodedJWT);
    }
}
