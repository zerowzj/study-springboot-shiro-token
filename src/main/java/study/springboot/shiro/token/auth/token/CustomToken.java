package study.springboot.shiro.token.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

public class CustomToken implements AuthenticationToken {

    private String token;

    public CustomToken(String token) {
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
