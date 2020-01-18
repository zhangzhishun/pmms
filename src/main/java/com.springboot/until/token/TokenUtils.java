package com.springboot.until.token;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author eternalSy
 * @version 1.0.0
 */
public class TokenUtils {

    /**
     * 签名秘钥
     */
    public static final String SECRET = "admin";

    /**
     * 生成token
     * @param id 一般传入userName
     * @return
     */
    public static String createJwtToken(String id) {
        String issuer = "www.xxxx.com";
        String subject = "xxxx@126.com";
        long ttlMillis = 3600000;
        return createJwtToken(id, issuer, subject, ttlMillis);
    }

    /**
     * 生成Token
     *
     * @param id        编号
     * @param issuer    该JWT的签发者，是否使用是可选的
     * @param subject   该JWT所面向的用户，是否使用是可选的；
     * @param ttlMillis 签发时间 （有效时间，过期会报错）
     * @return token String
     */
    public static String createJwtToken(String id, String issuer, String subject, long ttlMillis) {

        // 签名算法 ，将对token进行签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 通过秘钥签名JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 设置 JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        // 如果已经指定，添加过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();

    }

    // 验证和读取jwt的示例方法
    public static Claims parseJWT(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static boolean verifyToken(String id,String token){
        try{
            Claims t = parseJWT(token);
            return t.getId().equals(id)?true:false;
        }catch (MalformedJwtException e){
            System.out.println("token长度错误");
            return false;
        }catch (ExpiredJwtException e){
            System.out.println("token已过时");
            return false;
        }
    }

    @Test
    public void test1(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMTExMSIsImlhdCI6MTU3ODExOTA4Niwic3ViIjoieHh4eEAxMjYuY29tIiwiaXNzIjoid3d3Lnh4eHguY29tIiwiZXhwIjoxNTc4MTIyNjg2fQ.faOhPFgD3qksVkc4AE_Nz6H59PHr9Tw0NlUQ5NCy6Yg";
        System.out.println(verifyToken("1",createJwtToken("1")));
        System.out.println(verifyToken("1",token));
    }

}
