package study.springboot.shiro.token.service.login;

import study.springboot.shiro.token.support.result.Result;

public interface LoginService {

    String login(String username, String password);
}
