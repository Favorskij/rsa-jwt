package org.example;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Main key = new Main();
        System.out.println(key.getSer());

    }

    private String getSer() {

        try  {

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("clientkeystore");

            char[] password = "qwe123".toCharArray();
            String alias = "client";

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(inputStream, password);

            Key key = keyStore.getKey(alias, password);

            LocalDateTime currentTime = LocalDateTime.now();
            Date dat = Date.from(currentTime.plusMinutes(50).atZone(ZoneId.systemDefault()).toInstant());

            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", alias);
            claims.put("exp", dat.getTime());

            return Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setHeaderParam("alg", "RS256")
                    .setClaims(claims)
                    .signWith(key, SignatureAlgorithm.RS256).compact();

        } catch (IOException | KeyStoreException | CertificateException | NoSuchAlgorithmException |
                 UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        }


    }


}
