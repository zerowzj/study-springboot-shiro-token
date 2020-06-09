package study.springboot.shiro.token.controller.login;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.springboot.shiro.token.service.login.LoginService;
import study.springboot.shiro.token.support.result.Result;
import study.springboot.shiro.token.support.result.Results;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        //
        String token = loginService.login(username, password);
        Map<String, Object> data = Maps.newHashMap();
        data.put("token", token);
        return Results.success(data);
    }
}
