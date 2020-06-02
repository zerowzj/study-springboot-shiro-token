package study.springboot.shiro.token.controller.login;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.springboot.shiro.token.service.LoginService;
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
        loginService.login(username, password);
        String token = "666666666";
        Map<String, Object> data = Maps.newHashMap();
        data.put("token", token);
        return Results.success(data);
    }
}
