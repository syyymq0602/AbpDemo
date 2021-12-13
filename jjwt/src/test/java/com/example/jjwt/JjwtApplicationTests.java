package com.example.jjwt;

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

}
