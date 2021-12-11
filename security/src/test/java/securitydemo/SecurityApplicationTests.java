package securitydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class SecurityApplicationTests {

    @Test
    public void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        var encode = encoder.encode("123");
        System.out.println(encode);
        System.out.println(encoder.matches("234",encode));
    }

}
