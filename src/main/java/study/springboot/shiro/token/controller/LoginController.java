package study.springboot.shiro.token.controller;

import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.springboot.shiro.token.support.result.Result;
import study.springboot.shiro.token.support.result.Results;

import java.io.Serializable;
import java.util.Map;

@RestControllerAdvice
public class LoginController {

    @PostMapping("/login")
    public Result login() {
        //
        UsernamePasswordToken token = new UsernamePasswordToken();
        //
        Subject subject = SecurityUtils.getSubject();
        //
        subject.login(token);
        //设置session时间
        subject.getSession()
                .setTimeout(1000 * 60 * 30);
        Serializable sessionId = subject.getSession().getId();

        //
        Map<String, Object> data = Maps.newHashMap();
        data.put("token", sessionId);
        return Results.success(data);
    }

    @PostMapping("/logout")
    public Result logout() {

        return Results.success();
    }
}
