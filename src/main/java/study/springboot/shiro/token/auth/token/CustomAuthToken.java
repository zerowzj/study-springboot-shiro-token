package study.springboot.shiro.token.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

public class CustomAuthToken implements AuthenticationToken {

    private String token;

    public CustomAuthToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
