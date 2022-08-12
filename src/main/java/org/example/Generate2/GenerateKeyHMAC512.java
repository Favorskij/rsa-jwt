package org.example.Generate2;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenerateKeyHMAC512 {

    public static void main(String[] args)  {

        Map<String, Object> claim = new HashMap<>();
        claim.put("aa","dd");

        String[] claims = {"Volvo", "BMW", "Ford", "Mazda"};

        String s = JWT.create()
                .withIssuer("Get Arrays, LLS")
                .withAudience("User Management Portal")
                .withArrayClaim("Authorities", claims)
                .withClaim("Ivanov", "Roman")
                .withClaim("AUTHORITIES", claim)
                .withIssuedAt(new Date(System.currentTimeMillis() + 432_000_000))
                .sign(Algorithm.HMAC512("secretKey".getBytes()));

        System.out.println("secretKey");
        System.out.println(s);


    }

}
