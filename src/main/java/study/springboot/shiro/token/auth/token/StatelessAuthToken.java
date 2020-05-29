package study.springboot.shiro.token.auth.token;

import org.apache.shiro.authc.AuthenticationToken;

public class StatelessAuthToken implements AuthenticationToken {

    private String token;

    public StatelessAuthToken() {
    }

    public StatelessAuthToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return this;
    }
}
