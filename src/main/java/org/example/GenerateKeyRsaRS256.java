package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.*;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenerateKeyRsaRS256 {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);

        KeyPair kp = Keys.keyPairFor(SignatureAlgorithm.RS256);

        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        System.out.println("Public Key:");
        System.out.println(convertToPublicKey(encodedPublicKey));

        System.out.println("Private Key:");
        System.out.println(convertToPrivateKey(encodedPrivateKey));

        System.out.println("TOKEN:");
        String token = generateJwtToken(privateKey);

        System.out.println(token);
        printStructure(token, publicKey);

    }

    public static String generateJwtToken(PrivateKey privateKey) {

        Map<String, String> claims = new HashMap<>();
        claims.put("Ivanov","Roman");
        claims.put("email","test@test.com");

        return Jwts.builder()
                .setSubject("adam")
                .setHeaderParam("typ","JWT")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 432_000_000))
                .setIssuer("info@wstutorial.com")
                .claim("groups", new String[] { "user", "admin" })
                .signWith(privateKey, SignatureAlgorithm.RS256).compact();
    }

    public static void printStructure(String token, PublicKey publicKey) {

        Jws<Claims> parseClaimsJws = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);

        System.out.println("Header     : " + parseClaimsJws.getHeader());
        System.out.println("Body       : " + parseClaimsJws.getBody());
        System.out.println("Signature  : " + parseClaimsJws.getSignature());
    }


    private static String convertToPublicKey(String key){
        return "-----BEGIN PUBLIC KEY-----\n" +
                key +
                "\n-----END PUBLIC KEY-----";
    }

    private static String convertToPrivateKey(String pKey){
        return "-----BEGIN PRIVATE KEY-----\n" +
                pKey +
                "\n-----END PRIVATE KEY-----";
    }

}
