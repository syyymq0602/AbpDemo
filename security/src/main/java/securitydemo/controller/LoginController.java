package securitydemo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录Controller
 */
@Controller
public class LoginController {

//    @RequestMapping("/login")
//    public String login(){
//        System.out.println("执行登录方法");
//        return "redirect:main.html";
//    }

    @PostMapping("/toMain")
    public String toMain(){
        return "redirect:main.html";
    }

    @PostMapping("/toError")
    public String toError(){
        return "redirect:error.html";
    }

    @GetMapping("/demo")
    @ResponseBody
    public String demo(){
        return "demo";
    }

    @GetMapping("/roles")
    @ResponseBody
//    @Secured("ROLE_abc")
    // PreAuthorize允许以ROLE_开头也可以不用ROLE_开头  均可
    @PreAuthorize("hasAnyRole('abc')")
    public String roles(){
        return "roles";
    }

    @GetMapping("/ip")
    @ResponseBody
    public String IP(HttpServletRequest request){
        return request.getRemoteAddr();
    }
}
