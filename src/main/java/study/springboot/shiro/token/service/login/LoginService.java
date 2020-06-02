package study.springboot.shiro.token.service.login;

import study.springboot.shiro.token.support.result.Result;

public interface LoginService {

    Result login(String username, String password);
}
