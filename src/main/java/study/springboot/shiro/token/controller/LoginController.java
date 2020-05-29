package study.springboot.shiro.token.controller;

import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.springboot.shiro.token.auth.token.CustomAuthToken;
import study.springboot.shiro.token.support.result.Result;
import study.springboot.shiro.token.support.result.Results;

import java.util.Map;

@RestController
public class LoginController {

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, Object> param) {
        String token = "12312";

//        CustomAuthToken authToken = new CustomAuthToken(token);
//        SecurityUtils.getSubject()
//                .login(authToken);
        //
        Map<String, Object> data = Maps.newHashMap();
        data.put("token", token);
        return Results.success(data);
    }
}
