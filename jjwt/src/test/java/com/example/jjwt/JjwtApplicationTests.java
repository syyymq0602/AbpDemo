package com.example.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class JjwtApplicationTests {

    @Test
    public void textCreateToken() {
        JwtBuilder jwtBuilder = Jwts.builder()
                // 声明的标识 { “jti":"8888” }
                .setId("8888")
                // 主体 用户 { “sub":"Rose" }
                .setSubject("Rose")
                // 创建日期 { “ita":"xxxx" }
                .setIssuedAt(new Date())
                // 配置加密算法和盐
                .signWith(SignatureAlgorithm.HS256,"1111");
        String token = jwtBuilder.compact();
        System.out.println(token);
        System.out.println("=============");
        String[] tokens = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(tokens[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(tokens[1]));
        // 无法解密 => 乱码
        System.out.println(Base64Codec.BASE64.decodeToString(tokens[2]));
    }

    @Test
    public void testParseToken(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var token = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTYzOTQwMTI4MiwiZXhwIjoxNjM5NDAxMzQyfQ." +
                "IG1EPfnO_4VC7YgnC9-JOZ3LOAbQfH6Knqk5xuQINq8";
        // 解析Token获取负载中声明的对象
        Claims claims = Jwts.parser()
                .setSigningKey("1111")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("issuedAt:"+claims.getIssuedAt());
        System.out.println("签发时间:"+simpleDateFormat.format(claims.getIssuedAt()));
        System.out.println("过期时间:"+simpleDateFormat.format(claims.getExpiration()));
    }

    /**
     * 创建token（失效时间）
     */
    @Test
    public void textCreateTokenExpired() {
        // 当前系统时间
        long now = System.currentTimeMillis();
        // jwt过期时间
        long exp = now + 60 * 1000;
        JwtBuilder jwtBuilder = Jwts.builder()
                // 声明的标识 { “jti":"8888” }
                .setId("8888")
                // 主体 用户 { “sub":"Rose" }
                .setSubject("Rose")
                // 创建日期 { “ita":"xxxx" }
                .setIssuedAt(new Date())
                // 配置加密算法和盐
                .signWith(SignatureAlgorithm.HS256,"1111")
                .setExpiration(new Date(exp));
        String token = jwtBuilder.compact();
        System.out.println(token);
        System.out.println("=============");
        String[] tokens = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(tokens[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(tokens[1]));
        // 无法解密 => 乱码
        System.out.println(Base64Codec.BASE64.decodeToString(tokens[2]));
    }

}
