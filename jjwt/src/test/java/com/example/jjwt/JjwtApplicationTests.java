package com.example.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        var token = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJqdGkiOiI4ODg4Iiwic3ViIjoiUm9zZSIsImlhdCI6MTYzOTM5OTM0Nn0." +
                "0hZbfBCJvm_lVbId3naDez0j6xisp2Uq_oJ6kWC8GyU";
        // 解析Token获取负载中声明的对象
        Claims claims = Jwts.parser()
                .setSigningKey("1111")
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("issuedAt:"+claims.getIssuedAt());
    }

}
