package securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录Controller
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        System.out.println("执行登录方法");
        return "redirect:main.html";
    }
}
